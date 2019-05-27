package com.egovchina.partybuilding.partybuild.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegisterQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegisterVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/28
 */
@Repository
public interface TabPbPositiveRegistMapper extends BaseMapper<TabPbPositiveRegist> {

    /**
     * 查询list数据(分页)
     * @param queryBean 查询实体
     * @return
     */
    List<PositiveRegisterVO> selectPositiveRegisterMemberVOListByCondition(PositiveRegisterQueryBean queryBean);

    /**
     * 根据id查询报到VO信息
     *
     * @param positiveRegistId id
     * @return
     */
    PositiveRegisterVO selectPositiveRegisterVOById(Long positiveRegistId);

    /**
     * 检查指定党员是否已存在报到数据
     *
     * @param userId 党员id
     * @return
     */
    Boolean checkPartyMemberIsExistRegister(Long userId);

    /**
     * 聚合生成党员报到数据
     *
     * @param userId 党员id
     * @return
     */
    TabPbPositiveRegist aggregateGeneratePartyMemberRegisterData(Long userId);

    /**
     * 根据id查询报到信息
     *
     * @param positiveRegistId id
     * @return
     */
    TabPbPositiveRegist selectPositiveRegisterById(Long positiveRegistId);
}