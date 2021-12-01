package com.example.mvvmandroiddemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmandroiddemo.databinding.ActivityMainBinding
import com.example.mvvmandroiddemo.models.UserModel
import com.example.mvvmandroiddemo.repositary.UserRepository
import com.example.mvvmandroiddemo.ui.adapter.MainAdapter
import com.example.mvvmandroiddemo.utils.OnActionListener
import com.example.mvvmandroiddemo.viewModel.UserViewModel
import com.example.mvvmandroiddemo.viewModel.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MainAdapter
    private lateinit var items: MutableList<UserModel>

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        items = arrayListOf()

        initAdapter()
        initViewModel()
        initApi()
    }

    private fun initApi() {
        viewModel.getAllUsers()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(UserRepository())
        ).get(UserViewModel::class.java)

        viewModel.apply {

            getError().observe(this@MainActivity, {
                highlightError(it)
            })

            getIsLoading().observe(this@MainActivity, {

                binding.progressBar.visibility =
                    if (it)
                        View.VISIBLE
                    else
                        View.GONE
            })

            getUsers().observe(this@MainActivity, {
                it?.let {

                    binding.recyclerView.visibility = View.VISIBLE

                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }

    private fun initAdapter() {
        val onActionListener = object : OnActionListener<UserModel>{
            override fun notify(model: UserModel, position: Int) {
                 UserDetailsActivity.start(this@MainActivity, model)
            }
        }

        adapter = MainAdapter(items, onActionListener)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter
        }
    }

    private fun highlightError(error: String?) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        if (error != null) {
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
        }
    }
}
