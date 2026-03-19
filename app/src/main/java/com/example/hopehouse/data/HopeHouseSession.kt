package com.example.hopehouse.data

/**
 * In-memory session/state for the demo app.
 *
 * Notes:
 * - No backend: counts are reset if the app process is killed.
 * - Activity screens read/update these counters to show a dashboard.
 */
object HopeHouseSession {
    var userRole: String? = null

    var donationMoneyCount: Int = 0
    var donationItemsCount: Int = 0
    var volunteeringCount: Int = 0
    var foodPostsCount: Int = 0
    var foodClaimsCount: Int = 0
    var eventRegistrationsCount: Int = 0

    fun recordDonateMoney() {
        donationMoneyCount++
    }

    fun recordDonateItems() {
        donationItemsCount++
    }

    fun recordVolunteer() {
        volunteeringCount++
    }

    fun recordFoodPost() {
        foodPostsCount++
    }

    fun recordFoodClaim() {
        foodClaimsCount++
    }

    fun recordEventRegistration() {
        eventRegistrationsCount++
    }
}

