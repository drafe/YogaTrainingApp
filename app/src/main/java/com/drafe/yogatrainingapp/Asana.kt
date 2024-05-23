package com.drafe.yogatrainingapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Asana")
data class Asana (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val nameEng: String = "Asana",
    val nameHin: String = "Asana",
    val imgName: String? = "Good"
)

