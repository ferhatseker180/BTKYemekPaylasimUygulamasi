package com.example.paylasimuygulamasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paylasimuygulamasi.R
import com.example.paylasimuygulamasi.model.Post
import com.example.paylasimuygulamasi.view.AkisActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class AkisRecyclerAdapter(val postList:ArrayList<Post>) : RecyclerView.Adapter<AkisRecyclerAdapter.PostHolder>(){

    class PostHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.itemView.recycler_row_kullanici_email.text = postList[position].kullaniciEmail
        holder.itemView.recyler_row_kullanici_yorum.text = postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recyler_row_imageview)
    }
}