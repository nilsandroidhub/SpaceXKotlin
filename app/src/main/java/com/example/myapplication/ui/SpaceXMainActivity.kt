package com.example.myapplication.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.Launch
import com.example.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpaceXMainActivity : AppCompatActivity(), SpaceXAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ViewModelLaunch by viewModels()
    private lateinit var launchAdapter: SpaceXAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        setUpViewModel()
        refreshswipe()
    }

    private fun setUpRecyclerView() {
        launchAdapter = SpaceXAdapter(this)
        binding.recyclerSpace.layoutManager = LinearLayoutManager(this)
        binding.recyclerSpace.adapter = launchAdapter
        binding.recyclerSpace.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                (binding.recyclerSpace.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun setUpViewModel() {
        viewModel.fetchLaunches()
        viewModel.launches.observe(this, Observer { launches ->
            launchAdapter.submitList(launches)
        })
    }

    private fun refreshswipe() {
        binding.swipespace.setOnRefreshListener {
            setUpRecyclerView()
            setUpViewModel()
            binding.swipespace.isRefreshing = false
        }
    }

    override fun onItemClicked(launch: Launch, launchIcon: ImageView) {
        val intent = SpaceXDetailsActivity.newIntent(this.applicationContext, launch, launchIcon)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            launchIcon,
            ViewCompat.getTransitionName(launchIcon)!!
        )
        startActivity(intent, options.toBundle())
    }
}


