package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * desc: 党员发展步骤附件-数据传输对象
 * Created by FanYanGen on 2019/4/23 10:47
 */
@Data
@ApiModel("党员发展步骤附件-数据传输对象")
public class PartyDevAttachDTO {

    @ApiModelProperty(value = "主键id, 用于指定具体是哪一个发展步骤", required = true)
    private Long dpId;

    @ApiModelProperty(value = "指定的步骤, 如第一步骤第一环节就是11", required = true)
    private Long status;

    @ApiModelProperty(value = "附件列表, 只需文件名, 当使用此字段时, 将自动忽略attachments字段")
    private List<String> attach;

    @ApiModelProperty(value = "扩展字段, 若附件业务变动, 可使用此字段传递具体的附件信息. 一般情况下该字段不使用")
    private List<TabPbAttachment> attachments;

}
