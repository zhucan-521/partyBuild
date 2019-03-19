package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.EchartsDto;
import com.yizheng.partybuilding.entity.TabPbEduTrain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbEduTrainMapper {
    int deleteByPrimaryKey(Long trainId);

    int insert(TabPbEduTrain record);

    int insertSelective(TabPbEduTrain record);

    TabPbEduTrain selectByPrimaryKey(Long trainId);

    /**
     * 查询详细信息（课程，人员，附件等）
     *
     * @param trainId
     * @return
     */
    TabPbEduTrain bigSelectByKey(Long trainId);

    int updateByPrimaryKeySelective(TabPbEduTrain record);

    int updateByPrimaryKey(TabPbEduTrain record);

    void insertByBatch(List<TabPbEduTrain> attachmentTables);

    List<TabPbEduTrain> selectiveList(TabPbEduTrain train);

    List<EchartsDto> getTrainObjStatistics();

    List<EchartsDto> getTrainYearsStatistics();

    /**
     * 获取统计数据
     *
     * @return
     */
    List<TabPbEduTrain> getStatisticsTest();

    /**
     * 获取统计数据测试详情
     *
     * @param years
     * @return
     */
    List<EchartsDto> getStatistceDetailTest(@Param("years") String years);
}