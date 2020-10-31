package eu.profinit.pml.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.profinit.pml.databinding.ActivityDashboardPlanBinding

class PlanDetailActivity : AppCompatActivity() {

    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}