package com.dothebestmayb.nbc_challenge_kakaoapi.presenter.ui.search

import android.graphics.Rect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.dothebestmayb.nbc_challenge_kakaoapi.R
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentSearchBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presenter.network.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val searchAdapter = SearchAdapter { item ->
        viewModel.onBookmarkClick(item)
    }

    private var networkStatusHandleJob: Job? = null

    private val itemDecoration = object : ItemDecoration() {

        private val marginSize by lazy {
            binding.root.context.resources.getDimension(R.dimen.view_holder12).toInt()
        }

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)

            // 마지막 Item은 margin을 설정하지 않음
            // Item을 recyclerView에서 삭제할 수 있다면 position이 -1일 때도 지워지기 전과 동일하도록 처리해야 함
            // 관련 내용 : https://dodobest.tistory.com/115
            if (position != searchAdapter.itemCount - 1) {
                outRect.set(0, 0, 0, marginSize)
            }
        }
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
        setObserve()
    }

    private fun setRecyclerView() {
        binding.rvSearchResult.adapter = searchAdapter
        binding.rvSearchResult.addItemDecoration(itemDecoration)
    }

    private fun setListener() {
        binding.textFieldInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                hideInput()
                binding.vDummyForRemoveFocus.requestFocus()

                viewModel.onSearch(binding.textField.editText?.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.textFieldInput.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideInput()
                binding.vDummyForRemoveFocus.requestFocus()

                viewModel.onSearch(binding.textField.editText?.text.toString())
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

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.isLoading) {
                        binding.cpiLoading.show()
                        return@collect
                    }
                    binding.cpiLoading.hide()

                    when (state.networkStatus) {
                        NetworkStatus.AVAILABLE -> hideNetworkStatusBar()
                        NetworkStatus.LOST -> showNetworkStatusBar()
                    }
                    searchAdapter.submitList(state.searchResult)
                }
            }
        }
    }

    private fun hideNetworkStatusBar() {
        binding.tvNetworkStatus.text = getString(R.string.internet_is_connected)
        binding.tvNetworkStatus.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.green
            )
        )

        networkStatusHandleJob?.cancel()

        networkStatusHandleJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(2000L)
            binding.tvNetworkStatus.visibility = View.GONE
        }
    }

    private fun showNetworkStatusBar() {
        binding.tvNetworkStatus.text = getString(R.string.internet_is_not_connected)
        binding.tvNetworkStatus.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )

        networkStatusHandleJob?.cancel()
        networkStatusHandleJob = null

        binding.tvNetworkStatus.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        binding.rvSearchResult.adapter = null
        _binding = null

        super.onDestroyView()
    }
}