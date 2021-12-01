package com.example.mvvmandroiddemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mvvmandroiddemo.R
import com.example.mvvmandroiddemo.models.UserModel
import com.example.mvvmandroiddemo.utils.OnActionListener

class MainAdapter(
    private var items: List<UserModel>,
    private var onActionListener: OnActionListener<UserModel>
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.textView2).text = model.type
            findViewById<TextView>(R.id.textView).text = model.login

            findViewById<ImageView>(R.id.iv_profile)
                .load(model.avatar_url) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    transformations(CircleCropTransformation())
                }

            setOnClickListener { onActionListener.notify(model, holder.adapterPosition) }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}