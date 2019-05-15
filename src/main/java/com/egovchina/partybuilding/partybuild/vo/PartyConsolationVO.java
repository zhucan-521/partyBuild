package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 慰问党员实体
 *
 * @author zhucan
 */
@Data
@ApiModel("慰问党员实体")
public class PartyConsolationVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "被慰问的人 user_id")
    private Long userId;

    @ApiModelProperty(value = "慰问时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consolationTime;

    @ApiModelProperty(value = "慰问情况")
    private String consolationInfo;

    @ApiModelProperty(value = "被慰问的人姓名")
    private String username;

}
