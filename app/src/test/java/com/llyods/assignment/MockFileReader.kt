package com.llyods.assignment

import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.StandardCharsets

class MockFileReader {

    @Throws(IOException::class)
    fun getResponseFromJson(fileName: String): String? {
        val inputStream = javaClass.classLoader?.getResourceAsStream(
            "mock_response/$fileName"
        )
        val source = inputStream?.source()?.buffer()
        return source?.readString(StandardCharsets.UTF_8)
    }
}