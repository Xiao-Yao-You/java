package com.hk.jigai.module.infra.job.job.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeptDTO {
    private Long id;
    private String name;
    private Long parentId;

    private List<DeptDTO> children;

    public DeptDTO(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }
}
