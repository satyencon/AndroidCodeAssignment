package com.llyods.assignment.presentation.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.llyods.assignment.R
import com.llyods.assignment.databinding.ListUserItemBinding
import com.llyods.assignment.domain.datamodel.UserModel

class UserListAdapter(var context: Context): RecyclerView.Adapter<UserListAdapter.Holder>() {

    private val userList = arrayListOf<UserModel>()

    fun updateData(list: List<UserModel>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(context).inflate(R.layout.list_user_item, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val userdata = userList[position]
        with(holder) {
            Glide.with(binding.imageUser).load(userdata.avatar_url).into(binding.imageUser)
            binding.tvName.text = userdata.login

            itemView.setOnClickListener {
                onItemClick?.invoke(userdata)
            }
        }
    }

    private var onItemClick: ((UserModel) -> Unit)? = null
    fun onItemClick(listener: (UserModel) -> Unit) {
        onItemClick = listener
    }

    override fun getItemCount(): Int = userList.size

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ListUserItemBinding.bind(itemView)
    }
}