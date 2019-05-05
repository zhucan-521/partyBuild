package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TrainingQueryBean;
import com.egovchina.partybuilding.partybuild.dto.TrainingDTO;
import com.egovchina.partybuilding.partybuild.service.TrainingService;
import com.egovchina.partybuilding.partybuild.vo.TrainingVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhucan
 * 党员培训控制层
 */
@Api(tags = "党员-党员培训情况-v1-朱灿")
@RestController
@RequestMapping("/v1/training/")
public class TrainingController {

        @Autowired
        TrainingService tabPbTrainingService;

        @ApiOperation(value = "添加党员培训情况", notes = "必须有userId", httpMethod = "POST")
        @PostMapping
        public ReturnEntity addTraining(@RequestBody @Validated  @ApiParam(name = "培训党员")  TrainingDTO trainingDTO){
            return ReturnUtil.buildReturn(tabPbTrainingService.addTraningDTO(trainingDTO));
        }

        @ApiOperation(value = "根据某党员id列表显示某党员培训情况", notes = "根据党员id列表显示党员培训情况", httpMethod = "GET")
        @ApiImplicitParam(name = "userId", value = "党员ID", paramType = "query")
        @GetMapping
        public List<TrainingVO> getTrainingVOList(TrainingQueryBean trainingQueryBean){
           return tabPbTrainingService.getTrainingVOById(trainingQueryBean);
        }

        @ApiOperation(value = "根据培训主键单个查询党员培训情况", httpMethod = "GET")
        @ApiImplicitParam(value = "培训主键",name = "traningId",paramType = "path")
        @GetMapping ("/{traningId}")
        public TrainingVO getTrainingVOById(@PathVariable Long traningId) {
            return tabPbTrainingService.getTrainingVOById(traningId);
        }

        @ApiOperation(value = "根据培训主键删除党员培训情况", notes = "必须要有党员培训主键", httpMethod = "DELETE")
        @ApiImplicitParam(value = "培训主键",name = "traningId",paramType = "path")
        @DeleteMapping("/{traningId}")
        public ReturnEntity deletTrainingDTO(@PathVariable Long traningId) {
                return ReturnUtil.buildReturn(tabPbTrainingService.logicDeleteTrainingDTO(traningId));
        }

        @ApiOperation(value = "根据培训主键修改党员培训情况", notes = "必须要有党员培训主键", httpMethod = "PUT")
        @PutMapping
        public ReturnEntity updateTrainingDTO(@RequestBody @Validated  @ApiParam(name = "培训党员")  TrainingDTO trainingDTO) {
            return ReturnUtil.buildReturn(tabPbTrainingService.updateTrainingDTO(trainingDTO));
        }
}
