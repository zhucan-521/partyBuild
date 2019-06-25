package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDeptSecretary;
import com.egovchina.partybuilding.partybuild.repository.TabPbDeptSecretaryMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.egovchina.partybuilding.partybuild.vo.SecretarysVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * @author zhucan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SecretaryServiceImpl implements SecretaryService {

    @Autowired
    private TabPbDeptSecretaryMapper tabPbDeptSecretaryMapper;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    /**
     * 新增书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int addSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        TabPbDeptSecretary tabPbDeptSecretary = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, false);
        int flag = tabPbDeptSecretaryMapper.insertSelective(tabPbDeptSecretary);
        if (flag > 0) {
            SysUser sysUser = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, SysUser.class, true);
            tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
        return flag;
    }

    /**
     * 删除书记
     *
     * @param secretaryId
     * @return
     */
    @Override
    public int removeSecretary(Long secretaryId) {
        TabPbDeptSecretary tabPbDeptSecretary = new TabPbDeptSecretary().setDelFlag(CommonConstant.STATUS_DEL).setSecretaryId(secretaryId);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbDeptSecretary);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 修改书记
     *
     * @param secretaryMemberDTO
     * @return
     */
    @Override
    public int updateSecretary(SecretaryMemberDTO secretaryMemberDTO) {
        SysUser sysUser = new SysUser();
        Long userId = tabSysUserMapper.SelectUserIdByIDcard(secretaryMemberDTO.getIdCardNo());
        BeanUtil.copyPropertiesIgnoreNull(secretaryMemberDTO, sysUser);
        sysUser.setUserId(userId);
        tabSysUserMapper.updateByPrimaryKeySelective(sysUser);
        TabPbDeptSecretary tabPbDeptSecretary = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(secretaryMemberDTO, TabPbDeptSecretary.class, true);
        return tabPbDeptSecretaryMapper.updateByPrimaryKeySelective(tabPbDeptSecretary);
    }

    /**
     * 根据书记id获取书记详情
     *
     * @param secretaryId
     * @return
     */
    @Override
    public SecretaryMemberVO selectSecretaryBySecretaryId(Long secretaryId) {
        return tabPbDeptSecretaryMapper.selectSecretaryVOBySecretaryId(secretaryId);
    }

    /**
     * 列表查询书记
     *
     * @param secretaryMemberQueryBean
     * @return
     */
    @Override
    public List<SecretarysVO> selectSecretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbDeptSecretaryMapper.selectSecretaryVOList(secretaryMemberQueryBean);
    }

}
