package com.example.gagan.maymay

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by GAGAN on 06-02-2018.
 */
class MemeAdapter(private val memeList:ArrayList<Memecard>,var context:Context):RecyclerView.Adapter<MemeAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val myView=LayoutInflater.from(context).inflate(R.layout.memecard3,parent,false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return memeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var meme=memeList[position]
        holder!!.tv.text=meme.memeText
        Picasso.with(context).load(meme.memeImage).into(holder.iv)
        holder!!.username.text=meme.userName
    }

   inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

       var iv:ImageView=itemView.findViewById(R.id.ivMeme) as ImageView
       var tv:TextView=itemView.findViewById(R.id.mmeme3) as TextView
       var username:TextView=itemView.findViewById(R.id.mname3) as TextView

    }

}