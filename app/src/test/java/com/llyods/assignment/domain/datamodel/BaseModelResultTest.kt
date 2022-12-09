package com.llyods.assignment.domain.datamodel

import com.llyods.assignment.domain.datamodel.BaseModelResult.*
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.io.IOException


class BaseModelResultTest {

    @Test
    fun `test OnSuccess of ApiResult for a vaild data`(){
        val artist = mockk<UserModel>()
        val success = OnSuccess(artist)
        Assert.assertTrue(success is OnSuccess)
    }

    @Test
    fun `test OnFailure of ApiResult when there is a exception`() {
        val error = OnFailure(IOException("error message"))
        Assert.assertTrue(error is OnFailure)
    }
}