package se.kicksort.metalarchives.network

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type

/**
 * Created by Gustav Sundin on 10/03/17.
 */

internal class MyDeserializer<T> : JsonDeserializer<T> {
    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type, jdc: JsonDeserializationContext): T? {
        val content = je.asJsonObject
        return Gson().fromJson<T>(content, type)
    }
}