package com.hoon.batch.job

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChunkBasedBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun chunkBasedJob(
        chunkBasedStep: Step
    ) = jobBuilderFactory.get("chunkBasedJob")
        .start(chunkBasedStep)
        .build()

    @Bean
    fun chunkBasedStep(
        listItemReader: ItemReader<String>,
        listItemWriter: ItemWriter<String>
    ) = stepBuilderFactory.get("chunkBasedStep")
        .chunk<String, String>(10)
        .reader(listItemReader)
        .writer(listItemWriter)
        .build()
}