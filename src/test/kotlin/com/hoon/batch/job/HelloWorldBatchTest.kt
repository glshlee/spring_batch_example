package com.hoon.batch.job

import com.hoon.batch.BatchTestConfig
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [BatchTestConfig::class, HelloWorldBatchConfiguration::class])
@SpringBatchTest
class HelloWorldBatchTest @Autowired constructor(
    val jobLauncherTestUtils: JobLauncherTestUtils
) {

    @Test
    fun `HelloWorldBatch 성공`() {
        val jobParameters = jobLauncherTestUtils.uniqueJobParametersBuilder
            .addString("fileName", "test.csv")
            .toJobParameters()
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        jobExecution.exitStatus shouldBe ExitStatus.COMPLETED
    }
}