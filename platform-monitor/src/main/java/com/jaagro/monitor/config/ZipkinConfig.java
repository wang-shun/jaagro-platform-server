package com.jaagro.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.storage.mysql.MySQLStorage;

import javax.sql.DataSource;

/**
 * 将zipkin数据纯如数据库
 * @author tony
 */
@Configuration
public class ZipkinConfig {
    @Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }
}
