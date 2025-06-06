package com.example.dogapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dogapp.data.entity.CitaEntity

@Dao
interface CitaDao {
    @Query("SELECT * FROM citas")
    suspend fun getAll(): List<CitaEntity>

    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun obtenerCitaPorId(id: Int): CitaEntity

    @Query("DELETE FROM citas WHERE id = :id")
    suspend fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg citas: CitaEntity)

    @Insert
    suspend fun insert(cita: CitaEntity)


}