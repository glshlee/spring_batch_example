package com.hoon.batch.job

import com.hoon.batch.vlidator.ParameterValidator
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersValidator
import org.springframework.batch.core.Step
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
class HelloWorldJob(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun step(helloWorldTasklet: Tasklet): Step {
        return stepBuilderFactory.get("step1")
            .tasklet(helloWorldTasklet)
            .build()
    }

    @Bean
    fun job(
        step: Step,
        validator: JobParametersValidator
    ): Job {
        return jobBuilderFactory.get("job")
            .start(step)
            .validator(validator)
            .build()
    }

    @StepScope
    @Bean
    fun helloWorldTasklet(
        @Value("#{jobParameters['name']}") name: String?,
        @Value("#{jobParameters['fileName']}") fileName: String
    ) = Tasklet { _, _ ->
        println("Hello, $name!")
        println("fileName = $fileName")
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
}