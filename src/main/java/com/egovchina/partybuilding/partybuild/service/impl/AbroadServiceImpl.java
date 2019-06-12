package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.AbroadDTO;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.GoAbroadDTO;
import com.egovchina.partybuilding.partybuild.dto.ReturnAbroadDTO;
import com.egovchina.partybuilding.partybuild.entity.AbroadQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyGroupMember;
import com.egovchina.partybuilding.partybuild.repository.TabPbAbroadMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyGroupMemberMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.AbroadService;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.vo.AbroadVO;
import com.egovchina.partybuilding.partybuild.vo.BackAbroadDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.GoAbroadDetailsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetAndCopyProperties;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc:  出国出境-服务接口实现
 * Created by FanYanGen on 2019/4/22 15:58
 */
@Transactional(rollbackFor = Exception.class)
@Service("abroadService")
public class AbroadServiceImpl implements AbroadService {

    @Autowired
    private TabPbAbroadMapper tabPbAbroadMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private ExtendedInfoService extendedInfoService;

    @Autowired
    private TabPbPartyGroupMemberMapper tabPbPartyGroupMemberMapper;

    /**
     * 减少方式为停止党籍
     **/
    private final Long OUT_TYPE = 59591L;

    /**
     * 出党方式为出国出境
     **/
    private final Long QUIT_TYPE = 59624L;

    @Override
    public int insertGoAbroad(GoAbroadDTO goAbroadDTO) {
        Long userId = goAbroadDTO.getUserId();
        verification(goAbroadDTO.getOrgId(), userId, true);
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(goAbroadDTO, TabPbAbroad.class, false);
        int result = tabPbAbroadMapper.insertSelective(tabPbAbroad);
        /**
         * 党员出国后将该党员移至历史党员和从党小组中删除
         **/
        if (result > 0) {
            // 移到历史党员
            DeletePartyMemberDTO deletePartyMemberDTO = new DeletePartyMemberDTO()
                    .setUserId(userId).setOutType(OUT_TYPE).setQuitType(QUIT_TYPE)
                    .setReduceTime(tabPbAbroad.getAbroadDate()).setWhetherThisClass(false);
            result += extendedInfoService.invalidByUserId(deletePartyMemberDTO);
            // 移出党小组
            TabPbPartyGroupMember tabPbPartyGroupMember = new TabPbPartyGroupMember().setUserId(userId).setDelFlag(1);
            PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroupMember);
            result += tabPbPartyGroupMemberMapper.updateByPrimaryKeySelective(tabPbPartyGroupMember);
        }
        return result;
    }

    @Override
    public PageInfo<AbroadVO> findAbroadVOWithConditions(AbroadQueryBean abroadQueryBean, Page page) {
        PageHelper.startPage(page);
        TabPbAbroad tabPbAbroad = generateTargetAndCopyProperties(abroadQueryBean, TabPbAbroad.class);
        return new PageInfo<>(tabPbAbroadMapper.selectByConditions(tabPbAbroad));
    }

    @Override
    public int deleteAbroad(Long abroadId) {
        TabPbAbroad tabPbAbroad = new TabPbAbroad().setAbroadId(abroadId).setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbAbroad);
        int result = tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
        if (result > 0) {
            result += recoveryIdentity(tabPbAbroadMapper.selectByPrimaryKey(abroadId).getUserId());
        }
        return result;
    }

    @Override
    public int updateAbroad(AbroadDTO abroadDTO) {
        verification(abroadDTO.getOrgId(), abroadDTO.getUserId(), false);
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(abroadDTO, TabPbAbroad.class, true);
        return tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
    }

    @Override
    public int updateReturnAbroad(ReturnAbroadDTO returnAbroadDTO) {
        Long userId = returnAbroadDTO.getUserId();
        verification(returnAbroadDTO.getOrgId(), userId, false);
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(returnAbroadDTO, TabPbAbroad.class, true);
        /**
         * 党员回国后将该党员恢复成出国前的状态
         **/
        int result = tabPbAbroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
        if (result > 0) {
            result += recoveryIdentity(userId);
        }
        return result;
    }

    @Override
    public GoAbroadDetailsVO findGoAbroadDetailsVOByAbroadId(Long abroadId) {
        return generateTargetAndCopyProperties(tabPbAbroadMapper.findAbroadDetailsVOByAbroadId(abroadId), GoAbroadDetailsVO.class);
    }

    @Override
    public BackAbroadDetailsVO findBackAbroadDetailsVOByAbroadId(Long abroadId) {
        return generateTargetAndCopyProperties(tabPbAbroadMapper.findAbroadDetailsVOByAbroadId(abroadId), BackAbroadDetailsVO.class);
    }

    /**
     * desc: 数据校验提示
     *
     * @param orgId      组织ID
     * @param userId     用户ID
     * @param isGoAbroad 是否是出国操作
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verification(Long orgId, Long userId, boolean isGoAbroad) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(orgId)) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (isGoAbroad && !tabSysUserMapper.checkIsExistByUserId(userId)) {
            throw new BusinessDataCheckFailException("该用户不存在");
        }
    }

    /**
     * desc: 恢复党员身份
     *
     * @param userId 用户ID
     * @return int
     * @auther FanYanGen
     * @date 2019-05-15 11:12
     */
    private int recoveryIdentity(Long userId) {
        int result = 0;
        // 从历史党员中恢复
        result += extendedInfoService.restoreUser(userId);
        // 恢复到党小组
        TabPbPartyGroupMember tabPbPartyGroupMember = new TabPbPartyGroupMember().setUserId(userId).setDelFlag(0);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyGroupMember);
        result += tabPbPartyGroupMemberMapper.updateByPrimaryKeySelective(tabPbPartyGroupMember);
        return result;
    }

}
