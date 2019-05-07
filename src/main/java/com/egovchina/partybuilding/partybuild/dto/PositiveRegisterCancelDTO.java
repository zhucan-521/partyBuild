package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 党员报到撤销实体
 *
 * @author Zhang Fan
 **/
@ApiModel(value = "党员报到撤销DTO实体")
@Data
public class PositiveRegisterCancelDTO {

    @ApiModelProperty(value = "id", required = true)
    private Long positiveRegistId;

    @ApiModelProperty(value = "撤销说明")
    private String revokeTomment;

    @ApiModelProperty(value = "撤销时间 yyyy-MM-dd", required = true, example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "撤销时间不能为空")
    private Date revokeDate;
}
