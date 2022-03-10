package com.hoon.batch.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlowBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun conditionalStepLogicJob(
        preProcessingFlow: Flow,
        runBatch: Step
    ) = jobBuilderFactory.get("conditionalStepLogicJob")
        .start(preProcessingFlow)
        .next(runBatch)
        .end()
        .build()

    @Bean
    fun preProcessingFlow(
        loadFileStep: Step,
        loadCustomerStep: Step,
        updateStartStep: Step
    ) = FlowBuilder<Flow>("preProcessingFlow")
        .start(loadFileStep)
        .next(loadCustomerStep)
        .next(updateStartStep)
        .build()

    @Bean
    fun runBatch(
        runBatchTasklet: Tasklet
    ) = stepBuilderFactory.get("runBatch")
        .tasklet(runBatchTasklet)
        .build()

    @Bean
    fun loadFileStep(
        loadStockFile: Tasklet
    ) = stepBuilderFactory.get("loadStockFile")
        .tasklet(loadStockFile)
        .build()

    @Bean
    fun loadCustomerStep(
        loadCustomerFile: Tasklet
    ) = stepBuilderFactory.get("loadCustomerStep")
        .tasklet(loadCustomerFile)
        .build()

    @Bean
    fun updateStartStep(
        updateStart: Tasklet
    ) = stepBuilderFactory.get("loadCustomerStep")
        .tasklet(updateStart)
        .build()

    @Bean
    fun runBatchTasklet() = Tasklet { _, _ ->
        println("Flow Batch -> The batch has been run")
        RepeatStatus.FINISHED
    }

    @Bean
    fun loadStockFile() = Tasklet { _, _ ->
        println("Flow Batch -> The stock file has been loaded")
        RepeatStatus.FINISHED
    }

    @Bean
    fun loadCustomerFile() = Tasklet { _, _ ->
        println("Flow Batch -> The customer file has been loaded")
        RepeatStatus.FINISHED
    }

    @Bean
    fun updateStart() = Tasklet { _, _ ->
        println("Flow Batch -> The start has been updated")
        RepeatStatus.FINISHED
    }
}