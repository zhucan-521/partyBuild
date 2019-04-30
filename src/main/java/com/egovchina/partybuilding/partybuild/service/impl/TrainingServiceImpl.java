package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbTrainingDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTraining;
import com.egovchina.partybuilding.partybuild.repository.TabPbTrainingMapper;
import com.egovchina.partybuilding.partybuild.entity.TrainingQueryBean;
import com.egovchina.partybuilding.partybuild.dto.TrainingDTO;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.TrainingService;
import com.egovchina.partybuilding.partybuild.vo.TrainingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    TabPbTrainingMapper tabPbTrainingMapper;

    @Autowired
    TabSysDeptMapper tabSysDeptMapper;

    /**
     * 添加党员培训情况
     * @param
     * @return
     */
    @Override
    @PaddingBaseField
    public int addTraningDTO(TrainingDTO trainingDto) {
        if (trainingDto.getUserId() == null) {
            throw new BusinessDataCheckFailException("缺少userId");
        }
        TabPbTraining tabPbTrainingDto = new TabPbTraining();
        BeanUtil.copyPropertiesIgnoreNull(trainingDto, tabPbTrainingDto);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbTrainingDto);
        return tabPbTrainingMapper.insertSelective(tabPbTrainingDto);
    }


    /**
     * 逻辑删除党员培训情况
     * @param traningId
     * @return
     */
    @Override
    @PaddingBaseField
    public int deletTrainingDTO(Long traningId) {
        TabPbTraining tabPbTraining=new TabPbTrainingDto();
        tabPbTraining.setDelFlag("1");
        tabPbTraining.setTraningId(traningId);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbTraining);
        return tabPbTrainingMapper.updateByPrimaryKeySelective(tabPbTraining);
    }


    /**
     * 列表条件查询党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    @Override
    public List<TrainingVO> getTrainingVOById(TrainingQueryBean tabPbTrainingDto) {
        List<TrainingVO> list = tabPbTrainingMapper.selectiveTabPbTrainingVO(tabPbTrainingDto);
        return list;
    }


    /**
     * 修改党员培训情况 必须附带主键id
     * @param
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int updateTrainingDTO(TrainingDTO trainingDto) {
        if (trainingDto.getTraningId() == null) {
           throw new BusinessDataCheckFailException("缺少主键");
       }
        TabPbTrainingDto tabPbTrainingDto = new TabPbTrainingDto();
        BeanUtil.copyPropertiesIgnoreNull(trainingDto, tabPbTrainingDto);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbTrainingDto);
        return tabPbTrainingMapper.updateByPrimaryKeySelective(tabPbTrainingDto);
    }


    /**
     * 单个培训情况详情查询
     * @param traningId
     * @return
     */
    @Override
    public TrainingVO getTrainingVOById(Long traningId) {
        TrainingVO trainingVO = tabPbTrainingMapper.selectOneVoById(traningId);
        return trainingVO;
    }


}
