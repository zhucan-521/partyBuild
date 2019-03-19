package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbLeadTeamDto;
import com.yizheng.partybuilding.entity.TabPbLeadTeam;
import com.yizheng.partybuilding.service.inf.PbLeadTeamService;
import com.yizheng.commons.util.ExcelUtil;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 领导班子
 *
 * @Author Jiang An
 **/
@Api(tags = "党组织-领导班子")
@RestController
@RequestMapping("/pbleadteam")
public class PbLeadTeamController {

    @Autowired
    private PbLeadTeamService pbLeadTeamService;

    //时间格式化
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    @ApiOperation(value = "增加领导班子", notes = "增加领导班子", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "班子实体") @RequestBody TabPbLeadTeam tabPbLeadTeam) throws ParseException {
        int retVal = pbLeadTeamService.insert(tabPbLeadTeam);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询单个领导班子详情", notes = "查看单个领导班子详情", httpMethod = "GET")
    @GetMapping("/selectById")
    public TabPbLeadTeamDto selectById(@RequestParam @ApiParam(value = "领导班子Id", required = true) Long leadTeamId) {
        TabPbLeadTeamDto tabPbLeadTeam = pbLeadTeamService.selectByPrimaryKey(leadTeamId);
        return tabPbLeadTeam;
    }

    @ApiOperation(value = "删除单个领导班子", notes = "删除单个领导班子", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity deleteById(@RequestParam @ApiParam(value = "领导班子Id", required = true) Long leadTeamId) {
        int retVal = pbLeadTeamService.deleteId(leadTeamId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改领导班子", notes = "修改领导班子", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "班子实体") @RequestBody TabPbLeadTeam tabPbLeadTeam) {
        int retVal = pbLeadTeamService.updateWithAboutInfo(tabPbLeadTeam);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "查询领导班子", notes = "查询领导班子", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织Id ", paramType = "query"),
            @ApiImplicitParam(name = "personName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "orgName", value = "组织名称", paramType = "query")
    })
    @PostMapping("/find")
    public PageInfo<TabPbLeadTeamDto> list(@ApiIgnore TabPbLeadTeamDto tabPbLeadTeamDto, Page page) {
        Map<String, Object> conditions = new HashMap<>();

        conditions.put("personName", tabPbLeadTeamDto.getPersonName());
        conditions.put("orgRange",tabPbLeadTeamDto.getOrgRange());
        conditions.put("idCardNo", tabPbLeadTeamDto.getIdCardNo());
        conditions.put("orgName", tabPbLeadTeamDto.getOrgName());
        Long rangeDeptId = tabPbLeadTeamDto.getOrgId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.getOrgId();
        }
        conditions.put("rangeDeptId",rangeDeptId);
        List<TabPbLeadTeamDto> list = pbLeadTeamService.select(conditions, page);
        PageInfo<TabPbLeadTeamDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出", notes = "可指定条件导出")
    public void export(@RequestParam @ApiParam(name = "leadTeamId", value = "领导班子Id") String leadTeamId,
                       HttpServletResponse response) throws IOException {
        Map<String, Object> conditions = new HashMap<>();
        //TODO 导出可由用户选择数据或默认导出当前用户可见的所有数据
        conditions.put("leadTeamId", leadTeamId.split(","));
        Page page = new Page();
        page.setPageSize(10000L);
        List<TabPbLeadTeamDto> list = pbLeadTeamService.select(conditions, page);
        String[] title = {"序列", "党组织名称", "换届选举形式", "书记", "年限", "本届期满日期", "在任班子成员数", "文档", "图片"};
        List<String[]> exportDataList = list.stream().map(dept -> new String[]{dept.getLeadTeamId() + "", dept.getOrgName(), String.valueOf(dept.getVoteType()), dept.getOrgNiZeMaster(),
                String.valueOf(dept.getDuringYear()), format(dept.getChangeDate()), String.valueOf(dept.getClassNum()),
                String.valueOf(dept.getImgNum()), String.valueOf(dept.getDocNum())}).collect(Collectors.toList());

        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook("领导班子信息", title, exportDataList.toArray(new String[0][8]), null);
        ExcelUtil.setResponseStream(response, "领导班子信息.xls");
        workbook.write(response.getOutputStream());

    }

    private String format(Date date) {
        if (date == null) {
            return "";
        }
        return FORMAT.format(date);
    }

}
