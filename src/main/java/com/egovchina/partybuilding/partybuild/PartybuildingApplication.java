package com.egovchina.partybuilding.partybuild;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@MapperScan({"com.egovchina.partybuilding.partybuild.repository", "com.egovchina.partybuilding.partybuild.system.mapper"})
@ComponentScan(basePackages = "com.egovchina")
@EnableFeignClients
@EnableScheduling //启用后会定时从consul上拉取配置
@SpringBootApplication
public class PartybuildingApplication {

    public static void main(String[] args) {

        SpringApplication.run(PartybuildingApplication.class, args);
    }
}
