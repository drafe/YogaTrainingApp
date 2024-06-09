package com.drafe.yogatrainingapp.asana

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Asana",
    foreignKeys = [ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])])
data class Asana (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val nameEng: String = "Asana",
    val nameHin: String = "Asana",
    val imgName: String? = "Good",
    val description: String = "Asana",
    val pose: String? = "Asana",
    val difficulty: Int? = 1,
    val categoryId:  UUID? = null
)

