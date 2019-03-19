package com.yizheng.partybuilding.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yizheng.partybuilding.system.entity.SysDict;
import com.yizheng.partybuilding.system.mapper.SysDictMapper;
import com.yizheng.partybuilding.system.service.SysDictService;
import org.springframework.stereotype.Service;

@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
