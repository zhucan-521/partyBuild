package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbDoubleCommentaryMapper {

    int deleteByPrimaryKey(Long commentaryId);

    int insert(TabPbDoubleCommentary record);

    int insertSelective(TabPbDoubleCommentary record);

    CommentaryVO selectByPrimaryKey(Long commentaryId);

    int updateByPrimaryKeySelective(TabPbDoubleCommentary record);

    int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record);

    int updateByPrimaryKey(TabPbDoubleCommentary record);

    int logicDeleteById(Long commentaryId);

    List<CommentaryVO> selectWithConditions(CommentaryQueryBean commentaryQueryBean);

    /**
     * desc: 检验数据是否存在
     *
     * @param commentaryId 主键ID
     * @return boolean
     * @author FanYanGen
     * @date 2019/4/24 20:49
     **/
    boolean checkIsExistByCommentId(Long commentaryId);

    /**
     * desc: 根据年度查询该数据是否存在
     *
     * @param planYear 年度
     * @return boolean
     * @author FanYanGen
     * @date 2019/5/7 11:16
     **/
    boolean checkIsExistByPlanYear(String planYear);

}