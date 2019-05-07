package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "减少党员查询实体")
@Data
public class DeleteUserQueryBean {

    @ApiModelProperty(value ="用户id")
    private Long userId;

    @ApiModelProperty(value ="减少方式：出党3；停止党籍4；死亡5；其他6")
    private Long outType;

    @ApiModelProperty(value ="离开党组织日期",example ="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reduceTime;

    @ApiModelProperty("出党方式")
    private Long quitType;

}

