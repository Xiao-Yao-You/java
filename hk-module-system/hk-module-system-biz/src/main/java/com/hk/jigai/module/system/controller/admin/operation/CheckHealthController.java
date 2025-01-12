package com.hk.jigai.module.system.controller.admin.operation;

import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author SZW
 * @Date 2025/1/12 16:51
 */
@RestController
@RequestMapping()
@Validated
public class CheckHealthController {

    @GetMapping("/health")
    @PermitAll
    @TenantIgnore
    public Integer checkHealth() {
        String jdbcUrl = "jdbc:mysql://192.168.95.20:3306/hk_jigai_platform";
        String username = "root";
        String password = "xxb123@hk.com";

//        String jdbcUrl = "jdbc:mysql://172.21.5.52:3306/hk_jigai_platform";
//        String username = "root";
//        String password = "Xxb123@hk.com";
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立连接
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // 检查连接是否成功
            if (connection != null && !connection.isClosed()) {
                System.out.println("数据库连接成功！");
            } else {
                System.out.println("数据库连接失败！");
            }
            // 关闭连接
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动未找到！");
        } catch (
                SQLException e) {
            System.out.println("数据库连接异常：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("未知异常：" + e.getMessage());
        }
        return 200;
    }
}
