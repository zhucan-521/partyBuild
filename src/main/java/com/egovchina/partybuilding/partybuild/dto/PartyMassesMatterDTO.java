package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "服务事项DTO")
@Data
public class PartyMassesMatterDTO{

    @ApiModelProperty(value = "服务事项id")
    private Long partyMassesMatterId;

    @ApiModelProperty(value = "党群id",required = true)
    @NotNull(message = "党群id不能为空")
    private Long partyMassesId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "服务对象 字典id FWDX")
    private Long crowd;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

}
