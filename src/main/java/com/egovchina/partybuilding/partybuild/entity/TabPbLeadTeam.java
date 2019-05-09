package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TabPbLeadTeam {

    /**
	 * 领导班子id
	 */
    private Long leadTeamId;

    /**
	 * 党组织主键
	 */
    private Long orgId;

    /**
	 * 届数
	 */
    private Long sessionYear;

    /**
	 * 当选时间", example = "yyyy-MM-dd
	 */
    private Date changeDate;

    /**
	 * 任期年限
	 */
    private Long duringYear;

    /**
	 * 选举方式 dict XJFS
	 */
    private Long voteType;

    /**
	 * 应到人数
	 */
    private Long dueCount;

    /**
	 * 实到人数
	 */
    private Long factCount;

    /**
	 * 总票数
	 */
    private Long voteCount;

    /**
	 * 有效票数
	 */
    private Long validVoteCount;

    /**
	 * 当届标识
	 */
    private Byte current;

    /**
	 * 当选方式 dict YHZSFDX
	 */
    private Long electedType;

    /**
	 * 当选时间", example = "yyyy-MM-dd
	 */
    private Date electedTime;

    /**
	 * 委员人数
	 */
    private Long commMemNum;

    /**
	 * 委员兼职人数
	 */
    private Long commNumParttime;

    /**
	 * 兼职委员人数
	 */
    private Long parttimeCommNum;

    /**
	 * 常务委员人数
	 */
    private Long standingMemNum;

    /**
	 * 候补委员人数
	 */
    private Long backMemNum;

    /**
	 * 数据描述
	 */
    private String description;

    /**
	 * 选举情况
	 */
    private String voteStatus;

    /**
	 * 选举结果
	 */
    private String voteResult;

    /**
     * 有效标记
     */
    private String eblFlag;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 排序码
     */
    private Long orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户Id
     */
    private Long createUserid;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改用户Id
     */
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    private String updateUsername;

    /**
     * 版本
     */
    private Long version;

}