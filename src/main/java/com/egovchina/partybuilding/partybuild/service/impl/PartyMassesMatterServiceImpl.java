package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesMatterDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesMatterQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesMatter;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesMatterMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PartyMassesMatterService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesMatterVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Service
public class PartyMassesMatterServiceImpl implements PartyMassesMatterService {

    @Autowired
    private TabPbPartyMassesMatterMapper tabPbPartyMassesMatterMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    /**
     * Description: 新增
     *
     * @param partyMassesMatterDTO 党群服务事项DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int save(PartyMassesMatterDTO partyMassesMatterDTO) {
        partyMassesMatterDTO.setPartyMassesMatterId(null);
        TabPbPartyMassesMatter tabPbPartyMassesMatter = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesMatterDTO, TabPbPartyMassesMatter.class, false);
        int result = 0;
        result += tabPbPartyMassesMatterMapper.insert(tabPbPartyMassesMatter);
        result += tabPbAttachmentService.intelligentOperation(
                partyMassesMatterDTO.getAttachments(),
                tabPbPartyMassesMatter.getPartyMassesMatterId(), AttachmentType.PARTY_MASSES_MATTER);
        return result;
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesMatterDTO 党群服务事项DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int updateById(PartyMassesMatterDTO partyMassesMatterDTO) {
        TabPbPartyMassesMatter tabPbPartyMassesMatter = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesMatterDTO, TabPbPartyMassesMatter.class, true);
        int result = 0;
        result += tabPbAttachmentService.intelligentOperation(
                partyMassesMatterDTO.getAttachments(),
                tabPbPartyMassesMatter.getPartyMassesMatterId(), AttachmentType.PARTY_MASSES_MATTER);
        result += tabPbPartyMassesMatterMapper.updateById(tabPbPartyMassesMatter);
        return result;
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群服务事项id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesMatter tabPbPartyMassesMatter = new TabPbPartyMassesMatter();
        tabPbPartyMassesMatter.setPartyMassesMatterId(id);
        tabPbPartyMassesMatter.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesMatter);
        return tabPbPartyMassesMatterMapper.updateById(tabPbPartyMassesMatter);
    }

    /**
     * Description: 根据id查找
     *
     * @param id 党群服务事项id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public PartyMassesMatterVO selectById(Long id) {
        return tabPbPartyMassesMatterMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesMatterQueryBean 党群服务事项查询实体
     * @param page                       分页
     * @return PartyMassesMatterVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public List<PartyMassesMatterVO> selectList(PartyMassesMatterQueryBean partyMassesMatterQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesMatterMapper.list(partyMassesMatterQueryBean);
    }
}

