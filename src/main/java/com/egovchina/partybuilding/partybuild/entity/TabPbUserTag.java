package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbUserTag{

    /**
     * 用户标记id
     */
    private Long usertagId;

    /**
     * 用户id
     */
    private Long userId;

    /**
	 * 用户标签字典Id
	 */
    private Long tagType;

    /**
	 * 字典id
	 */
    private Integer id;

    /**
	 * 数据值",example = "AGE
	 */
    private String value;

    /**
	 * 标签名",example = "过滤条件—年龄
	 */
    private String label;

    /**
	 * 字典类型
	 */
    private String type;

    /**
	 * 描述
	 */
    private String description;

    /**
	 * 排序（升序）
	 */
    private BigDecimal sort;

    /**
	 * 备注信息
	 */
    private String remarks;

    /**
	 * 字典父级id(root字典为1)
	 */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户id
     */
    private Long createUserid;

    /**
     * 创建用户名称
     */
    private String createUsername;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新用户id
     */
    private Long updateUserid;

    /**
     * 更新用户姓名
     */
    private String updateUsername;

}