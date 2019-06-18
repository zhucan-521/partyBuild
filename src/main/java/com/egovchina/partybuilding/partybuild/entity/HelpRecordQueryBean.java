package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "驻村帮扶记录选择")
@Data
public class HelpRecordQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "列表范围 1.查当前组织及其直属组织；2.查当前组织及所有下级组织; 3.不传查当前组织")
    private Long orgRange;

    @ApiModelProperty(value = "记录日趋 开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDateStart;

    @ApiModelProperty(value = "记录日趋 结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDateEnd;

    @ApiModelProperty(value = "记录标题")
    private String recordTitle;

}