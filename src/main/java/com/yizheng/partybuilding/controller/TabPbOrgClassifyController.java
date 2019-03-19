package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.entity.TabPbOrgClassify;

import com.yizheng.partybuilding.service.inf.TabPbOrgClassifyService;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类定等控制器
 *
 * @Author Zhang Fan
 **/
@Api(tags = "党组织-分类定等模块")
@RestController
@RequestMapping("/classify")
public class TabPbOrgClassifyController {

    @Autowired
    private TabPbOrgClassifyService tabPbOrgClassifyService;
    @Autowired
    private TabSysDeptService tabSysDeptService;

    @ApiOperation(value = "分类定等列表", notes = "分类定等列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgLevel", value = "定等级别 dict FLDD", paramType = "query"),
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbOrgClassify> list(String orgLevel, String orgRange, Long rangeDeptId, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgRange", orgRange);
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();//当前用户的组织ID
        }
        conditions.put("rangeDeptId", rangeDeptId);
        conditions.put("orgLevel", orgLevel);
        conditions.put("delFlag", "0");
        List<TabPbOrgClassify> list = tabPbOrgClassifyService.selectWithConditions(conditions, page);
        PageInfo<TabPbOrgClassify> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @ApiOperation(value = "新增分类定等", notes = "新增分类定等", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "定等实体") @RequestBody TabPbOrgClassify tabPbOrgClassify) {
        operationalDataVerification(tabPbOrgClassify);
        int retVal = tabPbOrgClassifyService.insertSelective(tabPbOrgClassify);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "定等计划修改", notes = "定等计划修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "定等实体") @RequestBody TabPbOrgClassify tabPbOrgClassify) {
        operationalDataVerification(tabPbOrgClassify);
        int retVal = tabPbOrgClassifyService.updateByPrimaryKeySelective(tabPbOrgClassify);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "定等数据详情", notes = "定等数据详情", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbOrgClassify details(@RequestParam("orgClassifyId") @ApiParam(name = "orgClassifyId", value = "定等数据ID", required = true) Long orgClassifyId) {
        return tabPbOrgClassifyService.selectByPrimaryKey(orgClassifyId);
    }

    @ApiOperation(value = "定等数据删除", notes = "定等数据删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam("orgClassifyId") @ApiParam(name = "orgClassifyId", value = "定等数据ID", required = true) Long orgClassifyId) {
        //逻辑删除
        int retVal = tabPbOrgClassifyService.logicDeleteById(orgClassifyId);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 操作数据校验
     *
     * @param tabPbOrgClassify 数据实体
     */
    private void operationalDataVerification(TabPbOrgClassify tabPbOrgClassify) {
//        Long orgClassifyId = tabPbOrgClassify.getOrgClassifyId();
        Long deptId = tabPbOrgClassify.getDeptId();

        if (!tabSysDeptService.checkOrgIsExists(deptId)) {
            throw new BusinessDataNotFoundException("指定组织不存在");
        }
//        List<TabPbOrgClassify> dbTabPbOrgClassify = tabPbOrgClassifyService.selectByDeptId(deptId);
//        if (orgClassifyId == null) {
//            if (dbTabPbOrgClassify != null)
//                throw new BusinessDataCheckFailException("指定组织已被定等过");
//        } else {
//            if (dbTabPbOrgClassify != null && dbTabPbOrgClassify.getOrgClassifyId() != orgClassifyId)
//                throw new BusinessDataCheckFailException("指定组织已被定等过");
//        }
    }
}
