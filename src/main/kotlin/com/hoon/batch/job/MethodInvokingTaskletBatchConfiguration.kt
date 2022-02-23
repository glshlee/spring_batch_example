package com.hoon.batch.job

import com.hoon.batch.service.HelloWorldService
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MethodInvokingTaskletBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun methodInvokingJob(
        methodInvokingStep: Step
    ) = jobBuilderFactory.get("methodInvokingJob")
        .start(methodInvokingStep)
        .build()

    @Bean
    fun methodInvokingStep(
        methodInvokingTasklet: Tasklet
    ) = stepBuilderFactory.get("methodInvokingStep")
        .tasklet(methodInvokingTasklet)
        .build()

    @Bean
    @StepScope
    fun methodInvokingTasklet(
        @Value("#{jobParameters['name']}") name: String,
        helloWorldService: HelloWorldService
    ) = MethodInvokingTaskletAdapter().apply {
        this.setTargetObject(helloWorldService)
        this.setTargetMethod("helloWorld")
        this.setArguments(arrayOf(name))
    }
}