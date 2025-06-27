package com.example.kotliin1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.kotliin1.databinding.ActivityKoinBinding
import com.example.kotliin1.ui.MainViewModel
import com.example.kotliin1.ui.RecipeIntent
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class Koin : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()
//    private val hello by inject<String>(named("second"))

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityKoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_koin)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val id = s?.toString()?.trim() ?: ""
                if (id.isNotEmpty()) {
                    viewModel.sendIntent(RecipeIntent.LoadRecipe(id))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

//        fun onSearchClick() {
//            viewModel.recipeId.value?.let {
//                viewModel.doNetWorkCall(it)
//            }
//        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
//              Log.d("STATIS",".........$state")
            }
        }
    }
}


