package com.hoon.batch.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CallableTaskletBatch(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun callableTaskletJob(
        callableStep: Step
    ) = jobBuilderFactory.get("callableTaskletBatch")
        .start(callableStep)
        .build()

    @Bean
    fun callableStep() = stepBuilderFactory.get("callableStep")
        .tasklet(tasklet())
        .build()

    private fun tasklet(): CallableTaskletAdapter = CallableTaskletAdapter().apply {
        this.setCallable {
            print("This was executed in another thread")
            RepeatStatus.FINISHED
        }
    }
}