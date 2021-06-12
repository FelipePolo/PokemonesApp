package com.felipepolo.pokedexchallengue.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipepolo.pokedexchallengue.R
import org.xmlpull.v1.XmlPullParser

class RecycleViewSimpleAdapter(var context: Context , var list: ArrayList<String>): RecyclerView.Adapter<RecycleViewSimpleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vista = LayoutInflater.from(context).inflate(R.layout.template_simple_list,parent,false)
        var viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvname!!.text = list.get(position)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvname: TextView ? = null
        var container: LinearLayout? = null
        init {
            tvname = itemView.findViewById(R.id.tvname) as TextView
            container = itemView.findViewById(R.id.container) as LinearLayout
        }
    }
}