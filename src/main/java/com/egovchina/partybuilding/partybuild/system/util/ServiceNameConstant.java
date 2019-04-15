package com.egovchina.partybuilding.partybuild.system.util;

public interface ServiceNameConstant {
    /**
     * 认证服务的SERVICEID（zuul 配置的对应）
     */
    String AUTH_SERVICE = "pigx-auth";

    /**
     * UMPS模块
     */
    String UMPS_SERVICE = "pigx-upms";

    /**
     * 分布式事务协调服务
     */
    String TX_MANAGER = "pigx-tx-manager";
}
