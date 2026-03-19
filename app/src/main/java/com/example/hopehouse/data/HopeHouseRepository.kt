package com.example.hopehouse.data

import kotlin.math.min

/**
 * Dummy/static data for NGOs, events, opportunities, causes, and food posts.
 *
 * All stateful demo interactions (posting/claiming food, donations progress)
 * update in-memory mutable lists.
 */
object HopeHouseRepository {
    val ngos: List<Ngo> = listOf(
        Ngo(
            id = "ngo_1",
            name = "GreenLeaf Foundation",
            category = "Environment",
            location = "Austin, TX",
            imageResId = android.R.drawable.ic_menu_gallery,
            needs = listOf("Community clean-up supplies", "Reusable bags", "Recycling bins"),
            campaigns = listOf("River Cleanup Drive", "Tree Planting Month")
        ),
        Ngo(
            id = "ngo_2",
            name = "HopeMeals Network",
            category = "Food",
            location = "San Jose, CA",
            imageResId = android.R.drawable.ic_menu_sort_by_size,
            needs = listOf("Surplus produce", "Packaging containers", "Volunteers for sorting"),
            campaigns = listOf("Weekend Food Rescue", "Fresh Produce Drop-off")
        ),
        Ngo(
            id = "ngo_3",
            name = "BrightPath Education",
            category = "Education",
            location = "Chicago, IL",
            imageResId = android.R.drawable.ic_menu_edit,
            needs = listOf("School supplies", "Tutors", "Learning kits"),
            campaigns = listOf("Back-to-School Drive", "After-school Tutoring")
        ),
        Ngo(
            id = "ngo_4",
            name = "CareBridge Health",
            category = "Health",
            location = "Phoenix, AZ",
            imageResId = android.R.drawable.ic_menu_info_details,
            needs = listOf("First-aid kits", "Health screenings", "Care packages"),
            campaigns = listOf("Community Health Fair", "Wellness Checks")
        ),
        Ngo(
            id = "ngo_5",
            name = "ShelterSpark Alliance",
            category = "Shelter",
            location = "Denver, CO",
            imageResId = android.R.drawable.ic_menu_myplaces,
            needs = listOf("Winter coats", "Sleeping bags", "Hygiene products"),
            campaigns = listOf("Warm Nights Program", "Family Hygiene Packs")
        ),
        Ngo(
            id = "ngo_6",
            name = "DisasterRelief Now",
            category = "Disaster Relief",
            location = "Tampa, FL",
            imageResId = android.R.drawable.ic_menu_compass,
            needs = listOf("Emergency meals", "Blankets", "Rapid response volunteers"),
            campaigns = listOf("Storm Response Drive", "Emergency Supply Runs")
        )
    )

    val volunteerOpportunities: List<VolunteerOpportunity> = listOf(
        VolunteerOpportunity(
            id = "vol_1",
            ngoId = "ngo_2",
            title = "Food Sorting & Packaging",
            skills = listOf("Teamwork", "Basic hygiene"),
            availability = "Weekends (2-4 hrs)"
        ),
        VolunteerOpportunity(
            id = "vol_2",
            ngoId = "ngo_3",
            title = "After-school Math Tutor",
            skills = listOf("Math", "Patience"),
            availability = "Mon/Wed (4-6 pm)"
        ),
        VolunteerOpportunity(
            id = "vol_3",
            ngoId = "ngo_4",
            title = "Wellness Fair Assistant",
            skills = listOf("Customer support", "Comfort with forms"),
            availability = "Monthly (8-12 hrs)"
        ),
        VolunteerOpportunity(
            id = "vol_4",
            ngoId = "ngo_5",
            title = "Hygiene Kit Builder",
            skills = listOf("Organizing", "Careful packing"),
            availability = "Evenings (2 hrs)"
        ),
        VolunteerOpportunity(
            id = "vol_5",
            ngoId = "ngo_1",
            title = "Community Cleanup Crew",
            skills = listOf("Physical activity", "Safety awareness"),
            availability = "First Saturday each month"
        ),
        VolunteerOpportunity(
            id = "vol_6",
            ngoId = "ngo_6",
            title = "Emergency Supply Runner",
            skills = listOf("Driving", "Quick coordination"),
            availability = "On-call (short notice)"
        )
    )

    val events: List<Event> = listOf(
        Event("evt_1", "ngo_2", "HopeMeals Network", "2026-04-05", "Weekend Food Rescue"),
        Event("evt_2", "ngo_3", "BrightPath Education", "2026-04-12", "Back-to-School Drive"),
        Event("evt_3", "ngo_1", "GreenLeaf Foundation", "2026-04-19", "River Cleanup Drive"),
        Event("evt_4", "ngo_4", "CareBridge Health", "2026-04-20", "Community Health Fair"),
        Event("evt_5", "ngo_5", "ShelterSpark Alliance", "2026-04-27", "Warm Nights Program"),
        Event("evt_6", "ngo_6", "DisasterRelief Now", "2026-05-03", "Storm Response Drive"),
        Event("evt_7", "ngo_2", "HopeMeals Network", "2026-05-10", "Fresh Produce Drop-off"),
        Event("evt_8", "ngo_3", "BrightPath Education", "2026-05-17", "After-school Tutoring Kickoff")
    )

    // Mutable state for food posts and donations so that screens can reflect user actions.
    val foodPosts: MutableList<FoodPost> = mutableListOf(
        FoodPost("food_1", "San Jose Downtown", 12, "Today • 6:00 PM", claimed = false),
        FoodPost("food_2", "Campbell Market", 7, "Tomorrow • 9:30 AM", claimed = false)
    )

    val causes: MutableList<Cause> = mutableListOf(
        Cause("cause_1", "Meals for Families", target = 5000, progress = 1800, unit = "USD"),
        Cause("cause_2", "School Kits & Supplies", target = 3000, progress = 950, unit = "USD"),
        Cause("cause_3", "Health & Wellness Packs", target = 2500, progress = 1240, unit = "USD"),
        Cause("cause_4", "Emergency Shelter Support", target = 4000, progress = 2100, unit = "USD")
    )

    // Demo registrations to prevent duplicates.
    private val registeredVolunteerIds: MutableSet<String> = mutableSetOf()
    private val registeredEventIds: MutableSet<String> = mutableSetOf()

    fun getNgoById(ngoId: String): Ngo? = ngos.firstOrNull { it.id == ngoId }

    fun markFoodClaimed(foodPostId: String): Boolean {
        val post = foodPosts.firstOrNull { it.id == foodPostId } ?: return false
        return if (post.claimed) {
            false
        } else {
            post.claimed = true
            true
        }
    }

    fun addFoodPost(post: FoodPost) {
        foodPosts.add(post)
    }

    fun isVolunteerRegistered(opportunityId: String): Boolean = registeredVolunteerIds.contains(opportunityId)

    fun registerVolunteer(opportunityId: String) {
        registeredVolunteerIds.add(opportunityId)
    }

    fun isEventRegistered(eventId: String): Boolean = registeredEventIds.contains(eventId)

    fun registerEvent(eventId: String) {
        registeredEventIds.add(eventId)
    }

    fun donateToCauseMoney(causeId: String, amount: Int) {
        val cause = causes.firstOrNull { it.id == causeId } ?: return
        cause.progress = min(cause.target, cause.progress + amount)
    }

    fun donateToCauseItems(causeId: String, progressDelta: Int) {
        val cause = causes.firstOrNull { it.id == causeId } ?: return
        cause.progress = min(cause.target, cause.progress + progressDelta)
    }
}
