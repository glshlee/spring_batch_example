package com.hoon.batch.reader

import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class ListItemReaderConfiguration {

    @Bean
    fun listItemReader() = ListItemReader(List(1000) {
        UUID.randomUUID().toString()
    })
}