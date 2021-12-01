package com.example.mvvmandroiddemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mvvmandroiddemo.R
import com.example.mvvmandroiddemo.databinding.ActivityUserDetailsBinding
import com.example.mvvmandroiddemo.models.RepoModel
import com.example.mvvmandroiddemo.models.UserModel
import com.example.mvvmandroiddemo.repositary.UserRepository
import com.example.mvvmandroiddemo.ui.adapter.RepoAdapter
import com.example.mvvmandroiddemo.utils.Utils
import com.example.mvvmandroiddemo.viewModel.UserViewModel
import com.example.mvvmandroiddemo.viewModel.UserViewModelFactory

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userModel: UserModel

    private lateinit var adapter: RepoAdapter
    private lateinit var items: MutableList<RepoModel>

    private lateinit var viewModel: UserViewModel

    companion object {

        fun start(context: Context, model: UserModel) {
            val intent = Intent(context, UserDetailsActivity::class.java)
            intent.putExtra("userModel", model)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userModel = intent?.getSerializableExtra("userModel") as UserModel

        supportActionBar?.hide()
        items = arrayListOf()

        initViews()

        initAdapter()
        initViewModel()
        initApi()
    }

    private fun initApi() {
        viewModel.getUser(userModel.login!!)
        viewModel.getAllUserRepos(userModel.login!!)
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(UserRepository())
        ).get(UserViewModel::class.java)

        viewModel.apply {

            getError().observe(this@UserDetailsActivity, {
                Utils.showSnackbar(binding.root, it)
            })

            getIsLoading().observe(this@UserDetailsActivity, {

            })

            getUser().observe(this@UserDetailsActivity, {
                userModel = it
                initViews()
            })

            getUserRepos().observe(this@UserDetailsActivity, {
                items.clear()
                items.addAll(it)
                adapter.notifyDataSetChanged()
            })
        }
    }

    private fun initAdapter() {
        adapter = RepoAdapter(items)
        binding.rvRepo.apply {
            layoutManager = LinearLayoutManager(this@UserDetailsActivity)
            adapter = this@UserDetailsActivity.adapter
        }
    }

    private fun initViews() {
        binding.apply {

            ivBackDetails.setOnClickListener { onBackPressed() }

            ivProfileDetails.load(userModel.avatar_url) {
                crossfade(true)
                placeholder(R.drawable.image)
                transformations(CircleCropTransformation())
            }

            tvName.text = userModel.name
            tvBio.text = userModel.bio
        }
    }
}