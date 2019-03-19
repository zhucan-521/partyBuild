package com.yizheng.partybuilding.repository;

import com.yizheng.partybuilding.dto.TabPbLeadTeamDto;
import com.yizheng.partybuilding.entity.TabPbLeadTeam;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TabPbLeadTeamMapper {
    int deleteByPrimaryKey(Long leadTeamId);

    int insert(TabPbLeadTeam record);

    int insertSelective(TabPbLeadTeam record);

    TabPbLeadTeamDto selectByPrimaryKey(Long leadTeamId);

    int updateByPrimaryKeySelective(TabPbLeadTeam record);

    int updateByPrimaryKeyWithBLOBs(TabPbLeadTeam record);

    int updateByPrimaryKey(TabPbLeadTeam record);

    List<TabPbLeadTeamDto> select(Map<String, Object> conditions);

    //查询
    TabPbLeadTeam select(Long leadTeamId);

    int deleteId(Long leadTeamId);

    /**
     * 维护委员人数
     *
     */
    void updateNum(@Param("leadTeamId") Long leadTeamId);


    void updateById(@Param("leadTeamId")Long i,@Param("count")Long count);

    Long selectByNum(Long leadTeamId);
}