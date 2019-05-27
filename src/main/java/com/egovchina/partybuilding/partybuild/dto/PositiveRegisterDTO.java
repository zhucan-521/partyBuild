package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "在职党员报到信息DTO")
@Data
public class PositiveRegisterDTO {

    @ApiModelProperty(value = "报到id")
    private Long positiveRegistId;

    @ApiModelProperty(value = "社区id", required = true)
    @NotNull(message = "社区id不能为空")
    private Long communityId;

    @ApiModelProperty(value = "党员id", required = true)
    @NotNull(message = "党员id不能为空")
    private Long userId;

    @ApiModelProperty(value = "报到日期 yyyy-MM-dd", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registDate;

    @ApiModelProperty(value = "报到证附件")
    private List<TabPbAttachment> attachments;

}
