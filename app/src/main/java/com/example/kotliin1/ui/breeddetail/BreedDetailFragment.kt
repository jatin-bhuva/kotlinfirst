package com.example.kotliin1.ui.breeddetail
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kotliin1.databinding.FragmentBreedDetailBinding
class BreedDetailFragment : Fragment() {

    private var _binding: FragmentBreedDetailBinding? = null
    private val binding get() = _binding!!

//    private val args: BreedDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val breed = args.breed

//        binding.tvName.text = breed.name
//        binding.tvTemperament.text = breed.temperament ?: "No info"
//        binding.tvLifeSpan.text = breed.life_span ?: "Unknown"
//        binding.tvSize.text = "Size info not available"
//
//        Glide.with(requireContext())
//            .load(breed.image?.url)
//            .into(binding.ivBreedImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

