package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.CommentaryDTO;
import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.DoubleCommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbDoubleCommentary;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import com.github.pagehelper.PageInfo;

/**
 * desc: 双述双评-服务接口
 * Created by FanYanGen on 2019/4/24 16:17
 */
public interface DoubleCommentaryService {

    int deleteByPrimaryKey(Long commentaryId);

    int insert(TabPbDoubleCommentary record);

    int insertSelective(TabPbDoubleCommentary record);

    int updateByPrimaryKeySelective(TabPbDoubleCommentary record);

    int updateByPrimaryKeyWithBLOBs(TabPbDoubleCommentary record);

    int updateByPrimaryKey(TabPbDoubleCommentary record);

    /**
     * desc: 根据组合条件查询双述双评列表
     *
     * @param page                分页实体
     * @param commentaryQueryBean 查询条件
     * @param orgRange            组织条件集合
     * @return List<CommentaryVO>
     * @author FanYanGen
     * @date 2019/4/24 16:28
     **/
    PageInfo<CommentaryVO> findCommentaryVOWithConditions(Page page, CommentaryQueryBean commentaryQueryBean, OrgRange orgRange);

    /**
     * desc: 新增双述双评
     *
     * @param commentaryDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 17:55
     **/
    int insertCommentary(CommentaryDTO commentaryDTO);

    /**
     * desc: 更新双述双评
     *
     * @param commentaryDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 19:29
     **/
    int updateCommentary(CommentaryDTO commentaryDTO);

    /**
     * desc: 根据主键ID查询详情
     *
     * @param commentaryId 主键ID
     * @return CommentaryVO
     * @author FanYanGen
     * @date 2019/4/24 19:42
     **/
    CommentaryVO findCommentaryVOByCommentaryId(Long commentaryId);

    /**
     * desc: 根据主键ID逻辑删除数据
     *
     * @param commentaryId 主键ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 19:50
     **/
    int deleteCommentary(Long commentaryId);

    /**
     * desc: 双述双评审核
     *
     * @param doubleCommentaryQueryBean querybean
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 20:03
     **/
    int verifyCommentary(DoubleCommentaryQueryBean doubleCommentaryQueryBean);

}