package com.hoon.batch.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
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
    fun job(step: Step): Job {
        return jobBuilderFactory.get("job")
            .start(step)
            .build()
    }

    @StepScope
    @Bean
    fun helloWorldTasklet(
        @Value("#{jobParameters['name']}") name: String
    ) = Tasklet { _, _ ->
        println("Hello, $name!")
        RepeatStatus.FINISHED
    }
}