package com.zipdabang.zipdabang_android.core.data_store

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import org.apache.commons.lang3.SerializationException
import java.io.InputStream
import java.io.OutputStream


object ProtoSerializer: Serializer<Token> {
    override val defaultValue: Token
        get() = Token(
            accessToken = null,
            refreshToken = null,
            platformStatus = CurrentPlatform.NONE,
            platformToken = null,
            fcmToken = null
        )

    override suspend fun readFrom(input: InputStream): Token {
        return try {
            // read -> parse input(parameter/ json) into Token data class
            Json.decodeFromString(
                deserializer = Token.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Token.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}