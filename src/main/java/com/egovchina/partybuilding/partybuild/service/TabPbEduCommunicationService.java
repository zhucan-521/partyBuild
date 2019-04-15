package com.egovchina.partybuilding.partybuild.service;

import com.baomidou.mybatisplus.service.IService;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduCommunication;

import java.util.List;

/**
 * @author YangYingXiang on 2018/12/13
 */

public interface TabPbEduCommunicationService extends IService<TabPbEduCommunication> {
    /**
     * 添加方法
     * @param communication
     * @return
     */
    int add(TabPbEduCommunication communication);

    /**
     * 编辑方法
     * @param communication
     * @return
     */
    int edit(TabPbEduCommunication communication);

    /**
     * 删除方法(逻辑删除)
     * @param id
     * @return
     */
    int deleteFindById(Long id);

    /**
     * 根据类型查询list方法
     * @param type
     * @param page
     * @return
     */
    List<TabPbEduCommunication> communicationList(Long type,Page page);

    /**
     * 根据id查询单条数据
     * @param id
     * @return
     */
    TabPbEduCommunication findByIdData(Long id);
}
