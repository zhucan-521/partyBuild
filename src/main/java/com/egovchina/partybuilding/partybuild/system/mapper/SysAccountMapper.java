package com.egovchina.partybuilding.partybuild.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.system.dto.UserAdminDTO;
import com.egovchina.partybuilding.partybuild.system.entity.SysAccount;
import com.egovchina.partybuilding.partybuild.system.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAccountMapper extends BaseMapper<SysAccount>{

    /**
     * 翻页获取用户信息
     * @return
     */
    List<UserAdminDTO> listAll(UserAdminDTO userDto);

    /**
     * 通过用户名查询用户信息（含有角色信息）
     * @param idCardNo 身份证
     * @return userVo
     */
    UserVO selectUserVoByCardNo(String idCardNo);

    /**
     * 通过ID查询用户信息
     * @param id 用户ID
     * @return userVo
     */
    UserVO selectUserVoById(Integer id);

    /**
     * 通过身份证查找已经删除的用户
     * @param idCardNo 身份证
     * @return 用户对象
     */
    SysAccount selectDeletedUserByCardNo(@Param("idCardNo") String idCardNo);

    /**
     * 根据身份证删除用户（假删除）
     * @param idCardNo idCardNo
     * @param userId   userId
     * @return
     */
    Boolean deleteSysUserByCardNoAndUserId(@Param("idCardNo") String idCardNo, @Param("userId") Integer userId);

    /**
     * 修改密码根据id
     * @param sysAccount
     * @return
     */
    Boolean updatePwdById(SysAccount sysAccount);
}