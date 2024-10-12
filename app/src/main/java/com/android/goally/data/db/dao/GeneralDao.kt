package com.android.goally.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.goally.data.db.entities.token.Authentication


@Dao
interface GeneralDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthentication(authentication: Authentication)

    @Update
    suspend fun updateAuthentication(authentication: Authentication)

    @Query("Select * from authentication")
    fun getAuthenticationLive(): LiveData<Authentication?>
    @Query("Select * from authentication")
    suspend fun getAuthentication(): Authentication?
}