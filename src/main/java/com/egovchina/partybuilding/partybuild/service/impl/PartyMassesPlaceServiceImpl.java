package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesPlaceDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesPlaceQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesPlace;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesPlaceMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMassesPlaceService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesPlaceVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@Service
public class PartyMassesPlaceServiceImpl implements PartyMassesPlaceService {

    @Autowired
    private TabPbPartyMassesPlaceMapper tabPbPartyMassesPlaceMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesPlaceDTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int save(PartyMassesPlaceDTO partyMassesPlaceDTO) {
        partyMassesPlaceDTO.setPartyMassesPlaceId(null);
        TabPbPartyMassesPlace tabPbPartyMassesPlace = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesPlaceDTO, TabPbPartyMassesPlace.class, false);
        return tabPbPartyMassesPlaceMapper.insert(tabPbPartyMassesPlace);
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesPlaceDTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int updateById(PartyMassesPlaceDTO partyMassesPlaceDTO) {
        TabPbPartyMassesPlace tabPbPartyMassesPlace = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyMassesPlaceDTO, TabPbPartyMassesPlace.class, true);
        return tabPbPartyMassesPlaceMapper.updateById(tabPbPartyMassesPlace);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesPlace tabPbPartyMassesPlace = new TabPbPartyMassesPlace();
        tabPbPartyMassesPlace.setPartyMassesPlaceId(id);
        tabPbPartyMassesPlace.setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbPartyMassesPlace);
        return tabPbPartyMassesPlaceMapper.updateById(tabPbPartyMassesPlace);
    }

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public PartyMassesPlaceVO selectById(Long id) {
        return tabPbPartyMassesPlaceMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesPlaceQueryBean
     * @param page
     * @return
     * @author WuYunJie
     * @date 2019/05/20 21:37:39
     */
    @Override
    public List<PartyMassesPlaceVO> selectList(PartyMassesPlaceQueryBean partyMassesPlaceQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesPlaceMapper.list(partyMassesPlaceQueryBean);
    }
}

