package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.PartyMassesParticipantQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesParticipant;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesParticipantVO;
import com.egovchina.partybuilding.partybuild.vo.SignInToListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:Mapper类
 *
 * @author WuYunJie
 * @date 2019/05/22 10:17:23
 */
@Repository
public interface TabPbPartyMassesParticipantMapper {

    /**
     * Description: 新增
     *
     * @param tabPbPartyMassesParticipant
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:17:23
     */
    int insert(TabPbPartyMassesParticipant tabPbPartyMassesParticipant);

    /**
     * Description: 根据id修改
     *
     * @param tabPbPartyMassesParticipant
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:17:23
     */
    int updateById(TabPbPartyMassesParticipant tabPbPartyMassesParticipant);

    /**
     * Description: 根据id删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:17:23
     */
    int deleteById(Long id);

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/22 10:17:23
     */
    PartyMassesParticipantVO selectById(Long id);

    /**
     * Description: 查询列表
     *
     * @param tabPbPartyMassesParticipantQueryBean
     * @return 查询列表
     * @author WuYunJie
     * @date 2019/05/22 10:17:23
     */
    List<PartyMassesParticipantVO> list(
            PartyMassesParticipantQueryBean tabPbPartyMassesParticipantQueryBean);

    /**
     * 判断是否在人员表中
     *
     * @param userId                人员id
     * @param partyMassesActivityId 党群活动id
     * @return TabPbPartyMassesParticipant
     */
    TabPbPartyMassesParticipant selectByPbParticipant(
            @Param("userId") Long userId, @Param("partyMassesActivityId") Long partyMassesActivityId);

    /**
     * 修改已经在人员表中的人员签到时间
     *
     * @param userId                人员id
     * @param partyMassesActivityId 党群活动id
     * @return int
     * @auther WuYunJie
     * @date 2019/5/22 16:24
     */
    int updateParticipantCheckInTime(
            @Param("userId") Long userId, @Param("partyMassesActivityId") Long partyMassesActivityId);

    /**
     * 查看活动已签到人员跟未签到人的姓名
     *
     * @param partyMassesActivityId 党群活动id
     * @param signType              签到状态
     * @param realName              姓名
     * @return List<SignInToListVO>
     */
    List<SignInToListVO> selectByCheckInList(
            @Param("partyMassesActivityId") Long partyMassesActivityId,
            @Param("signType") Long signType, @Param("realName") String realName);

    /**
     * 修改签到状态
     *
     * @param partyMassesActivityId 党群活动id
     * @param participantIds        修改id集合
     * @return int
     */
    int updateSignIn(
            @Param("partyMassesActivityId") Long partyMassesActivityId,
            @Param("participantIds") List<Long> participantIds);

    /**
     * 查看活动已报名人员跟未报名人的姓名
     *
     * @param partyMassesActivityId 党群活动id
     * @param signType              报名状态
     * @param realName              姓名
     * @return List<SignInToListVO>
     */
    List<SignInToListVO> selectBySignUpList(
            @Param("partyMassesActivityId") Long partyMassesActivityId,
            @Param("signType") Long signType, @Param("realName") String realName);
}
