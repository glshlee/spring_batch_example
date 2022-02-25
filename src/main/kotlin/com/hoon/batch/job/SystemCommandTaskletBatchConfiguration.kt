package com.hoon.batch.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.SimpleSystemProcessExitCodeMapper
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor

@Configuration
class SystemCommandTaskletBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun systemCommandJob(
        systemCommandStep: Step
    ) = jobBuilderFactory.get("systemCommandJob")
        .start(systemCommandStep)
        .build()

    @Bean
    fun systemCommandStep() = stepBuilderFactory.get("systemCommandStep")
        .tasklet(systemCommandTasklet())
        .build()

    private fun systemCommandTasklet() = SystemCommandTasklet().apply {
        this.setWorkingDirectory("/Users")
        this.setCommand("ls -al")
        this.setTimeout(5000)
        this.setInterruptOnCancel(true)
        this.setTerminationCheckInterval(5000)
        this.setTaskExecutor(SimpleAsyncTaskExecutor())
        this.setEnvironmentParams(arrayOf("BATCH_HOME=/Users/batch"))
        this.setSystemProcessExitCodeMapper(SimpleSystemProcessExitCodeMapper())
    }
}