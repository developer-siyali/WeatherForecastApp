package com.example.weathermapapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermapapp.data.FavouriteLocations
import kotlinx.android.synthetic.main.list_item.view.*

class FavouritesListAdapter(
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<FavouritesListAdapter.FavouritesViewHolder>() {

    private var locationList = emptyList<FavouriteLocations>()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if ( position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        return FavouritesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return locationList.size
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val currentItem = locationList[position]
        holder.itemView.item_number.text = (position + 1).toString()
        holder.itemView.city_name.text = currentItem.cityName
        holder.itemView.country_name.text = currentItem.country
    }

    fun setData(locations: List<FavouriteLocations>) {
        this.locationList = locations
        notifyDataSetChanged()
    }

}