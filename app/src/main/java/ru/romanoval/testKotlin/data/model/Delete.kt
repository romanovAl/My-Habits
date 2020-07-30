package ru.romanoval.testKotlin.data.model

import com.google.gson.*
import java.io.Serializable
import java.lang.reflect.Type

data class Delete (val uid: String): Serializable{

    class DeleteSerializer : JsonSerializer<Delete>{
        override fun serialize(
            src: Delete?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonObject().apply {
                addProperty("uid", src?.uid)
            }
        }
    }

}