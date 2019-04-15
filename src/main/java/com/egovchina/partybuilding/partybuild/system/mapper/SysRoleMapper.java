package com.egovchina.partybuilding.partybuild.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.system.entity.SysRole;
import com.egovchina.partybuilding.partybuild.system.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 查询角色列表含有组织信息
	 *
	 * @param query     查询对象
	 * @param condition 条件
	 * @return List
	 */
	List<Object> selectRolePage(Query<Object> query, Map<String, Object> condition);

    /**
     * 翻页查询角色信息
     *
     * @param roleName
     * @return
     */
    List<Object> listPage(@Param(value = "createUserid") Long createUserid ,@Param(value = "roleName") String roleName);

	/**
	 * 通过组织ID查询角色列表
	 *
	 * @param deptId 组织ID
	 * @return 角色列表
	 */
	List<SysRole> selectListByDeptId(Integer deptId);

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userId);

	/**
	 * 根据角色标识查询角色数据
	 * @param roleCode
	 * @return
	 */
    SysRole selectByRoleCode(@Param(value = "roleCode") String roleCode);

}
