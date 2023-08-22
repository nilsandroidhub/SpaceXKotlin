package com.example.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.myapplication.Utils
import com.bumptech.glide.Glide
import com.example.myapplication.data.Launch
import com.example.myapplication.databinding.ActivityLaunchDetailBinding

class SpaceXDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchDetailBinding

    private var mPatch: String? = null
    private var mName: String? = null
    private var mNumber = 0
    private var mDate: String? = null
    private var mDetails: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        binding = ActivityLaunchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        mPatch = intent.getStringExtra(DETAILS_PATCH)
        mName = intent.getStringExtra(DETAILS_NAME)
        mNumber = intent.getIntExtra(DETAILS_NUMBER, 0)
        mDate = intent.getStringExtra(DETAILS_DATE)
        mDetails = intent.getStringExtra(DETAILS_DETAILS)
        initViews(intent)
    }

    private fun initViews(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = intent.getStringExtra(DETAILS_TRANSITION_NAME)
            binding.ivLaunch.transitionName = imageTransitionName
        }
        Glide.with(applicationContext).load(mPatch).into(binding.ivLaunch)
        binding.txtMissionName.text = String.format("Mission Name: %s", mName)
        System.out.println("SpaceX " + "binding.txtMissionName.text " + String.format("Mission Name: %s", mName))
        binding.txtFlightNumber.text = String.format("Flight Number: %s", mNumber.toString())
        binding.txtLaunchDate.text = String.format(
            "Launch Date: %s",
            Utils.convertToDate(mDate)
        )
        binding.txtDetail.text = String.format("Mission Name: %s", mName)
    }

    companion object {
        private const val DETAILS_PATCH = "details_patch"
        private const val DETAILS_NAME = "details_name"
        private const val DETAILS_NUMBER = "details_number"
        private const val DETAILS_DATE = "details_date"
        private const val DETAILS_DETAILS = "details_details"
        private const val DETAILS_TRANSITION_NAME = "details_transition_name"
        @JvmStatic
        fun newIntent(context: Context?, launchInfo: Launch, launchIcon: ImageView?): Intent {
            val intent = Intent(context, SpaceXDetailsActivity::class.java)
            intent.putExtra(DETAILS_PATCH, launchInfo.links.patch.small)
            intent.putExtra(DETAILS_NAME, launchInfo.name)
            intent.putExtra(DETAILS_NUMBER, launchInfo.flight_number.toInt())
            intent.putExtra(DETAILS_DATE, launchInfo.date_local)
            intent.putExtra(DETAILS_DETAILS, launchInfo.details)
            intent.putExtra(
                DETAILS_TRANSITION_NAME, ViewCompat.getTransitionName(
                    launchIcon!!
                )
            )
            return intent
        }
    }
}