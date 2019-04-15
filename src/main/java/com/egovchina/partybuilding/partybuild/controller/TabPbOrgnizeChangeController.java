package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbLinkLeaderDto;
import com.egovchina.partybuilding.partybuild.dto.UserDeptPositiveDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.service.TabPbOrgnizeChangeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述:
 * 组织变动控制器
 *
 * @author wuyunjie
 * Date 2018-12-02 10:45
 */
@Api(tags = "党组织-组织变动和联点信息-吴云杰")
@RestController
@RequestMapping("/orgChange")
public class TabPbOrgnizeChangeController {

    // 组织变动
    @Autowired
    private TabPbOrgnizeChangeService tabPbOrgnizeChangeService;

    @ApiOperation(value = "查看最新组织变动信息", notes = "查看最新组织变动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织ID", paramType = "query" ,dataType = "Long",required = true),
            @ApiImplicitParam(name = "changeType", value = "变动类型", paramType = "query" ,dataType = "Long",required = true)
            })
    @GetMapping("/showOrgChange")
    public TabPbOrgnizeChange showOrgChange(Long deptId , Long changeType) {
        return tabPbOrgnizeChangeService.selectOrgChangeByDeptIdOrderTime(deptId,changeType);
    }

    @ApiOperation(value = "组织变动",
            notes = "1. <font color=\"red\">组织更名(字典id=59006, value=ZZGM, type=ZZDB)</font><br/> " +
                    "2. <font color=\"red\">组织撤销(字典id=59007, value=ZZCX, type=ZZDB)</font><br/> " +
                    "3. <font color=\"red\">组织恢复(字典id=59008, value=ZZHF, type=ZZDB)</font><br/>"+
                    "4. <font color=\"red\">组织调整(字典id=59009, value=ZZTZ, type=ZZDB)</font>")
    @PostMapping()
    public String changeOrgName(@RequestBody TabPbOrgnizeChange change) {
        return this.tabPbOrgnizeChangeService.changeOrg(change);
    }

    @ApiOperation(value = "获取组织调整信息", notes = "可通过调整类型获取, 如果未传递调整类型, 则查询所有调整类型的数据, 该类型为数据字典中的ZZDB, 可通过指定type在数据字典接口中获取所有的类型,并将该字典的id作为请求参数使用")
    @GetMapping()
    @Deprecated
    public PageInfo<TabPbOrgnizeChange> list(String changeType,Long orgId, Page page){
        return this.tabPbOrgnizeChangeService.selectByChangeType(changeType,orgId, page);
    }

    @ApiOperation(value = "获取组织调整信息")
    @GetMapping("/selectCombination")
    public PageInfo<TabPbOrgnizeChange> selectCombination(Long orgId, Page page){
        return new PageInfo<>(this.tabPbOrgnizeChangeService.selectCombination(orgId,page));
    }


    @ApiOperation(value = "查看联点领导信息",notes = "查看联点领导信息")
    @GetMapping("/getJoint/{userId}")
    public UserDeptPositiveDto getJoint(@ApiParam(value = "党员ID", required = true) @PathVariable Long userId){
        return tabPbOrgnizeChangeService.selectJointByUserId(userId);
    }

    @ApiOperation(value = "查看联点领导列表详情",notes = "查看联点领导列表详情")
    @GetMapping("/getLeaderAll/{deptId}")
    public List<TabPbLinkLeaderDto> getLeaderAll(@ApiParam(value = "组织ID", required = true) @PathVariable Long deptId){
        return tabPbOrgnizeChangeService.selectUserDeptByDeptId(deptId);
    }

    @ApiOperation(value = "删除联点领导",notes = "删除联点领导",httpMethod = "DELETE")
    @DeleteMapping("/delLeader/{linkLedaerId}")
    public ReturnEntity delLeader(@ApiParam(value = "组织联点领导联点主键", required = true) @PathVariable Long linkLedaerId){
        int retVal = tabPbOrgnizeChangeService.delLeaderByLinkLedaerId(linkLedaerId);
        return ReturnUtil.buildReturn(retVal);
    }
    @ApiOperation(value = "保存联点信息",notes = "保存联点信息")
    @PostMapping ("/saveJoint")
    public ReturnEntity saveJoint(@ApiParam(value = "组织联点领导对象") @RequestBody TabPbLinkLeaderDto TabPbLinkLeaderDto){
        int retVal = tabPbOrgnizeChangeService.saveJoint(TabPbLinkLeaderDto);
        return ReturnUtil.buildReturn(retVal);
    }

}
