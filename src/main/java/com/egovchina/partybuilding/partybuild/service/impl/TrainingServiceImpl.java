package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.TrainingDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbTraining;
import com.egovchina.partybuilding.partybuild.entity.TrainingQueryBean;
import com.egovchina.partybuilding.partybuild.repository.TabPbTrainingMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.service.TrainingService;
import com.egovchina.partybuilding.partybuild.vo.TrainingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;


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
    public int addTraningDTO(TrainingDTO trainingDto) {
        if(trainingDto.getUserId()==null){
            throw new BusinessDataCheckFailException("缺少userId");
        }
        TabPbTraining tabPbTrainingDto =generateTargetCopyPropertiesAndPaddingBaseField(trainingDto,TabPbTraining.class,false);
        BeanUtil.copyPropertiesIgnoreNull(trainingDto,tabPbTrainingDto);
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbTrainingDto);
        return tabPbTrainingMapper.insertSelective(tabPbTrainingDto);
    }

    /**
     * 逻辑删除党员培训情况
     * @param trainingId
     * @return
     */
    @Override
    public int logicDeleteTrainingDTO(Long trainingId) {
        TabPbTraining tabPbTraining = new TabPbTraining();
        tabPbTraining.setDelFlag(CommonConstant.STATUS_DEL);
        tabPbTraining.setTraningId(trainingId);
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
        List<TrainingVO> list=tabPbTrainingMapper.selectiveTabPbTrainingVO(tabPbTrainingDto);
        return list;
    }

    /**
     * 修改党员培训情况 必须附带主键id
     * @param
     * @return
     */
    @Override
    public int updateTrainingDTO(TrainingDTO trainingDTO) {
        if (trainingDTO.getTraningId() == null) {
           throw new BusinessDataCheckFailException("缺少主键");
        }
        TabPbTraining tabPbTraining = generateTargetCopyPropertiesAndPaddingBaseField(
                trainingDTO, TabPbTraining.class, true);
        return tabPbTrainingMapper.updateByPrimaryKeySelective(tabPbTraining);
    }

    /**
     * 单个培训情况详情查询
     * @param traningId
     * @return
     */
    @Override
    public TrainingVO getTrainingVOById(Long traningId) {
        TrainingVO trainingVO=tabPbTrainingMapper.selectOneVoById(traningId);
        return trainingVO;
    }

}
