package hr.ferit.mlenic.eland

import androidx.room.TypeConverter
import java.util.*

class Converters{
    @TypeConverter
    fun fromDateTime(value: Long?): Date?{
        return value?.let { Date(it) }
    }
}