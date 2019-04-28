package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.TabPbDevPartyMemberDate;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberDateVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FanYanGen on 2019/4/23 10:26
 */
@Repository
public interface TabPbDevPartyMemberDateMapper {

    int deleteByPrimaryKey(Long timeId);

    int insert(TabPbDevPartyMemberDate record);

    int insertSelective(TabPbDevPartyMemberDate record);

    TabPbDevPartyMemberDate selectByPrimaryKey(Long timeId);

    int updateByPrimaryKeySelective(TabPbDevPartyMemberDate record);

    int updateByPrimaryKey(TabPbDevPartyMemberDate record);

    /**
     * desc: 根据hostId查询发展步骤时间
     *
     * @param hostId 附件ID
     * @return List<TabPbDevPartyMemberDate>
     * @author FanYanGen
     * @date 2019/4/23 20:45
     **/
    List<DevPartyMemberDateVO> selectByHostId(Long hostId);

}