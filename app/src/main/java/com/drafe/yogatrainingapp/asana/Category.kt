package com.drafe.yogatrainingapp.asana

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Category")
data class Category (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val category: String = "Category"
    )
