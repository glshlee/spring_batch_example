package com.hoon.batch.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DataSource {

    @Bean
    @Primary
    fun masterDataSource() = HikariDataSource().apply {
        this.driverClassName = "com.mysql.cj.jdbc.Driver"
        this.jdbcUrl = "jdbc:mysql://localhost:3307/job_repo"
        this.username = "root"
        this.password = "password"
    }
}