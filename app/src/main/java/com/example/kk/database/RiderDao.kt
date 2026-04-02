package com.example.kk.database

import androidx.room.*

@Dao
interface RiderDao {
    @Query("SELECT * FROM riders LIMIT 1")
    suspend fun getRider(): RiderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRider(rider: RiderEntity)

    @Update
    suspend fun updateRider(rider: RiderEntity)

    @Query("DELETE FROM riders")
    suspend fun deleteAllRiders()

    @Query("UPDATE riders SET totalEarnings = :earnings, totalOrders = :orders, rating = :rating WHERE riderId = :riderId")
    suspend fun updateRiderStats(riderId: String, earnings: Double, orders: Int, rating: Float)
}