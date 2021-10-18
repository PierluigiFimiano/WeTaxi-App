package it.wetaxi.test.message.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = -1,
    @ColumnInfo(name = "title") @Json(name = "tile") val title: String = "",
    @ColumnInfo(name = "text") @Json(name = "text") val text: String = "",
    @ColumnInfo(name = "date") @Json(name = "date") val date: String = "",
    @ColumnInfo(name = "time") @Json(name = "time") val time: String = "",
    @ColumnInfo(name = "priority") @Json(name = "priority") val priority: String = "",
    @ColumnInfo(name = "read") val read: Boolean = false
)