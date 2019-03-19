package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.partybuilding.dto.EchartsDto;
import com.yizheng.partybuilding.entity.TabPbEduTrain;
import com.yizheng.partybuilding.entity.TabPbEduTrainComment;
import com.yizheng.partybuilding.entity.TabPbEduTrainDynamic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author: huang
 * Date: 2018/12/10
 */
public interface ITabPbEduTrainService {
    int insert(TabPbEduTrain train);

    int deleteById(Integer id);

    int updateById(TabPbEduTrain train);

    TabPbEduTrain selectById(Integer id);

    PageInfo<TabPbEduTrain> selectList(Map<String, Object> params);

    int insertTrainDynamic(TabPbEduTrainDynamic trainDynamic);

    int editTrainDynamic(TabPbEduTrainDynamic trainDynamic);

    Map<String, List<EchartsDto>> getStatistics();

    PageInfo<TabPbEduTrainDynamic> selectDynamicList(Map<String, Object> params);

    boolean judgeRelease(Long trainId);

    TabPbEduTrainDynamic getTrainDynamicById(Integer id);

    boolean batchImport(String fileName, MultipartFile file,Long trainId) throws Exception ;

    boolean evaluation(TabPbEduTrainComment setResult);

    boolean isComment(Long courseId);

    boolean setAdmin(Long trainObjId);

    boolean sendNotification(Long trainId);

    boolean report(Long trainObjId);

    List<TabPbEduTrain> getStatisticsTest();
}
