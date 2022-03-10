package com.hoon.batch.job

import com.hoon.batch.BatchTestConfig
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [BatchTestConfig::class, FlowBatchConfiguration::class])
@SpringBatchTest
class FlowBatchTest @Autowired constructor(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {

    @Test
    fun `FlowBatch 성공`() {
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .toJobParameters()
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        jobExecution.stepExecutions.any { it.exitStatus.exitCode.equals(ExitStatus.FAILED.exitCode) } shouldBe false
        jobExecution.exitStatus shouldBe ExitStatus.COMPLETED
    }
}