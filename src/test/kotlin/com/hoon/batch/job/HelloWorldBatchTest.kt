package com.hoon.batch.job

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [HelloWorldBatch::class])
@EnableBatchProcessing
@SpringBatchTest
@Configuration
@EnableAutoConfiguration
class HelloWorldBatchTest @Autowired constructor(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {

    @Test
    fun `HelloWorldBatch 성공`() {
        print("test")
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .addString("fileName", "test.csv")
            .toJobParameters()
        jobLauncherTestUtils.launchJob(jobParameters)
    }
}