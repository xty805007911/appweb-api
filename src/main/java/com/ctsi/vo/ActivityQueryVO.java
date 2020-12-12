package com.ctsi.vo;

import lombok.Data;

@Data
public class ActivityQueryVO {
    private String keywords = "";
    private Integer activeTypeId = 1;
    private Integer page = 1;
    private  Integer size = 10;
}
