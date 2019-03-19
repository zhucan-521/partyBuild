package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.DevPartyUserDto;
import com.yizheng.partybuilding.dto.PartyApplyConditionsDto;
import com.yizheng.partybuilding.entity.TabPbDevPartyMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenshanlu
 */
@Repository
public interface TabPbDevPartyMemberMapper {
    int deleteByPrimaryKey(Long dpId);

    int insertSelective(TabPbDevPartyMember record);

    TabPbDevPartyMember selectByPrimaryKey(Long dpId);

    TabPbDevPartyMember selectByUserId(Long userId);

    int updateByPrimaryKeySelective(TabPbDevPartyMember record);

    List<TabPbDevPartyMember> checkUser(TabPbDevPartyMember user);

    List<TabPbDevPartyMember> list(TabPbDevPartyMember user);

    /**
     * 判断用户是否在指定的环节中
     *
     * @param userName 用户名
     * @param idCardNo 身份证号
     * @param isLost   是否失联
     * @param status   环节状态
     * @return
     */
    List<TabPbDevPartyMember> selectByUserAndStatus(@Param("userName") String userName,
                                                    @Param("idCardNo") String idCardNo,
                                                    @Param("isLost") Short isLost,
                                                    @Param("status") List<Long> status);


    /**
     * 查询发展党员列表, 返回发展党员信息
     * 分页显示
     *
     * @return
     */
    List<DevPartyUserDto> selectDevParty(PartyApplyConditionsDto conditionsDto);

}