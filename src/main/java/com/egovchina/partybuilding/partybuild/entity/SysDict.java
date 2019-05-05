package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {

    /**
     * ID
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
     * 字典类型(普通字典为父级字典的value，root字典为自身的value)",example = "AGE
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记",example = "0",required=true,notes = "0:正常;1:删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 父级id(root字典为1)",example = "0
     */
    private Integer parentId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
