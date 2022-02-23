package com.hoon.batch

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@EnableBatchProcessing
@Configuration
@EnableAutoConfiguration
class BatchTestConfig {
}