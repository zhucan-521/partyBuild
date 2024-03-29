package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesDTO;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesConfigurationMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesMatterMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesPlaceMapper;
import com.egovchina.partybuilding.partybuild.service.*;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesTree;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Service
public class PartyMassesServiceImpl implements PartyMassesService {

    @Autowired
    private TabPbPartyMassesMapper tabPbPartyMassesMapper;

    @Autowired
    private ITabPbAttachmentService tabPbAttachmentService;

    @Autowired
    private TabPbPartyMassesPlaceMapper partyMassesPlaceMapper;

    @Autowired
    private TabPbPartyMassesMatterMapper partyMassesMatterMapper;

    @Autowired
    private TabPbPartyMassesConfigurationMapper partyMassesConfigurationMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesDTO 党群DTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int save(PartyMassesDTO partyMassesDTO) {
        partyMassesDTO.setPartyMassesId(null);
        //校验
        dataValidation(partyMassesDTO);
        if(tabPbPartyMassesMapper.checkAdministrativeDivisionExistPartyMasses(
                partyMassesDTO.getAdministrativeDivisionId())){
            throw new BusinessDataCheckFailException("该行政区划下已存在党群服务中心,添加失败");
        }
        TabPbPartyMasses tabPbPartyMasses =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesDTO, TabPbPartyMasses.class, false);
        int result = 0;
        result += tabPbPartyMassesMapper.insert(tabPbPartyMasses);
        result += tabPbAttachmentService.intelligentOperation(
                partyMassesDTO.getAttachments(),
                tabPbPartyMasses.getPartyMassesId(), AttachmentType.PARTY_MASSES);
        return result;
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesDTO 党群DTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int updateById(PartyMassesDTO partyMassesDTO) {
        //校验
        dataValidation(partyMassesDTO);
        TabPbPartyMasses tabPbPartyMasses = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesDTO, TabPbPartyMasses.class, true);
        int result = 0;
        result += tabPbAttachmentService.intelligentOperation(
                partyMassesDTO.getAttachments(),
                tabPbPartyMasses.getPartyMassesId(), AttachmentType.PARTY_MASSES);
        result += tabPbPartyMassesMapper.updateById(tabPbPartyMasses);
        return result;
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id 党群id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Transactional
    @Override
    public int deleteById(Long id) {
        int result = 0;
        TabPbPartyMasses tabPbPartyMasses = new TabPbPartyMasses();
        tabPbPartyMasses.setPartyMassesId(id);
        tabPbPartyMasses.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMasses);
        result += tabPbPartyMassesMapper.updateById(tabPbPartyMasses);
        TabPbPartyMassesPlace tabPbPartyMassesPlace = new TabPbPartyMassesPlace();
        tabPbPartyMassesPlace.setPartyMassesId(id);
        tabPbPartyMassesPlace.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesPlace);
        result += partyMassesPlaceMapper.logicDeleteByPartyMassesId(tabPbPartyMassesPlace);
        TabPbPartyMassesMatter tabPbPartyMassesMatter = new TabPbPartyMassesMatter();
        tabPbPartyMassesMatter.setPartyMassesId(id);
        tabPbPartyMassesMatter.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesMatter);
        result += partyMassesMatterMapper.logicDeleteByPartyMassesId(tabPbPartyMassesMatter);
        TabPbPartyMassesConfiguration tabPbPartyMassesConfiguration = new TabPbPartyMassesConfiguration();
        tabPbPartyMassesConfiguration.setPartyMassesId(id);
        tabPbPartyMassesConfiguration.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(tabPbPartyMassesConfiguration);
        result += partyMassesConfigurationMapper.logicDeleteByPartyMassesId(tabPbPartyMassesConfiguration);
        return result;
    }

    /**
     * Description: 根据id查找
     *
     * @param id 党群id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public PartyMassesVO selectById(Long id) {
        return tabPbPartyMassesMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesQueryBean 党群查询实体
     * @param page                 分页
     * @return PartyMassesVO
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public List<PartyMassesVO> selectList(PartyMassesQueryBean partyMassesQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesMapper.list(partyMassesQueryBean);
    }

    @Override
    public List<PartyMassesTree> getPartyMassesListByOrgParentId(Long parentId) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("parentId", parentId);
        List<PartyMassesTree> partyMassesTrees = tabPbPartyMassesMapper.selectListByCondition(conditions);
        return partyMassesTrees.stream().map(partyMassesTree -> {
            PartyMassesTree node = new PartyMassesTree();
            node.setId(partyMassesTree.getId());
            node.setParentId(partyMassesTree.getParentId());
            node.setPartyMassesId(partyMassesTree.getPartyMassesId());
            node.setPartyMassesName(partyMassesTree.getPartyMassesName());
            return node;
        }).collect(Collectors.toList());
    }

    private void dataValidation(PartyMassesDTO partyMassesDTO) {
        if (tabPbPartyMassesMapper.checkIsExistPartyMassesName(
                partyMassesDTO.getPartyMassesName(), partyMassesDTO.getPartyMassesId())) {
            throw new BusinessDataCheckFailException("党群名字已存在");
        }
    }
}

