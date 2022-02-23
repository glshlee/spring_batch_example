package com.hoon.batch.service

import org.springframework.stereotype.Service

@Service
class HelloWorldService {

    fun helloWorld(name: String) {
        print("hello, world! $name.")
    }
}