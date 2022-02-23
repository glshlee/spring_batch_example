package com.hoon.batch.job

import com.hoon.batch.config.ExampleLogger
import com.hoon.batch.vlidator.ParameterValidator
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.listener.ExecutionContextPromotionListener
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloWorldBatch(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    companion object : ExampleLogger

    @Bean
    fun helloWorldJob(
        step1: Step,
        step2: Step,
        validator: JobParametersValidator
    ): Job {
        return jobBuilderFactory.get("helloWorldJob")
            .start(step1)
            .next(step2)
            .validator(validator)
            .listener(helloWorldJobListener())
            .build()
    }

    @Bean
    fun step1(
        helloWorldTasklet: Tasklet,
        promotionListener: StepExecutionListener
    ) = stepBuilderFactory.get("step1")
        .tasklet(helloWorldTasklet)
        .listener(promotionListener)
        .build()

    @Bean
    fun step2(goodByeTasklet: Tasklet) = stepBuilderFactory.get("step2")
        .tasklet(goodByeTasklet)
        .build()

    @Bean
    fun helloWorldTasklet() = Tasklet { _, context ->
        val parameters = context.stepContext
            .jobParameters
        log.info("Hello, ${parameters["name"]}!")
        log.info("fileName = ${parameters["fileName"]}")
        log.info("promotion_test = ${parameters["test"]}")

        val stepContext = context.stepContext
            .stepExecution
            .executionContext

        stepContext.put("promotion_test", "step context promotion")

        RepeatStatus.FINISHED
    }

    @Bean
    fun goodByeTasklet() = Tasklet { contribution, chunkContext ->
        val parameters = chunkContext.stepContext
            .jobParameters
        log.info("Hello, ${parameters["name"]}!")
        log.info("fileName = ${parameters["fileName"]}")
        log.info("promotionTest= ${chunkContext.stepContext.stepExecution.jobExecution.executionContext["promotion_test"]}")

        RepeatStatus.FINISHED
    }

    @Bean
    fun validator() = CompositeJobParametersValidator().apply {
        val defaultJobParametersValidator = DefaultJobParametersValidator().apply {
            this.setRequiredKeys(arrayOf("fileName"))
            this.setOptionalKeys(arrayOf("name", "random"))
        }
        defaultJobParametersValidator.afterPropertiesSet()

        this.setValidators(listOf(ParameterValidator(), defaultJobParametersValidator))
    }

    @Bean
    fun promotionListener(): StepExecutionListener {
        val listener = ExecutionContextPromotionListener()
        listener.setKeys(arrayOf("promotion_test"))
        return listener
    }

    private fun helloWorldJobListener() = object : JobExecutionListener {
        override fun beforeJob(jobExecution: JobExecution) {
            log.info("beforeJob -> ${jobExecution.jobInstance.jobName} is beginning execution")
        }

        override fun afterJob(jobExecution: JobExecution) {
            log.info("afterJob -> ${jobExecution.jobInstance.jobName} has completed with the status ${jobExecution.status}")
        }
    }
}