package com.example.kotliin1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotliin1.databinding.FragmentAddEditTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAddEditTask.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAddEditTask : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAddEditTaskBinding
    private val viewModel: TaskViewModel by viewModels()
    private val navArgs by navArgs<FragmentAddEditTaskArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_task, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeVariables(view)
        val task = navArgs.task
        if (task != null) {
            viewModel.loadTask(task)  // You'll define this in ViewModel
            binding.buttonSave.text = "Update Task"
            setSpinnerSelection(task.repeatInterval)

        }
    }

    private fun initializeVariables(view: View){
        viewModel.navigateBack.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                findNavController().popBackStack()
                viewModel.doneNavigating()
            }
        }

        setupSpinner()

        binding.buttonSave.setOnClickListener {
            viewModel.saveTask()
        }

       binding.buttonSelectDateTime.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .build()

            datePicker.show(parentFragmentManager, "datePicker")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val date = Date(selection)
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val formattedDate = formatter.format(date)
                binding.buttonSelectDateTime.text = formattedDate
                viewModel.dueDate.value = formattedDate
            }
        }
    }
    private fun setSpinnerSelection(value: String) {
        val adapter = binding.spinnerRepeatInterval.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i) == value) {
                binding.spinnerRepeatInterval.setSelection(i)
                break
            }
        }
    }
    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.repeat_intervals,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRepeatInterval.adapter = adapter

        binding.spinnerRepeatInterval.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent.getItemAtPosition(position).toString()
                viewModel.repeatInterval.value = selected
                Log.d("RepeatInterval", "Selected: $selected")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_add_edit_task.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAddEditTask().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}