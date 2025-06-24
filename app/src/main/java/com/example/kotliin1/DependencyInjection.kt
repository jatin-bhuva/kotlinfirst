package com.example.kotliin1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotliin1.databinding.ActivityDependenctInjectionBinding
import com.example.kotliin1.databinding.ActivityMainBinding
import com.example.kotliin1.di.data.ui.UserPagingAdapter
import com.example.kotliin1.di.data.ui.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DependencyInjection : AppCompatActivity() {

    private lateinit var binding: ActivityDependenctInjectionBinding
    private val viewModel: UserViewModel by viewModels()
    private val adapter = UserPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dependenct_injection)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        lifecycleScope.launch {
            Log.d("Test pagedUsers.....","./")
            viewModel.pagedUsers.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}