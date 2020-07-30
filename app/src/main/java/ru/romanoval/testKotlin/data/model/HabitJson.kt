package ru.romanoval.testKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Type

@Entity
data class HabitJson(
    @SerializedName("uid")
    var uid: String?,
    @PrimaryKey(autoGenerate = true)
    var bdId: Int?,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("priority")
    var priority: Int,
    @SerializedName("type")
    var type: Int,
    @SerializedName("count")
    var count: Int,
    @SerializedName("frequency")
    var frequency: Int,
    @SerializedName("color")
    var color: Int,
    @SerializedName("date")
    var date: Long,
    @TypeConverters(ListConverter::class)
    @SerializedName("done_dates")
    var done_dates: List<String>
) : Serializable {

    class ListConverter {
        @TypeConverter
        fun fromHabits(habits: List<String>): String {
            return habits.joinToString(",")
        }

        @TypeConverter
        fun toHabits(data: String): List<String> {
            return data.split(",")
        }
    }

    class HabitJsonSerializer : JsonSerializer<HabitJson> {
        override fun serialize(
            src: HabitJson?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonObject().apply {
                addProperty("uid", src?.uid)
                addProperty("title", src?.title)
                addProperty("description", src?.description)
                addProperty("priority", src?.priority)
                addProperty("type", src?.type)
                addProperty("count", src?.count)
                addProperty("frequency", src?.frequency)
                addProperty("color", src?.color)
                addProperty("date", src?.date)
//                addProperty("done_dates", src?.done_dates?.joinToString(","))
            }
        }
    }

    class HabitJsonDeserializer : JsonDeserializer<HabitJson> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): HabitJson {
            return HabitJson(
                uid = json.asJsonObject.get("uid").asString,
                bdId = null,
                title = json.asJsonObject.get("title").asString,
                description = json.asJsonObject.get("description").asString,
                priority = json.asJsonObject.get("priority").asInt,
                type = json.asJsonObject.get("type").asInt,
                count = json.asJsonObject.get("count").asInt,
                frequency = json.asJsonObject.get("frequency").asInt,
                color = json.asJsonObject.get("color").asInt,
                date = json.asJsonObject.get("date").asLong,
                done_dates = emptyList()
            )

        }
    }


}

