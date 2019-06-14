package com.egovchina.partybuilding.partybuild.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "驻村帮扶队伍")
@Data
public class HelpTeamQueryBean {


    @ApiModelProperty(value = "队伍名称")
    private String teamName;

    @ApiModelProperty(value = "成立日期 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date setDate;

    @ApiModelProperty(value = "人数")
    private Long peopleNumble;

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private Long orgRange;

}