package com.hoon.batch.reader

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.PassThroughLineMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class FlatFileItemReaderConfiguration {

    @Bean
    @StepScope
    fun flatFileItemReader(
        @Value("#{jobParameters['inputFile']}") inputFile: Resource
    ) = FlatFileItemReaderBuilder<String>()
        .name("flatFileItemReader")
        .resource(inputFile)
        .lineMapper(PassThroughLineMapper())
        .build()
}