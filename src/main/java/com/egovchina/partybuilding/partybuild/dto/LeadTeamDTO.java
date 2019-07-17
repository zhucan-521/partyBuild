package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 领导班子DTO
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子DTO")
@Data
public class LeadTeamDTO {

    @ApiModelProperty("领导班子id")
    private Long leadTeamId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "届数", required = true)
    @NotNull(message = "届数不能为空")
    private Long sessionYear;

    @ApiModelProperty(value = "当选时间", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date changeDate;

    @ApiModelProperty(value = "任期年限", required = true)
    @NotNull(message = "任期年限不能为空")
    private Long duringYear;

    @ApiModelProperty("选举方式 dict XJFS")
    private Long voteType;

    @ApiModelProperty("应到人数")
    private Long dueCount;

    @ApiModelProperty("实到人数")
    private Long factCount;

    @ApiModelProperty("总票数")
    private Long voteCount;

    @ApiModelProperty("有效票数")
    private Long validVoteCount;

    @ApiModelProperty("当届标识")
    private Byte current = '0';

    @ApiModelProperty("当选方式 dict YHZSFDX")
    private Long electedType;

    @ApiModelProperty(value = "当选时间", example = "2019-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date electedTime;

    @ApiModelProperty("委员人数")
    private Long commMemNum;

    @ApiModelProperty("委员兼职人数")
    private Long commNumParttime;

    @ApiModelProperty("兼职委员人数")
    private Long parttimeCommNum;

    @ApiModelProperty("常务委员人数")
    private Long standingMemNum;

    @ApiModelProperty("候补委员人数")
    private Long backMemNum;

    @ApiModelProperty("数据描述")
    private String description;

    @ApiModelProperty("选举情况")
    private String voteStatus;

    @ApiModelProperty("选举结果")
    private String voteResult;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    /**
     * 是否当届
     *
     * @return
     */
    public boolean ifCurr() {
        Calendar toComp = Calendar.getInstance();
        toComp.setTime(this.electedTime);
        toComp.set(Calendar.HOUR_OF_DAY, 0);
        toComp.set(Calendar.MINUTE, 0);
        toComp.set(Calendar.SECOND, 0);
        toComp.set(Calendar.MILLISECOND, 0);
        toComp.getTime();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.getTime();
        Calendar endComp = Calendar.getInstance();
        endComp.setTime(this.electedTime);
        endComp.set(Calendar.HOUR_OF_DAY, 0);
        endComp.set(Calendar.MINUTE, 0);
        endComp.set(Calendar.SECOND, 0);
        endComp.set(Calendar.MILLISECOND, 0);
        endComp.getTime();
        endComp.add(Calendar.YEAR, this.duringYear.intValue());
        return now.after(toComp) && now.before(endComp);
    }

}
