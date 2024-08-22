package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val searchAdapter = SearchAdapter { item ->
        viewModel.onBookmarkClick(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListener()
    }

    private fun setRecyclerView() {
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun setListener() {
        binding.textFieldInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                hideInput()
                binding.vDummyForRemoveFocus.requestFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.textFieldInput.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideInput()
                binding.vDummyForRemoveFocus.requestFocus()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.vDummyForRemoveFocus.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideInput()
            }
        }
    }

    private fun hideInput() {
        ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            ?.hideSoftInputFromWindow(
                binding.root.windowToken,
                0
            )
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}