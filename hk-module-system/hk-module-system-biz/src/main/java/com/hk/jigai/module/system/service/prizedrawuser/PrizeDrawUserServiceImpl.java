package com.hk.jigai.module.system.service.prizedrawuser;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.exception.ErrorCode;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserSaveReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.TextMessage;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import com.hk.jigai.module.system.dal.mysql.prizedrawactivity.PrizeDrawActivityMapper;
import com.hk.jigai.module.system.dal.mysql.prizedrawuser.PrizeDrawUserMapper;
import org.dom4j.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import org.springframework.web.client.RestTemplate;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;


/**
 * 抽奖用户 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeDrawUserServiceImpl implements PrizeDrawUserService {

    @Resource
    private PrizeDrawUserMapper prizeDrawUserMapper;

    @Resource
    private RedisTemplate stringRedisTemplate;

    @Resource
    private RestTemplate restTemplate;

    private final String appId = "wx145112d98bdbfbfe";

    private final String secret = "3d82310d78a1a2e54b2232ca97bbfdb7";

    @Resource
    private PrizeDrawActivityMapper prizeDrawActivityMapper;

    @Override
    public String getTokenByRefreshToken(String refreshToken) {
        //        https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        String refreshStr = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                        "appid=wx145112d98bdbfbfe&grant_type=refresh_token&refresh_token=" +
                        refreshToken, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map refreshTokenMap = (Map) JSON.parse(refreshStr);
        if (refreshTokenMap == null || refreshTokenMap.get("access_token") == null) {
            throw exception(PRIZE_DRAW_TOKEN);
        }
        String accessToken = (String) refreshTokenMap.get("access_token");
        return accessToken;
    }

    @Override
    public String receive(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request) {
        String token = "szhjshkxcl";
        // 验证微信签名
        if (!checkSignature(signature, timestamp, nonce, token)) {
            throw exception(PRIZE_DRAW_CHECK);
        }
        // 验证服务端配置
        if (echostr != null) {
            return echostr;
        }
        // 接收微信推送的消息
        String xmlString = null;
        try {
            xmlString = readRequest(request);
        } catch (Exception e) {
            throw exception(PRIZE_DRAW_CHECK);
        }
        try {
            Map<String, String> resXml = responseXmlToMap(xmlString);
            String ticket = resXml.get("Ticket"); // 获取二维码凭证
            String gzhOpenid = resXml.get("FromUserName"); // 获取OpenId
            String eventType = resXml.get("Event"); // 获取事件类型
            String msgType = resXml.get("MsgType");
//            System.out.println("===========================");
//            System.out.println(">>>>>>>>>>>>>>>>>>>>" + ticket + "||" + gzhOpenid + "||" + eventType + "||" + msgType + "||" + resXml.get("ToUserName"));
//            System.out.println("===========================");
            PrizeDrawActivityDO prizeDrawActivityDO = prizeDrawActivityMapper.selectOne(new QueryWrapper<PrizeDrawActivityDO>().lambda().eq(PrizeDrawActivityDO::getTicket, ticket));
            Long activityId = prizeDrawActivityDO.getId();

            stringRedisTemplate.delete("GZH_EWM_PREFIX" + ticket);
            stringRedisTemplate.opsForValue().set("GZH_EWM_PREFIX" + ticket, gzhOpenid, Duration.ofSeconds(280));
            if ("event".equals(msgType)) {
                if ("subscribe".equals(eventType)) { // 如果是订阅消息
                    String subscribeContent = "感谢关注,点击链接：<a style='color:#007ecc;'href='https://map.jshkxcl.cn?activityId=' + " + activityId + ">参与抽奖</a>";
                    stringRedisTemplate.opsForValue().set("GZH_GUAN_ZHU" + ticket, "0", Duration.ofSeconds(280));
                    return getWxReturnMsg(resXml, subscribeContent);
                }
                if ("SCAN".equals(eventType)) { // 如果是扫码消息
                    String scanContent = "感谢关注,点击链接：<a style='color:#007ecc;'href='https://map.jshkxcl.cn?activityId=' + " + activityId + ">参与抽奖</a>";
                    stringRedisTemplate.opsForValue().set("GZH_SAO_MA" + ticket, "1", Duration.ofSeconds(280));
                    return getWxReturnMsg(resXml, scanContent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readRequest(HttpServletRequest request) throws IOException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String str;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        in.close();
        inputStream.close();
        return sb.toString();

    }

//    private String getXmlReturnMsg(String toUser, String fromUser, Long createTime, String content) {
//        return "<xml>\n" +
//                "  <ToUserName><![CDATA[" + toUser + "]]></ToUserName>\n" +
//                "  <FromUserName><![CDATA[" + fromUser + "]]></FromUserName>\n" +
//                "  <CreateTime>" + createTime + "</CreateTime>\n" +
//                "  <MsgType><![CDATA[text]]></MsgType>\n" +
//                "  <Content><![CDATA[" + content + "]]></Content>\n" +
//                "</xml>";
//    }

    public static String getWxReturnMsg(Map<String, String> decryptMap, String content) throws UnsupportedEncodingException {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(decryptMap.get("FromUserName"));
        textMessage.setFromUserName(decryptMap.get("ToUserName"));
        textMessage.setCreateTime(System.currentTimeMillis());
        textMessage.setMsgType("text"); // 设置回复消息类型
        textMessage.setContent(content); // 设置回复内容
        String xmlMsg = getXmlString(textMessage);
        // 设置返回信息编码,防止中文乱码
        String encodeXmlMsg = new String(xmlMsg.getBytes(), "UTF-8");
        return encodeXmlMsg;
    }

    private static String getXmlString(TextMessage textMessage) {
        String xml = "";
        if (textMessage != null) {
            xml = "<xml>";
            xml += "<ToUserName><![CDATA[";
            xml += textMessage.getToUserName();
            xml += "]]></ToUserName>";
            xml += "<FromUserName><![CDATA[";
            xml += textMessage.getFromUserName();
            xml += "]]></FromUserName>";
            xml += "<CreateTime>";
            xml += textMessage.getCreateTime();
            xml += "</CreateTime>";
            xml += "<MsgType><![CDATA[";
            xml += textMessage.getMsgType();
            xml += "]]></MsgType>";
            xml += "<Content><![CDATA[";
            xml += textMessage.getContent();
            xml += "]]></Content>";
            xml += "</xml>";
        }
        return xml;
    }

    public static Map<String, String> responseXmlToMap(String xmlString) throws DocumentException {
        // 解析 XML 字符串为 Document 对象
        Document document = DocumentHelper.parseText(xmlString);
        // 获取根元素
        Element rootElement = document.getRootElement();
        // 获取子元素
        List<Element> nodes = rootElement.elements();
        // 获取子元素的文本内容
        Map<String, String> resultMap = new HashMap<>();
        for (Node node : nodes) {
            Element element = (Element) node;
            String nodeName = element.getName();
            String nodeText = element.getTextTrim();
            resultMap.put(nodeName, nodeText);
        }
        if (resultMap.containsKey("errcode")) {
            throw exception(PRIZE_DRAW_CHECK);
        }
        return resultMap;
    }


    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null && tmpStr.equalsIgnoreCase(signature.toUpperCase());
    }

    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            strDigest.append(byteToHexStr(byteArray[i]));
        }
        return strDigest.toString();
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    @Override
    public PrizeDrawUserRespVO createPrizeDrawUser(String code, String activityId) {
        PrizeDrawUserRespVO prizeDrawUserRespVO = new PrizeDrawUserRespVO();
        String authStr = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7&code="
                        + code + "&grant_type=authorization_code", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map authMap = (Map) JSON.parse(authStr);
        if (authMap == null || authMap.get("openid") == null) {
            throw exception(PRIZE_DRAW_TOKEN);
        }
        String openid = (String) authMap.get("openid");
        String accessToken = (String) authMap.get("access_token");
        String refreshToken = (String) authMap.get("refresh_token");
        prizeDrawUserRespVO.setOpenid(openid);
        prizeDrawUserRespVO.setAccessToken(accessToken);
        prizeDrawUserRespVO.setRefreshToken(refreshToken);
        PrizeDrawUserDO prizeDrawUserDO = prizeDrawUserMapper.selectOne(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getOpenid, openid)    //openId相同
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)); //活动批次相同
        if (prizeDrawUserDO != null) {
//            throw exception(PRIZE_DRAW_USER_EXISTS);
            PrizeDrawUserRespVO prizeDrawUserResp = BeanUtils.toBean(prizeDrawUserDO, PrizeDrawUserRespVO.class);
            prizeDrawUserResp.setOpenid(openid);
            prizeDrawUserResp.setAccessToken(accessToken);
            prizeDrawUserResp.setRefreshToken(refreshToken);
            return prizeDrawUserResp;
        }

        PrizeDrawUserDO prizeDrawUser = new PrizeDrawUserDO();
//        https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String infoStr = restTemplate.exchange("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map infoMap = (Map) JSON.parse(infoStr);
        if (infoMap == null) {
            throw exception(PRIZE_DRAW_NOT_INFO);
        }
        prizeDrawUser.setOpenid((String) authMap.get("openid"));
        prizeDrawUserRespVO.setOpenid((String) authMap.get("openid"));
        prizeDrawUser.setNickname((String) authMap.get("nickname"));
        prizeDrawUserRespVO.setNickname((String) authMap.get("nickname"));
        prizeDrawUser.setHeadimgurl((String) authMap.get("headimgurl"));
        prizeDrawUserRespVO.setHeadimgurl((String) authMap.get("headimgurl"));
        prizeDrawUser.setStatus(1); //默认“1”未中奖
        prizeDrawUser.setWinningRate(1.00);//默认权重为1

        prizeDrawUserMapper.insert(prizeDrawUser);
        Long aLong = prizeDrawUserMapper.selectCount(new QueryWrapper<PrizeDrawUserDO>().lambda().eq(PrizeDrawUserDO::getActivityBatch, activityId));
        prizeDrawUserRespVO.setCreateTime(prizeDrawUser.getCreateTime());
        prizeDrawUserRespVO.setCurrentNum(aLong);
        // 返回
        return prizeDrawUserRespVO;
    }

    @Override
    @Transactional
    public List<PrizeDrawUserDO> prizeDraw(Long activityId, Long prizePool, Integer winNum) {
        //所有可以参与抽奖的人员
        List<PrizeDrawUserDO> prizeDrawUserDOS = prizeDrawUserMapper.selectList(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawUserDO::getStatus, 1)
                .and(wrapper -> wrapper.isNull(PrizeDrawUserDO::getPrizePool).or().eq(PrizeDrawUserDO::getPrizePool, prizePool)));
        if (CollectionUtil.isEmpty(prizeDrawUserDOS) || prizeDrawUserDOS.size() < winNum) {
            throw exception(PRIZE_DRAW_USER_NOT_ENOUGH);
        }
        //开始抽奖
        List<PrizeDrawUserDO> winners = drawWinners(prizeDrawUserDOS, winNum);
        winners.stream().map(p -> {
            p.setStatus(2);
            p.setPrizeLevel(prizePool.intValue());
            return p;
        }).collect(Collectors.toList());
        prizeDrawUserMapper.updateBatch(winners);
        return winners;
    }

    public static List<PrizeDrawUserDO> drawWinners(List<PrizeDrawUserDO> participants, int numberOfWinners) {
        List<PrizeDrawUserDO> winners = new ArrayList<>();

        // 计算累积概率
        double[] cumulativeProbabilities = new double[participants.size()];
        double sum = 0.0;
        for (int i = 0; i < participants.size(); i++) {
            sum += participants.get(i).getWinningRate();
            cumulativeProbabilities[i] = sum;
        }

        // 使用随机数生成器选择中奖者
        Random random = new Random();
        while (winners.size() < numberOfWinners) {
            double rand = random.nextDouble() * sum;
            int winnerIndex = binarySearch(cumulativeProbabilities, rand);
            PrizeDrawUserDO winner = participants.get(winnerIndex);

            // 如果该参与者已经被抽中过，则继续抽，否则将其添加到中奖者列表
            if (!winners.contains(winner)) {
                winners.add(winner);
            }
        }

        return winners;
    }

    private static int binarySearch(double[] cumulativeProbabilities, double rand) {
        int low = 0, high = cumulativeProbabilities.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            double midVal = cumulativeProbabilities[mid];

            if (midVal < rand) {
                low = mid + 1;
            } else if (midVal > rand) {
                high = mid - 1;
            } else {
                return mid; // 这种情况几乎不会发生，因为概率是浮点数
            }
        }
        return low;
    }

    @Override
    public List<PrizeDrawUserDO> getAllWinnerList(Long activityId, Long prizeLevel) {

        List<PrizeDrawUserDO> prizeDrawUserDOS = prizeDrawUserMapper.selectList(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawUserDO::getPrizeLevel, prizeLevel));

        return prizeDrawUserDOS;
    }

}