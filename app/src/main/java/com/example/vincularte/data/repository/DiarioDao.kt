package com.vincularte.data.repository

import androidx.room.*
import com.vincularte.data.model.DiarioEmocional
import kotlinx.coroutines.flow.Flow

@Dao
interface DiarioDao {
    @Query("SELECT * FROM diario_emocional ORDER BY fecha DESC")
    fun getAll(): Flow<List<DiarioEmocional>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DiarioEmocional)

    @Delete
    suspend fun delete(entry: DiarioEmocional)
}
