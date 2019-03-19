package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbGrantCommittee {

    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value = "大公委主键")
    private Long grantCommitteeId;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "当届标识")
    private Byte current;

    @ApiModelProperty(value = "年届")
    private Long sessionYear;

    @ApiModelProperty(value = "换届时间")
    @JsonFormat(pattern = FORMAT, timezone = "GMT+8")
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
    @JsonFormat(pattern = FORMAT, timezone = "GMT+8")
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
    @JsonFormat(pattern = FORMAT, timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述", hidden = true)
    @JsonIgnore
    private String description;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "选举情况")
    private String voteStatus;

}