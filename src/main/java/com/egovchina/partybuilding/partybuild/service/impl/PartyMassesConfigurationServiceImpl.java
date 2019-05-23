package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesConfigurationDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesConfigurationQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesConfiguration;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesConfigurationMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMassesConfigurationService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesConfigurationVO;
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
public class PartyMassesConfigurationServiceImpl implements PartyMassesConfigurationService {

    @Autowired
    private TabPbPartyMassesConfigurationMapper tabPbPartyMassesConfigurationMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesConfigurationDTO 党群配置DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int save(PartyMassesConfigurationDTO partyMassesConfigurationDTO) {
        partyMassesConfigurationDTO.setPartyMassesConfigurationId(null);
        TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesConfigurationDTO, TabPbPartyMassesConfiguration.class, false);
        return tabPbPartyMassesConfigurationMapper.insert(tabPbPartyMassesConfiguration);
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesConfigurationDTO 党群配置DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int updateById(PartyMassesConfigurationDTO partyMassesConfigurationDTO) {
        TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesConfigurationDTO, TabPbPartyMassesConfiguration.class, true);
        return tabPbPartyMassesConfigurationMapper.updateById(tabPbPartyMassesConfiguration);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群配置id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration = new TabPbPartyMassesConfiguration();
        tabPbPartyMassesConfiguration.setPartyMassesConfigurationId(id);
        tabPbPartyMassesConfiguration.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesConfiguration);
        return tabPbPartyMassesConfigurationMapper.updateById(tabPbPartyMassesConfiguration);
    }

    /**
     * Description: 根据id查找
     *
     * @param id 党群配置id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public PartyMassesConfigurationVO selectById(Long id) {
        return tabPbPartyMassesConfigurationMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesConfigurationQueryBean 党群配置查询实体
     * @param page                              分页
     * @return PartyMassesConfigurationVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public List<PartyMassesConfigurationVO> selectList(PartyMassesConfigurationQueryBean partyMassesConfigurationQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesConfigurationMapper.list(partyMassesConfigurationQueryBean);
    }
}

