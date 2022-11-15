package com.basicapp.notex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.basicapp.notex.R
import com.basicapp.notex.databinding.FragmentDetailBinding
import com.basicapp.notex.model.Note
import com.basicapp.notex.viewmodel.NoteViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(activity?.application)
        )[NoteViewModel::class.java]
    }

    private val navigationArgs: DetailFragmentArgs by navArgs()
    lateinit var note: Note
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }


    /**
     * Binds views with the passed in note data.
     */
    private fun bind(note: Note) {
        binding.apply {
            txtTitle.text = note.noteTitle
            txtContent.text = note.noteContent
            //deletenote.setOnClickListener { showConfirmationDialog() }
            editNote.setOnClickListener { editNote() }

        }
    }


    /**
     * Navigate to the Edit item screen.
     */
    private fun editNote() {
        val action = DetailFragmentDirections.actionDetailFragmentToAddNoteFragment(
            getString(R.string.edit_fragment_title),
            note.id
        )
        this.findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
            note = selectedNote
            bind(note)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}