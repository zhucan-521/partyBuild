package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.WordUtil;
import com.egovchina.partybuilding.partybuild.dto.SecretaryMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.SecretaryMemberQueryBean;
import com.egovchina.partybuilding.partybuild.service.SecretaryService;
import com.egovchina.partybuilding.partybuild.vo.SecretaryMemberVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author zhucan
 */
@Api(tags = "党员-书记-v1-朱灿")
@RestController
@RequestMapping("/v1/secretaries")
public class SecretaryController {

    @Autowired
    SecretaryService secretaryService;

    @ApiOperation(value = "添加书记",  httpMethod = "POST")
    @HasPermission("party_teamSecretaryManager_add")
    @PostMapping
    public ReturnEntity addSecretary(@Validated @ApiParam(value = "书记实体")  @RequestBody SecretaryMemberDTO secretaryMemberDTO) {
        return ReturnUtil.buildReturn(secretaryService.addSecretary(secretaryMemberDTO));
    }

    @ApiOperation(value = "根据书记secretaryId获取书记详情", httpMethod = "GET")
    @ApiImplicitParam(name = "secretaryId", value = "书记Id", paramType = "path", required = true)
    @HasPermission("party_teamSecretaryManager_detail")
    @GetMapping("/{secretaryId}")
    public SecretaryMemberVO getSecretaryVOBySecretaryId(@PathVariable Long secretaryId) {
        return secretaryService.selectSecretaryBySecretaryId(secretaryId);
    }

    @ApiOperation(value = "修改书记", notes = "（书记的家庭成员和职务传他们的id则是修改不传则是添加）", httpMethod = "PUT")
    @HasPermission("party_teamSecretaryManager_edit")
    @PutMapping
    public ReturnEntity updateSecretary(@Validated @ApiParam(value = "书记实体") @RequestBody SecretaryMemberDTO secretaryMemberDTO) {
        return ReturnUtil.buildReturn(secretaryService.updateSecretary(secretaryMemberDTO));
    }

    @ApiOperation(value = "删除书记", httpMethod = "DELETE")
    @ApiImplicitParam(name = "specialWorkerId", value = "书记主键", required = true, paramType = "path")
    @HasPermission("party_teamSecretaryManager_delete")
    @DeleteMapping("/{secretaryId}")
    public ReturnEntity removeSecretary(@PathVariable Long secretaryId) {
        return ReturnUtil.buildReturn(secretaryService.removeSecretary(secretaryId));
    }

    @ApiOperation(value = "书记列表",  httpMethod = "GET")
    @HasPermission("party_teamSecretary")
    @GetMapping
    public PageInfo<SecretaryMemberVO> secretaryList(SecretaryMemberQueryBean secretaryMemberQueryBean, Page page) {
        return new PageInfo<>(secretaryService.selectSecretaryList(secretaryMemberQueryBean, page));
    }

    @ApiOperation(value = "书记导出",  httpMethod = "GET")
    @ApiImplicitParam(name = "secretaryId", value = "书记主键", paramType = "path", required = true)
    @HasPermission("party_teamSecretary")
    @GetMapping("/{secretaryId}/export")
    public void secretaryExport(@PathVariable Long secretaryId, HttpServletResponse response) throws Exception {
        Map<String, Object> exportData = secretaryService.selectExportDataBySecretaryId(secretaryId);
        if (exportData == null) {
            throw new BusinessDataNotFoundException("书记数据不存在");
        }
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(exportData.get("realname") + "_书记简历.doc", "UTF-8"));
        try(Writer writer = new OutputStreamWriter(outputStream)){
            WordUtil.processTemplate("secretaryExport.ftl", exportData, writer);
        }
    }

}
