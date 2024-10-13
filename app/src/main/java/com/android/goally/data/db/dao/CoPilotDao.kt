package com.android.goally.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.goally.data.model.api.response.copilot.Routine

@Dao
interface CoPilotDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutines(routines: List<Routine>)

    @Query("SELECT * FROM routine_table WHERE _id = :id")
    suspend fun getRoutineById(id: String): Routine?

    @Query("SELECT * FROM routine_table")
    suspend fun getAllRoutines(): List<Routine>
}