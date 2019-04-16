package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.partybuild.dto.TabPbJointMeetDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/29
 */
public interface TabPbJointMeetService {

    /**
     * 通过orgId添加成员组织, 如果联席会组织已存在则将成员附加
     * 否则将创建联席会组织, 然后将成员附加
     *
     * @param meet 联席会组织信息及成员信息, 如果没有可不传。 联席会组织成员列表为必传
     * @return 返回联席会主表信息
     */
    TabPbJointMeet save(TabPbJointMeetDto meet);

    /**
     * 通过党建联席会主键添加成员
     *
     * @param list 党建联席会主键id, 成员列表
     * @return 添加是否成功的消息
     */
    ReturnEntity save(List<TabPbJointMeetOrg> list);

    /**
     * 通过主键逻辑删除, 同时删除关联表数据
     *
     * @param meet 主键
     * @return 删除结果
     */
    void delete(TabPbJointMeet meet);

    /**
     * 通过主键删除明细表数据
     *
     * @param org
     * @return
     */
    void delete(TabPbJointMeetOrg org);

    /**
     * 更新主表数据
     *
     * @param meet
     * @return
     */
    int update(TabPbJointMeet meet);

    /**
     * 更新关联表数据
     *
     * @param meet
     * @return
     */
    int update(TabPbJointMeetOrg meet);

    /**
     * 查询主表数据, 以列表的形式返回, 并分页
     *
     * @param meet
     * @return
     */
    PageInfo<TabPbJointMeet> select(TabPbJointMeet meet, Page page);

    /**
     * 查询关联表数据, 以列表的形式返回
     *
     * @param org
     * @return
     */
    PageInfo<TabPbJointMeetOrg> select(TabPbJointMeetOrg org, Page page);
}
