package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.DevPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyApplyQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDevPartyMember;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FanYanGen on 2019/4/23 10:26
 */
@Repository
public interface TabPbDevPartyMemberMapper {

    int deleteByPrimaryKey(Long dpId);

    int insertSelective(TabPbDevPartyMember record);

    TabPbDevPartyMember selectByPrimaryKey(Long dpId);

    TabPbDevPartyMember selectByUserId(Long userId);

    int updateByPrimaryKeySelective(TabPbDevPartyMember record);

    List<TabPbDevPartyMember> checkUser(TabPbDevPartyMember user);

    /**
     * desc: 查询发展党员
     *
     * @param partyApplyQueryBean 查询条件
     * @return List<DevPartyVO>
     * @author FanYanGen
     * @date 2019/4/23 16:56
     **/
    List<DevPartyVO> selectDevParty(PartyApplyQueryBean partyApplyQueryBean);

    /**
     * desc: 根据身份证号码查询党员
     *
     * @param idCard 身份证
     * @return SysUser
     * @author FanYanGen
     * @date 2019/4/23 15:39
     **/
    SysUser selectUserByIdCardNo(String idCard);

    /**
     * desc: 分页获取党员发展列表信息
     *
     * @param devPartyMemberDTO dto
     * @return List<TabPbDevPartyMember>
     * @author FanYanGen
     * @date 2019/4/23 15:01
     **/
    List<DevPartyMemberVO> selectList(DevPartyMemberDTO devPartyMemberDTO);

    /**
     * desc: 根据主键id逻辑删除
     *
     * @param dpId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 14:23
     **/
    int deleteByDeptId(Long dpId);

    /**
     * desc: 更新党员发展步骤
     *
     * @param devPartyMemberDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 14:50
     **/
    int updateDevStep(DevPartyMemberDTO devPartyMemberDTO);

    /**
     * 判断用户是否在指定的环节中
     *
     * @param userName 用户名
     * @param idCardNo 身份证号
     * @param isLost   是否失联
     * @param status   环节状态
     * @return
     */
    List<TabPbDevPartyMember> selectByUserAndStatus(@Param("userName") String userName, @Param("idCardNo") String idCardNo, @Param("isLost") Short isLost, @Param("status") List<Long> status);

}