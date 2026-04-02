package com.example.kk.database

import android.content.Context
import com.example.kk.models.Order
import com.example.kk.models.LocationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseHelper(context: Context) {

    private val database = AppDatabase.getDatabase(context)
    private val orderDao = database.orderDao()
    private val riderDao = database.riderDao()

    // Order Operations
    suspend fun saveOrder(order: Order) = withContext(Dispatchers.IO) {
        val orderEntity = OrderEntity(
            orderId = order.orderId,
            pickupAddress = order.pickupLocation?.address ?: "",
            pickupLat = order.pickupLocation?.lat ?: 0.0,
            pickupLng = order.pickupLocation?.lng ?: 0.0,
            dropAddress = order.dropLocation?.address ?: "",
            dropLat = order.dropLocation?.lat ?: 0.0,
            dropLng = order.dropLocation?.lng ?: 0.0,
            customerName = order.customerName,
            customerPhone = order.customerPhone,
            distanceKm = order.distanceKm,
            earning = order.earning,
            paymentType = order.paymentType,
            status = order.status
        )
        orderDao.insertOrder(orderEntity)
    }

    suspend fun saveOrders(orders: List<Order>) = withContext(Dispatchers.IO) {
        val orderEntities = orders.map { order ->
            OrderEntity(
                orderId = order.orderId,
                pickupAddress = order.pickupLocation?.address ?: "",
                pickupLat = order.pickupLocation?.lat ?: 0.0,
                pickupLng = order.pickupLocation?.lng ?: 0.0,
                dropAddress = order.dropLocation?.address ?: "",
                dropLat = order.dropLocation?.lat ?: 0.0,
                dropLng = order.dropLocation?.lng ?: 0.0,
                customerName = order.customerName,
                customerPhone = order.customerPhone,
                distanceKm = order.distanceKm,
                earning = order.earning,
                paymentType = order.paymentType,
                status = order.status
            )
        }
        orderDao.insertOrders(orderEntities)
    }

    suspend fun getAllOrders(): List<Order> = withContext(Dispatchers.IO) {
        orderDao.getAllOrders().map { entity ->
            Order(
                orderId = entity.orderId,
                pickupLocation = LocationData(
                    address = entity.pickupAddress,
                    lat = entity.pickupLat,
                    lng = entity.pickupLng
                ),
                dropLocation = LocationData(
                    address = entity.dropAddress,
                    lat = entity.dropLat,
                    lng = entity.dropLng
                ),
                customerName = entity.customerName,
                customerPhone = entity.customerPhone,
                distanceKm = entity.distanceKm,
                earning = entity.earning,
                paymentType = entity.paymentType,
                status = entity.status
            )
        }
    }

    suspend fun getOrdersByStatus(status: String): List<Order> = withContext(Dispatchers.IO) {
        orderDao.getOrdersByStatus(status).map { entity ->
            Order(
                orderId = entity.orderId,
                pickupLocation = LocationData(
                    address = entity.pickupAddress,
                    lat = entity.pickupLat,
                    lng = entity.pickupLng
                ),
                dropLocation = LocationData(
                    address = entity.dropAddress,
                    lat = entity.dropLat,
                    lng = entity.dropLng
                ),
                customerName = entity.customerName,
                customerPhone = entity.customerPhone,
                distanceKm = entity.distanceKm,
                earning = entity.earning,
                paymentType = entity.paymentType,
                status = entity.status
            )
        }
    }

    suspend fun updateOrderStatus(orderId: String, status: String) = withContext(Dispatchers.IO) {
        val order = orderDao.getOrderById(orderId)
        order?.let {
            val updatedOrder = it.copy(status = status)
            orderDao.updateOrder(updatedOrder)
        }
    }

    suspend fun getCompletedOrdersCount(): Int = withContext(Dispatchers.IO) {
        orderDao.getCompletedOrdersCount()
    }

    suspend fun getTotalEarnings(): Int = withContext(Dispatchers.IO) {
        orderDao.getTotalEarnings() ?: 0
    }

    // Rider Operations
    suspend fun saveRider(rider: RiderEntity) = withContext(Dispatchers.IO) {
        riderDao.insertRider(rider)
    }

    suspend fun getRider(): RiderEntity? = withContext(Dispatchers.IO) {
        riderDao.getRider()
    }

    suspend fun updateRiderStats(riderId: String, earnings: Double, orders: Int, rating: Float) = withContext(Dispatchers.IO) {
        riderDao.updateRiderStats(riderId, earnings, orders, rating)
    }

    // Clear all data
    suspend fun clearAllData() = withContext(Dispatchers.IO) {
        orderDao.deleteAllOrders()
        riderDao.deleteAllRiders()
    }

    // Seed sample data for testing
    suspend fun seedSampleData() = withContext(Dispatchers.IO) {
        try {
            // Clear existing data
            clearAllData()

            // Add sample rider
            val rider = RiderEntity(
                riderId = "RIDER_001",
                name = "John Doe",
                phone = "+919876543210",
                email = "john@example.com",
                totalEarnings = 3250.0,
                totalOrders = 28,
                rating = 4.8f
            )
            riderDao.insertRider(rider)

            // Add sample orders
            val sampleOrders = listOf(
                OrderEntity(
                    orderId = "ORD_001",
                    pickupAddress = "Pizza Hut, Andheri West",
                    pickupLat = 19.1353,
                    pickupLng = 72.8296,
                    dropAddress = "Bandra West, Mumbai",
                    dropLat = 19.0596,
                    dropLng = 72.8295,
                    customerName = "Alice Smith",
                    customerPhone = "+919876543211",
                    distanceKm = 4.2,
                    earning = 120,
                    paymentType = "COD",
                    status = "COMPLETED"
                ),
                OrderEntity(
                    orderId = "ORD_002",
                    pickupAddress = "McDonald's, Powai",
                    pickupLat = 19.1176,
                    pickupLng = 72.9056,
                    dropAddress = "Andheri East, Mumbai",
                    dropLat = 19.1136,
                    dropLng = 72.8697,
                    customerName = "Bob Johnson",
                    customerPhone = "+919876543212",
                    distanceKm = 3.8,
                    earning = 100,
                    paymentType = "ONLINE",
                    status = "COMPLETED"
                ),
                OrderEntity(
                    orderId = "ORD_003",
                    pickupAddress = "Starbucks, Lower Parel",
                    pickupLat = 18.9941,
                    pickupLng = 72.8258,
                    dropAddress = "Worli, Mumbai",
                    dropLat = 19.0166,
                    dropLng = 72.8166,
                    customerName = "Charlie Brown",
                    customerPhone = "+919876543213",
                    distanceKm = 2.5,
                    earning = 80,
                    paymentType = "COD",
                    status = "PENDING"
                )
            )

            orderDao.insertOrders(sampleOrders)

        } catch (e: Exception) {
            throw e
        }
    }
}