package com.hoon.batch.service

import org.springframework.stereotype.Service

@Service
class HelloWorldService {

    fun helloWorld() {
        print("hello, world!")
    }
}