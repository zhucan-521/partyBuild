package com.egovchina.partybuilding.partybuild.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.egovchina.partybuilding.partybuild.system.entity.SysDict;
import com.egovchina.partybuilding.partybuild.system.mapper.SysDictMapper;
import com.egovchina.partybuilding.partybuild.system.service.SysDictService;
import org.springframework.stereotype.Service;

@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
