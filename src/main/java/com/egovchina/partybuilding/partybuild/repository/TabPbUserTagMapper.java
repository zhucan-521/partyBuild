package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.UserTagDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabPbUserTagMapper {
    int deleteByPrimaryKey(Long usertagId);

    int deleteByUserIdAndTagType(@Param("userId") Long userId, @Param("tagTypes") Long tagType);

    int insert(TabPbUserTag record);

    int insertSelective(TabPbUserTag record);

    int insertUserTagDTOSelective(TabPbUserTag tabPbUserTag);

    TabPbUserTag selectByPrimaryKey(Long usertagId);

    int updateByPrimaryKeySelective(TabPbUserTag record);

    int updateByPrimaryKey(TabPbUserTag record);

    List<TabPbUserTag> selectListSelective(TabPbUserTag userTag);

    /**
     * 以字典为主的返回
     *
     * @param userTag 只有userId有用
     * @return
     */
    List<TabPbUserTag> selectAllListSelective(TabPbUserTag userTag);

    /**
     * 对应的dictId可通过系统-字典管理-用户标签查看对应的标签
     *
     * @param tagDict 用户标签查看对应的标签
     * @return
     */
    List<SysUser> selectUserByTagDict(Long tagDict);

    boolean exist(@Param("userId") Long userId, @Param("tagTypes") Long tagType);

    /**
     * 批量插入标记
     * @param list
     * @return
     */
    int batchInsertUserTagDTO(List<TabPbUserTag> list);

    List<TabPbUserTag> selectTagTypesList(Long userId);

    List selectUsertagIdList(Long userId);

    List<TabPbUserTag> selectTabPbUserTagListByUserId(Long userId);

    int insertUserTagDTO(TabPbUserTag tabPbUserTag);

    /**
     * 根据用户id删除用户所有标签
     * @param userId 用户id
     * @return
     */
    int batchDeleteByUserId(Long userId);

    /**
     * 批量删除根据id
     * @param deleteIds ids
     * @return
     */
    int batchDeleteById(List<Long> deleteIds);
}