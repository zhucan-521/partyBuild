package com.egovchina.partybuilding.partybuild.repository;


import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.partybuild.dto.TransferUserDeptInfo;
import com.egovchina.partybuilding.partybuild.dto.UserDeptPositiveDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberChooseQueryBean;
import com.egovchina.partybuilding.partybuild.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TabSysUserMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insertSelective(SysUser record);

    /**
     * 通过主键查询
     *
     * @param userId
     * @return
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * 通过条件更新用户
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * 用于维护新增或修改时,关联的学历,工作信息,职务数据
     *
     * @param user
     * @return
     */
    int updateByPrimaryKeySelectiveSpecialModification(SysUser user);

    /**
     * 用于党员列表显示,获取完整度度字段查询
     *
     * @param hashMap
     * @return
     */
    List<SystemDetailsVO> selectPageByMap(HashMap<String, Object> hashMap);

    /**
     * 党员列表总数查询
     *
     * @param hashMap
     * @return
     */
    int selectPageByMapCOUNT(HashMap<String, Object> hashMap);

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

    /**
     * 根据身份証查找identity信息
     *
     * @return
     */
    Long selectUserByIdFindIdentity(Long userId);

    /**
     * 通过用户名 或者身份证查询用户列表
     *
     * @param idCardNo
     * @param username
     * @return
     */
    List<SysUserVO> selectPartyByIdCardNoOnUserName(@Param("idCardNo") String idCardNo, @Param("username") String username);

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
    UserDeptPositionVO selectUserDeptPositionVOByUserId(Long userId);

    /**
     * 根据组织id 获得该组织连接领导列表
     *
     * @param deptId
     * @return
     */
    List<UserDeptPositiveDTO> selectUserDeptByDeptId(Long deptId);

    /**
     * 判断党员兴趣爱好是否选择
     *
     * @param userId
     * @return 选择返回他的兴趣爱好  未选择返回字符串0
     */
    String checkedSysUserLikes(Long userId);


    /**
     * 批量修改党员所在组织
     *
     * @param userIdList
     * @param deptId
     * @return
     */
    int batchDeptIdByUserId(@Param("userIdList") List<Integer> userIdList, @Param("deptId") Long deptId);

    /**
     * 查询组织下的直属党员
     *
     * @param deptId
     * @return
     */
    List<DirectPartyMemberVO> selectDirectPartyMemberVOByDeptId(@Param("deptId") Long deptId);

    /**
     * 党员信息核查
     *
     * @param queryValue 查询值
     * @return
     */
    List<PersonnelVO> partyIdentityVerification(@Param("queryValue") String queryValue);

    /**
     * 判断是否属于此节点
     *
     * @param ordId  当前登录人的组织id
     * @param deptId 传进来的组织id
     * @return
     */
    Boolean verification(@Param("orgId") Long ordId, @Param("deptId") Long deptId);

    /**
     * desc: 根据用户Id查询该用户是否存在
     *
     * @param userId 用户Id
     * @return true or false
     * @author FanYanGen
     * @date 2019/4/15 9:32
     **/
    boolean checkIsExistByUserId(Long userId);

    /**
     * 根据身份证查询用户是否存在
     *
     * @param idCard
     * @return
     */
    boolean checkIsExistByIdCard(String idCard);

    /**
     * 根据手机号码查询用户是否存在
     *
     * @param phone
     * @return
     */
    boolean checkIsExistByPhone(String phone);

    /**
     * 查询附带 工作 学历信息
     *
     * @param userId
     * @return
     */
    PartyMemberVO selectByPrimaryKeyToAll(Long userId);

    /**
     * 党员名册查询概况
     *
     * @param userId
     * @return
     */
    PartyMemberDetailVO selectDeailsByPrimaryKey(Long userId);

    /**
     * 查询书记个人简单信息
     *
     * @param userId
     * @return
     */
    SecretariesPartyMemberVO selectSecretariesPartyByPrimaryKey(Long userId);

    /**
     * 修改困难标识
     *
     * @param isPoor
     * @param hardshipId
     * @return
     */
    int updateUserIsPoorByHardshipId(@Param("isPoor") Integer isPoor, @Param("hardshipId") Long hardshipId);

    /**
     * 根据组织id获取党员集合
     *
     * @param orgId
     * @return List<SysUserVO>
     */
    List<SysUserVO> selectByOrgIdSelective(Long orgId);

    /**
     * 根据查询实体查询党员选择vo列表
     *
     * @param queryBean 查询实体
     * @return
     */
    List<PartyMemberChooseVO> selectPartyMemberChooseVOListByQueryBean(PartyMemberChooseQueryBean queryBean);

    int selectPartyMemberChooseVOListCountByQueryBean(PartyMemberChooseQueryBean queryBean);

    /**
     * 根据身份证查询指定信息
     *
     * @param idCardNo
     * @return
     */
    PartyMemberChooseVO selectPartyMemberChooseVOByIdCardNo(String idCardNo);

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param avatar 用户头像
     */
    int updateAvatarByUserId(@Param("userId") Long userId, @Param("avatar") String avatar);

    /**
     * desc: 根据用户Id查询单条数据
     *
     * @param userId 用户ID
     * @return sysuser
     * @auther FanYanGen
     * @date 2019-06-24 18:40
     */
    SysUser selectOneByUserId(@Param("userId") Long userId);

}