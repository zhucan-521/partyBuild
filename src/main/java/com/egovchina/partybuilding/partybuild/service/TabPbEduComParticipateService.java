package com.egovchina.partybuilding.partybuild.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduComParticipate;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/14
 */
public interface TabPbEduComParticipateService extends IService<TabPbEduComParticipate> {
    /**
     * 添加方法
     *
     * @param participate
     * @return
     */
    int add(TabPbEduComParticipate participate);

    /**
     * 编辑方法
     *
     * @param participate
     * @return
     */
    int edit(TabPbEduComParticipate participate);

    /**
     * 删除方法(逻辑删除)
     *
     * @param id
     * @return
     */
    int deleteFindById(Long id);

    /**
     * 根据类型查询list方法
     *
     * @param participate
     * @param page
     * @return
     */
    List<TabPbEduComParticipate> listParticipate(TabPbEduComParticipate participate,Page page);

    /**
     * 根据id查询单条数据
     *
     * @param id
     * @return
     */
    TabPbEduComParticipate findByIdData(Long id);

    /**
     * 改变审核状态
     *
     * @param id
     * @return
     */
    int changeState(Long id, String state, String shResult);
}
