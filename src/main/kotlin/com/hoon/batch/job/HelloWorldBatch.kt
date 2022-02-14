package com.hoon.batch.job

import com.hoon.batch.config.ExampleLogger
import com.hoon.batch.vlidator.ParameterValidator
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloWorldBatch(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    companion object: ExampleLogger

    @Bean
    fun step(helloWorldTasklet: Tasklet): Step {
        return stepBuilderFactory.get("step1")
            .tasklet(helloWorldTasklet)
            .build()
    }

    @Bean
    fun helloWorldJob(
        step: Step,
        validator: JobParametersValidator
    ): Job {
        return jobBuilderFactory.get("helloWorldJob")
            .start(step)
            .validator(validator)
            .listener(HelloWorldJobListener())
            .build()
    }

    @StepScope
    @Bean
    fun helloWorldTasklet(
        @Value("#{jobParameters['name']}") name: String?,
        @Value("#{jobParameters['fileName']}") fileName: String
    ) = Tasklet { _, _ ->
        log.info("Hello, $name!")
        log.info("fileName = $fileName")
        RepeatStatus.FINISHED
    }

    @Bean
    fun validator() = CompositeJobParametersValidator().apply {
        val defaultJobParametersValidator = DefaultJobParametersValidator().apply {
            this.setRequiredKeys(arrayOf("fileName"))
            this.setOptionalKeys(arrayOf("name"))
        }
        defaultJobParametersValidator.afterPropertiesSet()

        this.setValidators(listOf(ParameterValidator(), defaultJobParametersValidator))
    }

    class HelloWorldJobListener : JobExecutionListener {
        override fun beforeJob(jobExecution: JobExecution) {
            log.info("beforeJob -> ${jobExecution.jobInstance.jobName} is beginning execution")
        }

        override fun afterJob(jobExecution: JobExecution) {
            log.info("afterJob -> ${jobExecution.jobInstance.jobName} has completed with the status ${jobExecution.status}")
        }
    }
}