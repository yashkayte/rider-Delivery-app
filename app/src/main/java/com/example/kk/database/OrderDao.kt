package com.example.kk.database

import androidx.room.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    suspend fun getAllOrders(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY timestamp DESC")
    suspend fun getOrdersByStatus(status: String): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: String): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("DELETE FROM orders WHERE orderId = :orderId")
    suspend fun deleteOrder(orderId: String)

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

    @Query("SELECT COUNT(*) FROM orders WHERE status = 'COMPLETED'")
    suspend fun getCompletedOrdersCount(): Int

    @Query("SELECT SUM(earning) FROM orders WHERE status = 'COMPLETED'")
    suspend fun getTotalEarnings(): Int?
}