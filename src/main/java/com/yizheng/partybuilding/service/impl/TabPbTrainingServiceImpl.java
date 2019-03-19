package com.yizheng.partybuilding.service.impl;

import com.yizheng.commons.config.PaddingBaseField;
import com.yizheng.commons.exception.BusinessDataCheckFailException;
import com.yizheng.commons.util.BeanUtil;
import com.yizheng.partybuilding.dto.TabPbTrainingDto;
import com.yizheng.partybuilding.entity.TabPbTraining;
import com.yizheng.partybuilding.repository.TabPbTrainingMapper;
import com.yizheng.partybuilding.service.inf.TabPbTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TabPbTrainingServiceImpl implements TabPbTrainingService {

    @Autowired
    TabPbTrainingMapper tabPbTrainingMapper;

    /**
     * 添加党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    @Override
    @PaddingBaseField
    public int insert(TabPbTrainingDto tabPbTrainingDto) {
        if(tabPbTrainingDto.getUserId()==null){
            throw new BusinessDataCheckFailException("缺少userId");
        }
        return tabPbTrainingMapper.insertSelective(tabPbTrainingDto);
    }


    /**
     * 逻辑删除党员培训情况
     * @param traningId
     * @return
     */
    @Override
    @PaddingBaseField
    public int delete(Long traningId) {
        TabPbTraining tabPbTraining=new TabPbTrainingDto();
        tabPbTraining.setDelFlag("1");
        tabPbTraining.setTraningId(traningId);
        return tabPbTrainingMapper.updateByPrimaryKeySelective(tabPbTraining);
    }


    /**
     * 列表条件查询党员培训情况
     * @param tabPbTrainingDto
     * @return
     */
    @Override
    public List<TabPbTrainingDto> select(TabPbTrainingDto tabPbTrainingDto) {
        List<TabPbTrainingDto> list=tabPbTrainingMapper.selectiveTabPbTrainingDto(tabPbTrainingDto);
        return list;
    }


    /**
     * 修改党员培训情况 必须附带主键id
     * @param tabPbTrainingDto
     * @return
     */
    @Override
    @PaddingBaseField(updateOnly = true)
    public int update(TabPbTrainingDto tabPbTrainingDto) {
       if(tabPbTrainingDto.getTraningId()==null){
           throw new BusinessDataCheckFailException("缺少主键");
       }
        return tabPbTrainingMapper.updateByPrimaryKeySelective(tabPbTrainingDto);
    }


    /**
     * 单个培训情况详情查询
     * @param traningId
     * @return
     */
    @Override
    public TabPbTrainingDto selectTabPbTrainingDtoById(Long traningId) {
        return tabPbTrainingMapper.selectOneById(traningId);
    }


}
