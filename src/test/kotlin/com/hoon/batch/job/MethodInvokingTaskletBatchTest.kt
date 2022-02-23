package com.hoon.batch.job

import com.hoon.batch.BatchTestConfig
import com.hoon.batch.service.HelloWorldService
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [BatchTestConfig::class, MethodInvokingTaskletBatchConfiguration::class, HelloWorldService::class])
@SpringBatchTest
class MethodInvokingTaskletBatchTest @Autowired constructor(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {
    @Test
    fun `성공`() {
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .toJobParameters()
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        jobExecution.exitStatus shouldBe ExitStatus.COMPLETED
    }
}