package com.drafe.yogatrainingapp.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.drafe.yogatrainingapp.asana.Asana
import java.util.Date
import java.util.UUID

@Entity(tableName = "Train",
 foreignKeys = [ForeignKey(entity = Asana::class, parentColumns = ["id"], childColumns = ["asanaId"])])
data class TrainHistory(
 @PrimaryKey
     val id:UUID = UUID.randomUUID(),
 val asanaId: UUID = UUID.randomUUID(),
 val date: Date = Date(),
 val score: Int = 50,
 val expertCom: String = "Good"
)
