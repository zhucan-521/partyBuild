package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.partybuild.dto.PartyMassesParticipantDTO;
import com.egovchina.partybuilding.partybuild.entity.PartyMassesParticipantQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPartyMassesParticipant;
import com.egovchina.partybuilding.partybuild.repository.TabPbPartyMassesParticipantMapper;
import com.egovchina.partybuilding.partybuild.service.PartyMassesParticipantService;
import com.egovchina.partybuilding.partybuild.vo.PartyMassesParticipantVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;


/**
 * Description:服务类
 *
 * @author WuYunJie
 * @date 2019/05/22 10:16:01
 */
@Service
public class PartyMassesParticipantServiceImpl implements PartyMassesParticipantService {

    @Autowired
    private TabPbPartyMassesParticipantMapper tabPbPartyMassesParticipantMapper;

    /**
     * Description: 新增
     *
     * @param partyMassesParticipantDTO
     * @return 插入成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    @Override
    public int save(PartyMassesParticipantDTO partyMassesParticipantDTO) {
        TabPbPartyMassesParticipant partyMassesParticipant =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesParticipantDTO, TabPbPartyMassesParticipant.class, false);
        return tabPbPartyMassesParticipantMapper.insert(partyMassesParticipant);
    }

    /**
     * Description: 根据id修改
     *
     * @param partyMassesParticipantDTO
     * @return 修改成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    @Override
    public int updateById(PartyMassesParticipantDTO partyMassesParticipantDTO) {
        TabPbPartyMassesParticipant partyMassesParticipant =
                BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(
                        partyMassesParticipantDTO, TabPbPartyMassesParticipant.class, true);
        return tabPbPartyMassesParticipantMapper.updateById(partyMassesParticipant);
    }

    /**
     * Description: 根据id逻辑删除
     *
     * @param id
     * @return 删除成功记录数
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    @Override
    public int deleteById(Long id) {
        TabPbPartyMassesParticipant partyMassesParticipant = new TabPbPartyMassesParticipant();
        partyMassesParticipant.setPartyMassesActivityId(id);
        partyMassesParticipant.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(partyMassesParticipant);
        return tabPbPartyMassesParticipantMapper.updateById(partyMassesParticipant);
    }

    /**
     * Description: 根据id查找
     *
     * @param id
     * @return VO对象
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    @Override
    public PartyMassesParticipantVO selectById(Long id) {
        return tabPbPartyMassesParticipantMapper.selectById(id);
    }

    /**
     * Description: 查询列表
     *
     * @param partyMassesParticipantQueryBean
     * @param page
     * @return
     * @author WuYunJie
     * @date 2019/05/22 10:16:01
     */
    @Override
    public List<PartyMassesParticipantVO> selectList(PartyMassesParticipantQueryBean partyMassesParticipantQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbPartyMassesParticipantMapper.list(partyMassesParticipantQueryBean);
    }
}

