package com.example.holu.hinhtetvs1.ui.main

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.holu.hinhtetvs1.R
import kotlinx.android.synthetic.main.adapter_item_recycler_home.view.*

/**
 * Project Name Hinhtetvs1
 * Created by HoLu on 11/21/2017.
 * All rights reserved
 */
class HomeAdapter(var listData : ArrayList<PictureModel>,var onClickDownload : (PictureModel)->Unit,var onClickShare:(PictureModel)->Unit)
    : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.adapter_item_recycler_home,parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        holder!!.bindItem(listData.get(position))
        holder.itemView.adapterRecyclerHome_linear_download.setOnClickListener {
            onClickDownload(listData[position])
        }
        holder.itemView.adapterRecyclerHome_linear_share.setOnClickListener {
            onClickShare(listData[position])
        }
    }

    override fun getItemCount(): Int {
        if (listData.size>10){
            return 10
        }
        else{
            return listData.size
        }
    }

    class HomeViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItem(item: PictureModel){
            Glide.with(itemView).load(item.url).into(itemView.adapterRecyclerHome_img_picture)
            itemView.adapterRecyclerHome_txt_download.text = "("+item.download.toString()+")"
            itemView.adapterRecyclerHome_txt_share.text = "("+item.share.toString()+")"
        }
    }
}