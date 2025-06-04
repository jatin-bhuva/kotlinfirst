package com.example.kotliin1.ui.breedlist
import com.example.kotliin1.viewModel.BreedViewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kotliin1.data.api.DogApiService
import com.example.kotliin1.data.api.RetrofitHelper
import com.example.kotliin1.data.repository.DogRepository
import com.example.kotliin1.databinding.FragmentBreedListBinding
import com.example.kotliin1.viewModel.BreedListAdapter
import com.example.kotliin1.viewModel.ViewModelFactory


class BreedListFragment : Fragment() {

    private var _binding: FragmentBreedListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BreedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBreedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breedService = RetrofitHelper.getInstance().create(DogApiService::class.java)

        val repository = DogRepository(breedService)

        viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(BreedViewModel::class.java)

        val adapter = BreedListAdapter(emptyList()) { breed ->

//            val action = BreedListFragmentDirections
//                .actionFragmentTaskListToFragmentBreedDetail(breed)
//            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.breeds.observe(viewLifecycleOwner, Observer {
           adapter.updateList(it)
        })
        viewModel.breedsLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                updateNoDataMessage(adapter.itemCount, viewModel.breedsLoading.value ?: false)
                return true
            }
        })

    }
    private fun updateNoDataMessage(itemCount: Int, isLoading: Boolean) {
        binding.tvNoData.visibility = if (!isLoading && itemCount == 0) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
