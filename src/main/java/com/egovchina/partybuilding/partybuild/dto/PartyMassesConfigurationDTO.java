package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "配置清单DTO")
@Data
public class PartyMassesConfigurationDTO{

    @ApiModelProperty(value = "配置清单id")
    private Long partyMassesConfigurationId;

    @ApiModelProperty(value = "党群id",required = true)
    @NotNull(message = "党群id不能为空")
    private Long partyMassesId;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空")
    private String configurationType;

    @ApiModelProperty(value = "数量")
    private Long quantity;

}
