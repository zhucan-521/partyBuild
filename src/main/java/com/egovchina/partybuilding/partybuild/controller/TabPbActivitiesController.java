//package com.yizheng.partybuilding.controller;
//
//import com.github.pagehelper.PageInfo;
//import OrgRange;
//import Page;
//import TabPbActivitiesDto;
//import TabPbActivitiesService;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author YangYingXiang on 2018/12/03
// */
//@RestController
//@RequestMapping("/activities")
//@Api(tags = "党员-社区活动 蒋安")
//public class TabPbActivitiesController {
//
//    @Autowired
//    private TabPbActivitiesService activitiesService;
//
//    @GetMapping(value = "/list")
//    @ApiOperation(value = "获取社区活动信息列表", notes = "可指定条件查询",httpMethod = "GET")
//    @ApiImplicitParams({
//            @ApiImplicitParam(value = "活动名称", name = "subject", dataType = "String"),
//            @ApiImplicitParam(value = "党员名称", name = "username", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(value = "活动类型", name = "activitiesType", dataType = "Long", paramType = "query"),
//            @ApiImplicitParam(value = "是否直属组织 0直属，1报到", name = "deptState", paramType = "query")
//    })
//    public PageInfo<TabPbActivitiesDto> list(String subject, String username, Long activitiesType,
//                                             Long deptState, @ApiParam Page page, OrgRange orgRange) {
//        TabPbActivitiesDto tabPbActivitiesDto = new TabPbActivitiesDto();
//        tabPbActivitiesDto.setSubject(subject);
//        tabPbActivitiesDto.setUsername(username);
//        tabPbActivitiesDto.setActivitiesType(activitiesType);
//        tabPbActivitiesDto.setRangeDeptId(orgRange.getRangeDeptId());
//        tabPbActivitiesDto.setOrgRange(orgRange.getOrgRange());
//        tabPbActivitiesDto.setDeptState(deptState);
//        return new PageInfo<>(this.activitiesService.ActivitiesDtoList(tabPbActivitiesDto, page));
//    }
//
//}
