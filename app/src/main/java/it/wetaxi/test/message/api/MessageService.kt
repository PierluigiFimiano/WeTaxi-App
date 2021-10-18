package it.wetaxi.test.message.api

import it.wetaxi.test.message.data.MessageResponse
import retrofit2.http.GET

interface MessageService {

    @GET("/v5/tests/messages/useless")
    suspend fun getMessage(): MessageResponse

}