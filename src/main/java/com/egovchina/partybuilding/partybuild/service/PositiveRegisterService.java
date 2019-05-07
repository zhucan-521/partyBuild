package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterCancelDTO;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegisterDTO;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegisterQueryBean;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegisterVO;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/29
 */
public interface PositiveRegisterService {

    /**
     * 新增报到信息
     *
     * @param positiveRegisterDTO 报到DTO
     * @return
     */
    int insertPositiveRegister(PositiveRegisterDTO positiveRegisterDTO);

    /**
     * 根据条件查询报到VO列表
     *
     * @param positiveRegisterQueryBean 查询实体
     * @param page                      分页对象
     * @return
     */
    List<PositiveRegisterVO> selectPositiveRegisterVOListByCondition(PositiveRegisterQueryBean positiveRegisterQueryBean, Page page);

    /**
     * 撤销报到信息
     *
     * @param positiveRegisterCancelDTO 撤销报到实体
     */
    int cancelPositiveRegister(PositiveRegisterCancelDTO positiveRegisterCancelDTO);

    /**
     * 逻辑删除
     *
     * @param positiveRegistId id
     */
    int logicDeletePositiveRegisterById(Long positiveRegistId);

    /**
     * 根据id获取报到信息
     *
     * @param positiveRegistId id
     * @return
     */
    PositiveRegisterVO getPositiveRegisterVOById(Long positiveRegistId);
}
