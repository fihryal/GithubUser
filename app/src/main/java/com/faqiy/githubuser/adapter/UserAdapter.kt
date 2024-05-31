package com.faqiy.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.faqiy.githubuser.data.modal.ResponseUser
import com.faqiy.githubuser.databinding.ItemUserBinding

class UserAdapter (private val listener : (ResponseUser.Item) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val listUser : MutableList<ResponseUser.Item> = mutableListOf ()

    fun setData(list: List<ResponseUser.Item>){
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

    inner class UserViewHolder (val binding: ItemUserBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= UserViewHolder (
        ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = listUser[position]
        holder.binding.apply {
            tvName.text = data.login
            profileImage.load(data.avatar_url){
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                listener(data)
            }
        }



    }
}