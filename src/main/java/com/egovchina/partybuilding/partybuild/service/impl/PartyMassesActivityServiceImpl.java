package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesActivityDTO;
import com.egovchina.partybuilding.partybuild.dto.SignInDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesActivityQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesActivity;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesParticipant;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesActivityMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesParticipantMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PartyMassesActivityService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesActivityVO;
import com.egovchina.partybuilding.partybuild.vo.SignInToListVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Service
public class PartyMassesActivityServiceImpl implements PartyMassesActivityService {

    @Autowired
    private TabPbPartyMassesActivityMapper tabPbPartyMassesActivityMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbPartyMassesParticipantMapper tabPbPartyMassesParticipantMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesActivityDTO 党群活动DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Transactional
    @Override
    public int save(PartyMassesActivityDTO partyMassesActivityDTO) {
        partyMassesActivityDTO.setPartyMassesActivityId(null);
        TabPbPartyMassesActivity partyMassesActivity =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesActivityDTO, TabPbPartyMassesActivity.class, false);
        int result = 0;
        result += tabPbPartyMassesActivityMapper.insert(partyMassesActivity);
        result += tabPbAttachmentService.intelligentOperation(
                partyMassesActivityDTO.getAttachments(),
                partyMassesActivity.getPartyMassesActivityId(), AttachmentType.PARTY_MASSES);
        return result;
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesActivityDTO 党群活动DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int updateById(PartyMassesActivityDTO partyMassesActivityDTO) {
        TabPbPartyMassesActivity partyMassesActivity =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesActivityDTO, TabPbPartyMassesActivity.class, true);
        return tabPbPartyMassesActivityMapper.updateById(partyMassesActivity);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群活动id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesActivity tabPbPartyMassesActivity = new TabPbPartyMassesActivity();
        tabPbPartyMassesActivity.setPartyMassesActivityId(id);
        tabPbPartyMassesActivity.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesActivity);
        return tabPbPartyMassesActivityMapper.updateById(tabPbPartyMassesActivity);
    }

    /**
     * Description: 根据id查找
     *
     * @param id 党群活动id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public PartyMassesActivityVO selectById(Long id) {
        return tabPbPartyMassesActivityMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesActivityQueryBean 党群活动查询实体
     * @param page                         分页
     * @return 结果集
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public List<PartyMassesActivityVO> selectList(PartyMassesActivityQueryBean partyMassesActivityQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesActivityMapper.list(partyMassesActivityQueryBean);
    }

    /**
     * 签到接口加人/二维码签到
     *
     * @param partyMassesActivityId 党群活动id
     * @param idCardNo              身份证
     * @return int
     */
    @Override
    public int insertSignInWithPeople(Long partyMassesActivityId, String idCardNo) {
        //判断该党群活动是否存在
        PartyMassesActivityVO partyMassesActivityVO = tabPbPartyMassesActivityMapper.selectById(partyMassesActivityId);
        if (partyMassesActivityVO == null) {
            throw new BusinessDataCheckFailException("该党群活动不存在");
        }
        //查询出人员id
        SysUser sysUser = tabSysUserMapper.selectUserByIdCardNo(idCardNo);
        //判断该人是否存在人员表中
        TabPbPartyMassesParticipant dbTabPbPartyMassesParticipant =
                tabPbPartyMassesParticipantMapper.selectByPbParticipant(sysUser.getUserId(), partyMassesActivityId);
        int result = 0;
        if (dbTabPbPartyMassesParticipant != null) {
            result += tabPbPartyMassesParticipantMapper.updateParticipantCheckInTime(sysUser.getUserId(), partyMassesActivityId);
        } else {
            //如果党群活动没人就直接添加进人员表直接签到
            TabPbPartyMassesParticipant tabPbPartyMassesParticipant = new TabPbPartyMassesParticipant();
            tabPbPartyMassesParticipant.setPartyMassesActivityId(partyMassesActivityId)
                    .setUserId(sysUser.getUserId())
                    .setRealName(sysUser.getRealname())
                    .setSigninTime(new Date());
            paddingBaseFiled(tabPbPartyMassesParticipant);
            result += tabPbPartyMassesParticipantMapper.insert(tabPbPartyMassesParticipant);
        }
        return result;
    }

    /**
     * 党群签到变更
     *
     * @param signInDTO 党群签到DTO
     * @return int
     * @auther WuYunJie
     * @date 2019/5/22 15:47
     */
    @Override
    public int updateSignIn(SignInDTO signInDTO) {
        Long partyMassesActivityId = signInDTO.getPartyMassesActivityId();
        List<Long> participantIds = signInDTO.getParticipantIds();
        PartyMassesActivityVO partyMassesActivityVO = tabPbPartyMassesActivityMapper.selectById(partyMassesActivityId);
        if (partyMassesActivityVO != null) {
            if (partyMassesActivityVO.getFinishedTime() != null) {
                Date date = new Date();
                if (date.after(partyMassesActivityVO.getFinishedTime())) {
                    throw new BusinessDataCheckFailException("活动已结束");
                }
            }
            return tabPbPartyMassesParticipantMapper.updateSignIn(partyMassesActivityId, participantIds);
        }
        throw new BusinessDataCheckFailException("活动数据不存在");
    }

    /**
     * 查询签到情况列表
     *
     * @param partyMassesActivityId 党群活动id
     * @param signType              签到状态
     * @param page                  分页
     * @param realName              名字
     * @return SignInToListVO
     */
    @Override
    public List<SignInToListVO> selectSignInVOListByCondition(Long partyMassesActivityId, Long signType, Page page, String realName) {
        PageHelper.startPage(page);
        return tabPbPartyMassesParticipantMapper.selectByCheckInList(partyMassesActivityId, signType, realName);
    }
}

