package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/29
 */

@Data
@Accessors(chain = true)
@ApiModel("党建联席会及成员组织")
public class JointMeetDTO {
    @ApiModelProperty(value = "党建联席会主键")
    private Long jointMeetId;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "成立时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundedDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "党建联席会成员组织列表", required = true)
    @NotNull(message = "党建联席会成员组织列表不能为空")
    List<JointMeetOrgDTO> jointMeetOrgs;
}
