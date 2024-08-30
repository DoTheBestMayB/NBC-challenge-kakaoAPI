package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding: FragmentBookmarkBinding
        get() = _binding!!

    private val viewModel: BookmarkViewModel by viewModels()

    private val bookmarkAdapter = BookmarkAdapter { item ->
        viewModel.onBookmarkClick(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserve()
    }

    private fun setRecyclerView() {
        binding.rvBookmarked.adapter = bookmarkAdapter
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect {
                    bookmarkAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvBookmarked.adapter = null
        _binding = null

        super.onDestroyView()
    }
}