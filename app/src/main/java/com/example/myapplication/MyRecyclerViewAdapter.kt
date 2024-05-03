package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MyRecyclerViewAdapter(val mList:ArrayList<OneItem>):RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val VHname: TextView = itemView.findViewById(R.id.iName)
        val VHcounty:TextView = itemView.findViewById(R.id.iCounty)
        val VHstatus:TextView = itemView.findViewById(R.id.iStatus)
        val VHpm25 : TextView = itemView.findViewById(R.id.iPM25)
        val wind:TextView=itemView.findViewById(R.id.iWind)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.oneitem,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oneItem=mList[position]
        holder.VHname.text = oneItem.iname
        holder.VHcounty.text = oneItem.icounty
        holder.VHstatus.text = oneItem.istatus
        holder.VHpm25.text = oneItem.ipm25
        holder.wind.text = oneItem.iwind
        if (holder.VHpm25.text.toString().toInt()>15)
            holder.VHpm25.setTextColor(Color.RED)
        else
            holder.VHpm25.setTextColor(Color.BLACK)

        holder.itemView.setOnClickListener{
            //Toast.makeText(it.context,"${holder.wind.text}",Toast.LENGTH_LONG).show()
            AlertDialog.Builder(it.context)
                .setMessage(
                        "所在區域：${holder.VHcounty.text}\n"+
                        "風　速：${holder.wind.text}\n"+
                        "PM2.5：${holder.VHpm25.text}\n"+
                        "狀　態：${holder.VHstatus.text}\n")
                .setTitle("${holder.VHname.text}")
                .setPositiveButton("OK",null)
                .show()
        }
    }
}