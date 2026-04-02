package com.example.kk.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var orderDao: OrderDao
    private lateinit var riderDao: RiderDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        orderDao = db.orderDao()
        riderDao = db.riderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetOrder() = runBlocking {
        val order = OrderEntity(
            orderId = "TEST_001",
            pickupAddress = "Test Pickup",
            pickupLat = 19.0760,
            pickupLng = 72.8777,
            dropAddress = "Test Drop",
            dropLat = 19.0821,
            dropLng = 72.8826,
            customerName = "Test Customer",
            customerPhone = "+919876543210",
            distanceKm = 2.5,
            earning = 100,
            paymentType = "COD",
            status = "PENDING"
        )

        orderDao.insertOrder(order)
        val retrievedOrder = orderDao.getOrderById("TEST_001")

        assertNotNull(retrievedOrder)
        assertEquals(order.orderId, retrievedOrder?.orderId)
        assertEquals(order.customerName, retrievedOrder?.customerName)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRider() = runBlocking {
        val rider = RiderEntity(
            riderId = "RIDER_TEST",
            name = "Test Rider",
            phone = "+919876543211",
            email = "test@example.com",
            totalEarnings = 1000.0,
            totalOrders = 10,
            rating = 4.5f
        )

        riderDao.insertRider(rider)
        val retrievedRider = riderDao.getRider()

        assertNotNull(retrievedRider)
        assertEquals(rider.riderId, retrievedRider?.riderId)
        assertEquals(rider.name, retrievedRider?.name)
    }
}