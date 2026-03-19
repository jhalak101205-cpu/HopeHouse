package com.example.hopehouse.data

/**
 * Simple data models for the dummy HopeHouse app.
 *
 * There is no backend; lists + progress are stored in memory via [HopeHouseRepository]
 * and user activity is tracked in [HopeHouseSession].
 */
data class Ngo(
    val id: String,
    val name: String,
    val category: String,
    val location: String,
    val imageResId: Int,
    val needs: List<String>,
    val campaigns: List<String>
)

data class FoodPost(
    val id: String,
    val location: String,
    val quantityKg: Int,
    val timeLabel: String,
    var claimed: Boolean = false
)

data class VolunteerOpportunity(
    val id: String,
    val ngoId: String,
    val title: String,
    val skills: List<String>,
    val availability: String
)

data class Event(
    val id: String,
    val ngoId: String,
    val ngoName: String,
    val dateLabel: String,
    val title: String
)

data class Cause(
    val id: String,
    val title: String,
    val target: Int,
    var progress: Int,
    val unit: String
)

