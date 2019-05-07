package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.DoubleCommentaryDTO;
import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.DoubleCommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import com.github.pagehelper.PageInfo;

/**
 * desc: 双述双评-服务接口
 * Created by FanYanGen on 2019/4/24 16:17
 */
public interface DoubleCommentaryService {

    /**
     * desc: 根据组合条件查询双述双评列表
     *
     * @param commentaryQueryBean 查询条件
     * @param page                分页实体
     * @return List<CommentaryVO>
     * @author FanYanGen
     * @date 2019/4/24 16:28
     **/
    PageInfo<CommentaryVO> findCommentaryVOWithConditions(CommentaryQueryBean commentaryQueryBean, Page page);

    /**
     * desc: 新增双述双评
     *
     * @param doubleCommentaryDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 17:55
     **/
    int insertCommentary(DoubleCommentaryDTO doubleCommentaryDTO);

    /**
     * desc: 更新双述双评
     *
     * @param doubleCommentaryDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/24 19:29
     **/
    int updateCommentary(DoubleCommentaryDTO doubleCommentaryDTO);

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
