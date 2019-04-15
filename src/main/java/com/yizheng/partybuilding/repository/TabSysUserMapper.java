package com.yizheng.partybuilding.repository;


import com.yizheng.partybuilding.dto.Personnel;
import com.yizheng.partybuilding.dto.TransferUserDeptInfo;
import com.yizheng.partybuilding.dto.UserDeptPositiveDto;
import com.yizheng.partybuilding.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabSysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKeyWithBLOBs(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int updateByPrimaryKeyId(SysUser record);

    List<SysUser> selectPage(SysUser sysUser);

    List<SysUser> selectPageByMap(Map<String, Object> map);

    /**
     * 根据身份证查询人员ID
     *
     * @param IDcard
     * @return
     */
    Long SelectUserIdByIDcard(String IDcard);


    /**
     * 根据身份证查询人员信息
     *
     * @param idCardNo
     * @return
     */
    SysUser selectUserByIdCardNo(String idCardNo);


    List<SysUser> selectPartyByIdCardNoOrUserName(SysUser sysUser);

    //根据组织Id 获取党务工作者信息
    TransferUserDeptInfo getDWRoleUserInfoByDeptId(Long deptId);

    //根据多个组织id获取，多个党务工作者
    List<TransferUserDeptInfo> getDWRoleUserInfoByDeptIds(List<String> manageDeptIds);

    //组织关系接转-修改用户的deptId
    int updateDeptIdByUserId(@Param("userId") Integer userId, @Param("deptId") Integer deptId);

    /**
     * 根据userId 获取组织信息，职务信息
     *
     * @param userId
     * @return
     */
    UserDeptPositiveDto selectUserDeptPositiveByUserId(Long userId);

    /**
     * 根据组织id 获得该组织连接领导列表
     *
     * @param deptId
     * @return
     */
    List<UserDeptPositiveDto> selectUserDeptByDeptId(Long deptId);

    int updateByUserId(Long userId);

    /**
     * 判断党员兴趣爱好是否选择
     *
     * @param userId
     * @return 选择返回他的兴趣爱好  未选择返回字符串0
     */
    String checkedSysUserLikes(Long userId);


    int updateByisPoor(Long userId);

    /**
     * 批量修改党员所在组织
     *
     * @param userIdList
     * @param deptId
     * @return
     */
    int batchDeptIdByUserId(@Param("userIdList") List<Integer> userIdList, @Param("deptId") Long deptId);

    /**
     * 查询组织下的所有人员 包括直属党员、在职党员、流入党员
     *
     * @param deptId
     * @return
     */
    List<Personnel> selectUserByDeptId(@Param("deptId") Long deptId);

    /**
     * 查询组织下的直属党员
     *
     * @param deptId
     * @return
     */
    List<Personnel> getAllUserByDeptId(@Param("deptId") Long deptId);

    void updateNoPoor(Long userId);

    List<SysUser> partyIdentityVerification(@Param("username") String username, @Param("idCardNo") String idCardNo, @Param("phone") String phone);

    /**
     * 选人接口(根据主键id或者报到组织id进行筛选必填其中一项)
     *
     * @param user
     * @return
     */
    List<SysUser> selectAllRegister(SysUser user);

    /**
     * 修改社区报到信息
     *
     * @param user
     */
    void editUserRegister(SysUser user);

    /**
     * 根据id逻辑删除
     *
     * @param userId
     */
    void flagDelete(Long userId);

    /**
     * 保存家庭成员信息并返回自增长Id
     *
     * @param sysUser
     * @return
     */
    int saveEntity(SysUser sysUser);

    /**
     * 判断是否属于此节点
     *
     * @param ordId  当前登录人的组织id
     * @param deptId 传进来的组织id
     * @return
     */
    Boolean verification(@Param("orgId") Long ordId, @Param("deptId") Long deptId);

    /**
     * 修改头像
     *
     * @param avatar
     * @param userId
     * @return
     */
    int editAvatar(@Param("avatar") String avatar, @Param("userId") String userId);

    /**
     * desc: 根据用户Id查询该用户是否存在
     *
     * @param userId 用户Id
     * @return 受影响行数
     * @author FanYanGen
     * @date 2019/4/15 9:32
     **/
    int checkIsExistByUserId(Integer userId);

}