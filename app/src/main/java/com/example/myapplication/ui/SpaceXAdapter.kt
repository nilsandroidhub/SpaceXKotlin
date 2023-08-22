package com.example.myapplication.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Utils
import com.example.myapplication.data.Launch
import com.example.myapplication.databinding.LaunchListItemBinding

class SpaceXAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SpaceXAdapter.LaunchViewHolder>()
 {
     private val launches = mutableListOf<Launch>()

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
         val binding = LaunchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
         return LaunchViewHolder(binding)
     }

     override fun getItemCount() = launches.size

     override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
         holder.bind(launches[position], itemClickListener)
     }

     fun submitList(launches1: List<Launch>) {
         launches.clear()
         launches.addAll(launches1)
         notifyDataSetChanged()
     }

     class LaunchViewHolder(private val binding: LaunchListItemBinding) : RecyclerView.ViewHolder(binding.root) {
         fun bind(launch: Launch, itemClickListener: SpaceXAdapter.OnItemClickListener) {
             binding.launchNumber.text = launch.flight_number.toInt().toString()
             binding.launchName.text = launch.name
             if (launch.success) {
                 binding.launchSuccess.setBackgroundColor(Color.GREEN)
             } else {
                 binding.launchSuccess.setBackgroundColor(Color.RED)
             }
             binding.launchDate.text = Utils.convertToDate(launch.date_local)
             Glide.with(binding.launchIcon.context).load(launch.links.patch.small).into(binding.launchIcon)

             itemView.setOnClickListener {
                 itemClickListener.onItemClicked(
                     launch,
                     binding.launchIcon
                 )
             }

             ViewCompat.setTransitionName(binding.launchIcon, launch.name)
         }
     }

     interface OnItemClickListener {
         fun onItemClicked(launch: Launch, launchIcon: ImageView)
     }
}