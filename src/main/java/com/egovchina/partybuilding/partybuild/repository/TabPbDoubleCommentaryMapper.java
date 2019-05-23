package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;
import com.egovchina.partybuilding.partybuild.vo.CommentaryDetailsVO;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * desc: 根据查询条件返回列表
     *
     * @param commentaryQueryBean 查询条件
     * @return List<CommentaryVO>
     * @auther FanYanGen
     * @date 2019-05-22 20:32
     */
    List<CommentaryVO> selectWithConditions(CommentaryQueryBean commentaryQueryBean);

    /**
     * desc: 根据主键ID获取详情
     *
     * @param commentaryId 主键ID
     * @return CommentaryVO
     * @auther FanYanGen
     * @date 2019-05-22 20:35
     */
    CommentaryDetailsVO getCommentDetailByCommentId(Long commentaryId);

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
     * @param orgId        组织ID
     * @param commentaryId 双述书评
     * @param planYear     年度
     * @return boolean
     * @author FanYanGen
     * @date 2019/5/7 11:16
     **/
    Boolean checkIsExistByPlanYear(@Param("orgId") Long orgId, @Param("commentaryId") Long commentaryId, @Param("planYear") String planYear);



}