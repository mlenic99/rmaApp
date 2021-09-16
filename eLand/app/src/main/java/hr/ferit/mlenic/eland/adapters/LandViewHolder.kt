package hr.ferit.mlenic.eland.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.mlenic.eland.R

class LandViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val refNum: TextView = itemView.findViewById<TextView>(R.id.tvLandID)
    val landName: TextView = itemView.findViewById<TextView>(R.id.tvLandName)
    val landDate: TextView = itemView.findViewById<TextView>(R.id.tvDateStamp)
    val landDelete: ImageButton = itemView.findViewById<ImageButton>(R.id.ibDelete)
    //val landLocate: ImageButton = itemView.findViewById<ImageButton>(R.id.ibLocate)

}