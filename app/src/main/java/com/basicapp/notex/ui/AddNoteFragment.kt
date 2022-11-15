package com.basicapp.notex.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.basicapp.notex.databinding.FragmentAddNoteBinding
import com.basicapp.notex.model.Note
import com.basicapp.notex.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * [AddNoteFragment] để soạn thảo ghi chú
 */
class AddNoteFragment : Fragment() {

    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(activity?.application)
        )[NoteViewModel::class.java]
    }

    private val navigationArgs: AddNoteFragmentArgs by navArgs()
    lateinit var note: Note

    private var _binding: FragmentAddNoteBinding? = null

    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd MMMM, yyyy - HH:mm")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root

    }


    /**
     * Trả về true nếu đã nhập dữ liệu
     */
    private fun isDataValid(): Boolean {
        return viewModel.isDataValid(
            binding.inputTitleEditText.text.toString(),
            binding.inputContentEditText.text.toString(),
        )
    }


    /**
     * Binds views with the passed in [note] information.
     */
    private fun bind(note: Note) {
        binding.apply {
            inputTitleEditText.setText(note.noteTitle, TextView.BufferType.SPANNABLE)
            inputContentEditText.setText(note.noteContent, TextView.BufferType.SPANNABLE)
            btnSave.setOnClickListener { updateItem() }
        }
    }

    /**
     * Inserts the new Note into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isDataValid()) {
            viewModel.addNote(
                binding.inputTitleEditText.text.toString(),
                binding.inputContentEditText.text.toString(),
                sdf.format(Date())
            )
            val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Updates an existing Note in the database and navigates up to list fragment.
     */
    private fun updateItem() {
        if (isDataValid()) {
            viewModel.updateNote(
                this.navigationArgs.id,
                this.binding.inputTitleEditText.text.toString(),
                this.binding.inputContentEditText.text.toString(),
                sdf.format(Date())
            )
            val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
                note = selectedNote
                bind(note)
            }
        } else {
            binding.btnSave.setOnClickListener {
                addNewItem()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}