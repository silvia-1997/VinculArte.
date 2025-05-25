package com.vincularte.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diario_emocional")
data class DiarioEmocional(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val emocion: String,
    val notas: String
)
