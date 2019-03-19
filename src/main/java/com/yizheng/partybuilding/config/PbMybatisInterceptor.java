package com.yizheng.partybuilding.config;

import com.yizheng.commons.enums.ResultCodeEnum;
import com.yizheng.commons.exception.BusinessException;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.partybuilding.dto.SysDeptDto;
import com.yizheng.partybuilding.service.inf.TabSysDeptService;
import com.yizheng.partybuilding.system.entity.SysDept;
import com.yizheng.partybuilding.system.entity.SysUser;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 mybatis 拦截器 , 监控执行 update 类型的sql，在修改 党组织与党员数据时，需要验证当前用户是否有权限（不能修改上级数据）
 zhuyu 2019-03-15
 Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 ParameterHandler (getParameterObject, setParameters)
 ResultSetHandler (handleResultSets, handleOutputParameters)
 StatementHandler (prepare, parameterize, batch, update, query)
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }) })
public class PbMybatisInterceptor implements Interceptor {

    @Autowired private TabSysDeptService tabSysDeptService;

    /**
     * 实现拦截的业务代码
     * */
    @Override
    public Object intercept(Invocation invocation) {
        Object result = null;
        boolean flag = isExecuter(invocation);
        if(flag){
            long start = System.currentTimeMillis();
            //Method method = invocation.getMethod();
            try {
                result = invocation.proceed();
            }catch (Throwable e){
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            //System.out.println("[PbMybatisInterceptor] execute cost [" + (end - start) + "] ms");
        }else{
            throw new BusinessException("您没有操作该数据的权限");
        }
        return result;
    }

    /**
     * 是否能执行
     * @param invocation
     * @return
     */
    private boolean isExecuter(Invocation invocation){
        boolean flag = true;
        Object target = invocation.getTarget();
        if(target instanceof Executor){
            Object[] args = invocation.getArgs();
            //遍历处理所有参数，update方法有两个参数，参见Executor类中的update()方法。
            for(int i=0;i<args.length;i++)
            {
                Object arg = args[i];
                //String className = arg.getClass().getName();
                //System.out.println(i + " 参数类型："+className);
                //根据它判断是否给“操作属性”赋值。如果是第一个参数 MappedStatement
                if(arg instanceof MappedStatement)
                {
                    MappedStatement ms = (MappedStatement)arg;
                    SqlCommandType sqlCommandType = ms.getSqlCommandType();
                    if(sqlCommandType != SqlCommandType.SELECT)
                    {//如果是“增加”或“更新”操作，则继续进行默认操作信息赋值。否则，则退出
                        continue;
                    }
                    else
                    {
                        break;
                    }
                }
                //修改组织或党员信息时，检查当前人是否有权限修改（条件：1.上级修改自己与下级数据，2.本级修改自己数据）
                if(arg instanceof SysDeptDto){
                    SysDeptDto deptDto = (SysDeptDto)arg;
                    if(deptDto.getDeptId() != null && deptDto.getDeptId() > 0){
                        int cuurentDeptId = UserContextHolder.getOrgId().intValue();
                        flag = isOperator(cuurentDeptId ,deptDto.getDeptId());
                    }
                }
                if(arg instanceof SysUser){
                    SysUser sysUser = (SysUser)arg;
                    if(sysUser.getDeptId() != null && sysUser.getDeptId() > 0){
                        int cuurentDeptId = UserContextHolder.getOrgId().intValue();
                        flag = isOperator(cuurentDeptId ,sysUser.getDeptId());
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 是否能操作该数据
     * 判断当前人的组织id，是否是操作数据组织id的下级或自己
     * @param cuurentDeptId   当前人的组织id
     * @param operatorDeptId  操作数据点组织id
     * @return
     */
    private boolean isOperator(int cuurentDeptId ,int operatorDeptId){
        //可以修改自己组织的信息
        if(cuurentDeptId == operatorDeptId){
            return true;
        }
        //不能修改上级组织的信息
        SysDept dbDept = tabSysDeptService.selectAloneByPrimaryKey(Long.valueOf(operatorDeptId));
        if(dbDept != null && !StringUtils.isEmpty(dbDept.getFullPath())){
            String fullPath = dbDept.getFullPath()+",";
            //System.out.println(" fullPath："+fullPath + " , cuurentDeptId : " + cuurentDeptId +" , operatorDeptId : " + operatorDeptId);
            int cuurentIndex = fullPath.indexOf(cuurentDeptId+",");
            int operatorIndex = fullPath.indexOf(operatorDeptId+",");
            //如果当前人的组织id在操作的组织的前面 则可以操作
            if(cuurentIndex < 0){
                return false;
            }
            if(cuurentIndex < operatorIndex){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static void main(String[] args){
        String fullPath = "14307,4917,13238,6,";
        int cuurentDeptId = 4917;
        int operatorDeptId = 13238;
        int cuurentIndex = fullPath.indexOf(cuurentDeptId+",");
        int operatorIndex = fullPath.indexOf(operatorDeptId+",");

        System.out.println("cuurentIndex ："+ cuurentIndex + " , operatorIndex : " + operatorIndex);
    }

    /**
     * Plugin.wrap生成拦截代理对象
     * */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    @Override
    public void setProperties(Properties properties) {

    }
}
