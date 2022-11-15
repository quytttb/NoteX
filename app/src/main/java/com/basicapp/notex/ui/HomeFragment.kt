package com.basicapp.notex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.basicapp.notex.R
import com.basicapp.notex.databinding.FragmentHomeBinding
import com.basicapp.notex.viewmodel.NoteViewModel


/**
 * [HomeFragment] là màn hình chính, hiện danh sách ghi chú
 */
class HomeFragment : Fragment() {

    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(activity?.application)
        )[NoteViewModel::class.java]
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter() {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.notesRV.setHasFixedSize(true)
        binding.notesRV.layoutManager = LinearLayoutManager(this.context)
        binding.notesRV.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allNotes.observe(this.viewLifecycleOwner) {
                adapter.submitList(it)
        }

        binding.idFAB.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(
                getString(R.string.add_fragment_title)
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
