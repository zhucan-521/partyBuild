package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegistMemberQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegistMemberVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/28
 */
@Repository
public interface TabPbPositiveRegistMapper extends BaseMapper<TabPbPositiveRegist> {

    /**
     * 保存申请报到记录
     * @param positiveRegist
     */
    int addPositiveRegist(TabPbPositiveRegist positiveRegist);

    /**
     * 查询list数据(分页)
     * @param positiveRegist
     * @return
     */
    List<TabPbPositiveRegist> selectListPage(TabPbPositiveRegist positiveRegist);


    /**
     * 查询list数据(分页)
     * @param positiveRegist
     * @return
     */
    List<PositiveRegistMemberVO> selectListVoPage(PositiveRegistMemberQueryBean positiveRegist);



    /**
     * 修改报到状态
     * @param positiveRegist
     */
    int editPositive(TabPbPositiveRegist positiveRegist);

    /**
     * 逻辑删除
     * @param positiveRegistId
     */
    int deleteRegist(TabPbPositiveRegist positiveRegistId);

    TabPbPositiveRegist findById(Long userId);

    TabPbPositiveRegist findByVoId(Long userId);

    /**
     * 根据主键id查询单条数据
     * @param positiveRegistId
     * @return
     */
    TabPbPositiveRegist editFindById(Long positiveRegistId);

    PositiveRegistMemberVO findPositiveRegistMemberById(Long positiveRegistId);

}