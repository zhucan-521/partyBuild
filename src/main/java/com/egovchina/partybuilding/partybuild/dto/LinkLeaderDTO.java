package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 联点信息DTO
 *
 * @author wuyunjie
 * Date 2019-04-22 17:43
 */
@Data
@ApiModel("联点信息DTO")
public class LinkLeaderDTO {
    @ApiModelProperty(value = "组织联点领导联点主键")
    private Long linkLedaerId;

    @ApiModelProperty(value = "组织主键", required = true)
    @NotNull(message = "组织id不能为空")
    private Long deptId;

    @ApiModelProperty(value = "人员Id", required = true)
    @NotNull(message = "userId不能为空")
    private Long userId;

    @ApiModelProperty(value = "联点开始时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date linkStartDate;

    @ApiModelProperty(value = "联点结束时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date linkFinishedDate;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "联点说明")
    private String comment;

    @ApiModelProperty(value = "连接领导姓名")
    private String realName;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "活动id集合")
    private List<Long> activitiesId;
}
