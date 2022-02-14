package com.hoon.batch.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface ExampleLogger {
    val log: Logger get() = LoggerFactory.getLogger(this.javaClass)
}