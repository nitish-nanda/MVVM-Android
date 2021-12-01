package com.example.mvvmandroiddemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmandroiddemo.R
import com.example.mvvmandroiddemo.models.RepoModel

class RepoAdapter(
    private var items: List<RepoModel>
) :
    RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git_user_repo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_repo_name).text = model.name
            findViewById<TextView>(R.id.tv_repo_full_name).text = model.full_name
            findViewById<TextView>(R.id.tv_repo_lang).text = model.language
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}