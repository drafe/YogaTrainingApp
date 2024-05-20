package com.drafe.yogatrainingapp


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID


@Entity(tableName = "Train")
data class TrainHistory(
    @PrimaryKey
     val id:UUID = UUID.randomUUID(),
     val asanName:String = "ASAN",
     val date: Date = Date(),
     val score: Int = 50,
     val comment: String = "Good"
)
