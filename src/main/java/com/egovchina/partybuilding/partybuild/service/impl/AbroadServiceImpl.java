package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.GoAbroadDTO;
import com.egovchina.partybuilding.partybuild.dto.ReturnAbroadDTO;
import com.egovchina.partybuilding.partybuild.entity.AbroadQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbAbroad;
import com.egovchina.partybuilding.partybuild.repository.TabPbAbroadMapper;
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

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;

/**
 * desc:  出国出境-服务接口实现
 * Created by FanYanGen on 2019/4/22 15:58
 */
@Transactional(rollbackFor = Exception.class)
@Service("abroadService")
public class AbroadServiceImpl implements AbroadService {

    @Autowired
    private TabPbAbroadMapper abroadMapper;

    @Autowired
    private TabSysDeptMapper deptMapper;

    @Autowired
    private TabSysUserMapper sysUserMapper;

    @Autowired
    private ExtendedInfoService extendedInfoService;

    @Override
    public int insertGoAbroad(GoAbroadDTO goAbroadDTO) {
        Long userId = goAbroadDTO.getUserId();
        verification(goAbroadDTO.getOrgId(), userId);
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(goAbroadDTO, TabPbAbroad.class, false);
        int result = abroadMapper.insertSelective(tabPbAbroad);
        // 党员出国后将该党员移至历史党员
        if (0 < result) {
            DeletePartyMemberDTO deletePartyMemberDTO = new DeletePartyMemberDTO();
            deletePartyMemberDTO.setUserId(userId);
            deletePartyMemberDTO.setOutType(6L);
            extendedInfoService.updateByUserId(deletePartyMemberDTO);
        }
        return result;
    }

    @Override
    public PageInfo<AbroadVO> findAbroadVOWithConditions(AbroadQueryBean abroadQueryBean, Page page) {
        PageHelper.startPage(page);
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(abroadQueryBean, TabPbAbroad.class, false);
        return new PageInfo<>(abroadMapper.selectByConditions(tabPbAbroad));
    }

    @Override
    public int deleteAbroad(Long abroadId) {
        TabPbAbroad tabPbAbroad = new TabPbAbroad();
        tabPbAbroad.setDelFlag(CommonConstant.STATUS_DEL);
        tabPbAbroad.setAbroadId(abroadId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbAbroad);
        return abroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
    }

    @Override
    public int updateGoAbroad(GoAbroadDTO goAbroadDTO) {
        verification(goAbroadDTO.getOrgId(), goAbroadDTO.getUserId());
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(goAbroadDTO, TabPbAbroad.class, true);
        return abroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
    }

    @Override
    public int updateReturnAbroad(ReturnAbroadDTO returnAbroadDTO) {
        verification(returnAbroadDTO.getOrgId(), returnAbroadDTO.getUserId());
        TabPbAbroad tabPbAbroad = generateTargetCopyPropertiesAndPaddingBaseField(returnAbroadDTO, TabPbAbroad.class, true);
        return abroadMapper.updateByPrimaryKeySelective(tabPbAbroad);
    }

    @Override
    public GoAbroadDetailsVO findGoAbroadDetailsVOByAbroadId(Long abroadId) {
        return generateTargetCopyPropertiesAndPaddingBaseField(abroadMapper.findAbroadDetailsVOByAbroadId(abroadId), GoAbroadDetailsVO.class, false);
    }

    @Override
    public BackAbroadDetailsVO findBackAbroadDetailsVOByAbroadId(Long abroadId) {
        return generateTargetCopyPropertiesAndPaddingBaseField(abroadMapper.findAbroadDetailsVOByAbroadId(abroadId), BackAbroadDetailsVO.class, false);
    }

    /**
     * desc: 数据校验提示
     *
     * @param orgId  组织ID
     * @param userId 用户ID
     * @author FanYanGen
     * @date 2019/4/24 21:02
     **/
    private void verification(Long orgId, Long userId) {
        if (!deptMapper.checkIsExistByOrgId(orgId)) {
            throw new BusinessDataCheckFailException("该组织不存在");
        }
        if (!sysUserMapper.checkIsExistByUserId(userId)) {
            throw new BusinessDataCheckFailException("该用户不存在");
        }
    }

}
