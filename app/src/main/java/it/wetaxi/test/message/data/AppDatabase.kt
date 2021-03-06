package it.wetaxi.test.message.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Message::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}