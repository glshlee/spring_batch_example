package com.hoon.batch.job

import com.hoon.batch.BatchTestConfig
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [BatchTestConfig::class, ConditionalBatchConfiguration::class])
@SpringBatchTest
class ConditionalBatchTest @Autowired constructor(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {

    @Test
    fun `ConditionalBatch 성공`() {
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .addLong("divisor", 5)
            .toJobParameters()
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        jobExecution.stepExecutions.any { it.exitStatus.exitCode.equals(ExitStatus.FAILED.exitCode) } shouldBe false
        jobExecution.exitStatus shouldBe ExitStatus.COMPLETED
    }

    @Test
    fun `ConditionalBatch 실패`() {
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .addLong("divisor", 0)
            .toJobParameters()
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        jobExecution.stepExecutions.any { it.exitStatus.exitCode.equals(ExitStatus.FAILED.exitCode) } shouldBe true
        jobExecution.exitStatus shouldBe ExitStatus.FAILED
    }
}