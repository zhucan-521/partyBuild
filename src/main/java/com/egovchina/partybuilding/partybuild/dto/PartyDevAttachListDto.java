package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2019/01/04
 */

//用于展示党员发展附件
@Data
@Accessors(chain = true)
@ApiModel("党员发展附件")
public class PartyDevAttachListDto {

    @ApiModelProperty(value = "附件类型")
    private Long attachmentType;

    @ApiModelProperty(value = "当前发展状态")
    private Long status;

    @ApiModelProperty(value = "当前发展状态")
    private Long userId;

    @ApiModelProperty(value = "当前发展状态")
    private Long dpId;

    private Map<String, Object> hostId11;
    private Map<String, Object> hostId12;
    private Map<String, Object> hostId21;
    private Map<String, Object> hostId22;
    private Map<String, Object> hostId23;
    private Map<String, Object> hostId24;
    private Map<String, Object> hostId31;
    private Map<String, Object> hostId32;
    private Map<String, Object> hostId33;
    private Map<String, Object> hostId34;
    private Map<String, Object> hostId35;
    private Map<String, Object> hostId41;
    private Map<String, Object> hostId42;
    private Map<String, Object> hostId43;
    private Map<String, Object> hostId44;
    private Map<String, Object> hostId45;
    private Map<String, Object> hostId46;
    private Map<String, Object> hostId47;
    private Map<String, Object> hostId51;
    private Map<String, Object> hostId52;
    private Map<String, Object> hostId53;
    private Map<String, Object> hostId54;
    private Map<String, Object> hostId55;
    private Map<String, Object> hostId56;
    private Map<String, Object> hostId57;

}
