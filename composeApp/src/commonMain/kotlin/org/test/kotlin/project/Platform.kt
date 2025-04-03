package org.test.kotlin.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform