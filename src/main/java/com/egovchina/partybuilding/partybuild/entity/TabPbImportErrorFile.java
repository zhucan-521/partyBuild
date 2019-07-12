package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "导入错误实体")
@Data

public class TabPbImportErrorFile {

    private Long id;

    private Byte businessType;

    /**
     * 操作用户id
     */
    private Long createUserid;

    /**
     * 操作用户名称
     */
    private String createUsername;

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private Date createTime;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件地址
     */
    private String fileAccessUrl;
}