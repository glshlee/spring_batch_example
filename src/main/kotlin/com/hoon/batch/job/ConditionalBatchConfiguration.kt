package com.hoon.batch.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConditionalBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun conditionalJob(
        divideStep: Step,
        successStep: Step,
        failureStep: Step
    ) = jobBuilderFactory.get("conditionalJob")
        .start(divideStep)
        .on("FAILED").to((failureStep))
        .from(divideStep).on("*").to(successStep)
        .end()
        .build()

    @Bean
    fun divideStep(
        divideTasklet: Tasklet
    ) = stepBuilderFactory.get("divideStep")
        .tasklet(divideTasklet)
        .build()

    @Bean
    fun successStep(
        successTasklet: Tasklet
    ) = stepBuilderFactory.get("successStep")
        .tasklet(successTasklet)
        .build()

    @Bean
    fun failureStep(
        failureTasklet: Tasklet
    ) = stepBuilderFactory.get("failureStep")
        .tasklet(failureTasklet)
        .build()

    @Bean
    @StepScope
    fun divideTasklet() = Tasklet { _, chunkContext ->
        val divisor = chunkContext.stepContext
            .stepExecution
            .jobExecution
            .jobParameters
            .getLong("divisor")!!
        println(5/divisor)
        RepeatStatus.FINISHED
    }

    @Bean
    fun successTasklet(

    ) = Tasklet { _, _ ->
        println("Success!")
        RepeatStatus.FINISHED
    }

    @Bean
    fun failureTasklet() = Tasklet { _, _ ->
        println("Failure!")
        RepeatStatus.FINISHED
    }
}