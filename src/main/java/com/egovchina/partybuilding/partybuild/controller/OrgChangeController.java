package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.OrgChangeDTO;
import com.egovchina.partybuilding.partybuild.entity.OrgChangeQueryBean;
import com.egovchina.partybuilding.partybuild.service.OrgChangeService;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述:
 * 组织变动控制器
 *
 * @author wuyunjie
 * Date 2018-12-02 10:45
 */
@Api(tags = "党组织-组织变动-v1-吴云杰")
@RestController
@RequestMapping("/v1/org-changes")
public class OrgChangeController {
    @Autowired
    private OrgChangeService orgChangeService;

    @ApiOperation(value = "查看最新组织变动信息", notes = "查看最新组织变动信息")
    @ApiImplicitParam(name = "changeId", value = "变动id", paramType = "path", dataType = "Long", required = true)
    @GetMapping("/{changeId}")
    public OrgChangeVO getOrgChangeById(@PathVariable Long changeId) {
        return orgChangeService.selectOrgChangeById(changeId);
    }

    @ApiOperation(value = "新增组织变动",
            notes = "1. <font color=\"red\">组织更名(字典id=59006, value=ZZGM, type=ZZDB)</font><br/> " +
                    "2. <font color=\"red\">组织撤销(字典id=59007, value=ZZCX, type=ZZDB)</font><br/> " +
                    "3. <font color=\"red\">组织恢复(字典id=59008, value=ZZHF, type=ZZDB)</font><br/>" +
                    "4. <font color=\"red\">组织调整(字典id=59009, value=ZZTZ, type=ZZDB)</font><br/>" +
                    "5. <font color=\"red\">其他调整(字典id=59567, value=QTTZ, type=ZZDB)</font><br/>" +
                    "6. <font color=\"red\">整建制转移(字典id=59603, value=ZJZZY, type=ZZDB)</font>")
    @PostMapping
    public ReturnEntity addOrgChange(@ApiParam("组织变动信息") @RequestBody @Validated OrgChangeDTO change) {
        return ReturnUtil.buildReturn(this.orgChangeService.addOrgChange(change));
    }

    @ApiOperation(value = "获取组织变动信息列表")
    @GetMapping
    public PageInfo<OrgChangeVO> getOrgChangeList(@Validated OrgChangeQueryBean orgChangeQueryBean, Page page) {
        return new PageInfo<>(this.orgChangeService.selectOrgChangeList(orgChangeQueryBean, page));
    }
}
