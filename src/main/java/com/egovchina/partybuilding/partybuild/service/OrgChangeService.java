package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.partybuild.dto.OrgChangeDTO;
import com.egovchina.partybuilding.partybuild.entity.OrgChangeQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;

import java.util.List;

/**
 * 描述:
 * 组织变动接口
 *
 * @author wuyunjie
 * Date 2019-04-20 11:17
 */
public interface OrgChangeService {
    /**
     * 根据组织id、变动类型查询最新的变动记录
     *
     * @param changeId 调整id
     * @return OrgChangeVO
     * @auther WuYunJie
     * @date 2019/5/11 14:22
     */
    OrgChangeVO selectOrgChangeById(Long changeId);

    /**
     * 添加组织变动
     *
     * @param orgChangeDTO 组织变动TDO
     * @return int
     * @auther WuYunJie
     * @date 2019/5/11 14:34
     */
    int addOrgChange(OrgChangeDTO orgChangeDTO);

    /**
     * 查询组织调整记录
     *
     * @param orgChangeQueryBean 组织变动查询实体
     * @param page               分页
     * @return List<OrgChangeVO>
     * @auther WuYunJie
     * @date 2019/5/11 14:33
     */
    List<OrgChangeVO> selectOrgChangeList(OrgChangeQueryBean orgChangeQueryBean, Page page);

    /**
     * 添加组织变动记录
     *
     * @param tabPbOrgnizeChange 组织变动实体
     * @return int
     * @auther WuYunJie
     * @date 2019/5/11 14:45
     */
    int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange);

    /**
     * 组织更名
     *
     * @param tabPbOrgnizeChange 变动实体
     * @param sysDept            变动前组织对象
     * @return int
     * @auther WuYunJie
     * @date 2019/5/11 15:38
     */
    int orgRename(TabPbOrgnizeChange tabPbOrgnizeChange, SysDept sysDept);

    /**
     * 组织撤消、恢复
     *
     * @param tabPbOrgnizeChange 变动实体
     * @param sysDept            变动前组织对象
     * @param eblFlag            有效性
     * @param orgStatus          状态
     * @return int
     */
    int orgRestoreOrRevoke(TabPbOrgnizeChange tabPbOrgnizeChange, SysDept sysDept, String eblFlag, Long orgStatus);

    /**
     * 组织调整上级组织
     *
     * @param tabPbOrgnizeChange 变动实体
     * @param sysDept            变动前组织对象
     * @return int
     * @auther WuYunJie
     * @date 2019/5/11 15:48
     */
    int orgChangeSuperiorOrg(TabPbOrgnizeChange tabPbOrgnizeChange, SysDept sysDept);
}
