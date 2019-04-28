package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wuyunjie
 * @version 1.0
 * @date 2018/12/10
 */

@ApiModel("大公委VO")
@Data
public class StreetCommitteeVO{
    @ApiModelProperty(value = "大公委主键")
    private Long grantCommitteeId;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "当届标识")
    private Byte current;

    @ApiModelProperty(value = "年届")
    private Long sessionYear;

    @ApiModelProperty(value = "换届时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date changeDate;

    @ApiModelProperty(value = "任期年限")
    private Long duringYear;

    @ApiModelProperty(value = "选举类型 FJXJ")
    @JsonSerialize(using = DictSerializer.class)
    private Long voteType;

    @ApiModelProperty(value = "应到人数")
    private Long dueCount;

    @ApiModelProperty(value = "实到人数")
    private Long factCount;

    @ApiModelProperty(value = "总票数")
    private Long voteCount;

    @ApiModelProperty(value = "有效票数")
    private Long validVoteCount;

    @ApiModelProperty(value = "当选方式 XJFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long electedType;

    @ApiModelProperty(value = "当选时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date electedTime;

    @ApiModelProperty(value = "委员人数")
    private Long commMemNum;

    @ApiModelProperty(value = "委员兼职人数")
    private Long commNumParttime;

    @ApiModelProperty(value = "兼职委员人数")
    private Long parttimeCommNum;

    @ApiModelProperty(value = "常务委员人数")
    private Long standingMemNum;

    @ApiModelProperty(value = "候补委员人数")
    private Long backMemNum;

    @ApiModelProperty(value = "成立时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "选举情况")
    private String voteStatus;

}
