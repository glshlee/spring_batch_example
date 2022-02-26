package com.hoon.batch.writer

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.transform.PassThroughLineAggregator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class FlatFileItemWriterConfiguration {

    @Bean
    @StepScope
    fun flatFileItemWriter(
        @Value("#{jobParameters['outputFile']}") outputFile: Resource
    ) = FlatFileItemWriterBuilder<String>()
        .name("flatFileItemWriter")
        .resource(outputFile)
        .lineAggregator(PassThroughLineAggregator())
        .build()
}