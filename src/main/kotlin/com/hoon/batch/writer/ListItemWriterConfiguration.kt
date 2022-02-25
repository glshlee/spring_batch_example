package com.hoon.batch.writer

import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ListItemWriterConfiguration {

    @Bean
    fun listItemWriter()  = ItemWriter<String> { items ->
        items.forEach {
            println(">> current item = $it")
        }
    }
}