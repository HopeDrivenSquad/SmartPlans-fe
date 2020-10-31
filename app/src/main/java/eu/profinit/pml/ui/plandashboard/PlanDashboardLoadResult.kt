package eu.profinit.pml.ui.plandashboard

/**
 * Authentication result : success (user details) or error message.
 */
data class PlanDashboardLoadResult(
        val error: Int? = null
)