package com.zipdabang.zipdabang_android.core.data_store.proto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.zipdabang.zipdabang_android.common.Constants.DATA_STORE_FILE_NAME
import kotlinx.serialization.json.Json
import org.apache.commons.lang3.SerializationException
import java.io.InputStream
import java.io.OutputStream


object ProtoSerializer: Serializer<Token> {
    override val defaultValue: Token
        get() = Token(
            accessToken = null,
            refreshToken = null,
            platformToken = null,
            platformStatus = CurrentPlatform.NONE,
            fcmToken = null,
            deviceNumber = null
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

val Context.tokenDataStore: DataStore<Token> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = ProtoSerializer
)