package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentSearchBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.App
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.SearchContainer
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.shared.SearchSharedEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.shared.SearchSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val container by lazy {
        (requireActivity().application as App).appContainer
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val adapter: SearchAdapter by lazy {
        SearchAdapter(
            onBookmarkChanged = { mediaInfo: MediaInfo, isBookmarked: Boolean ->
                searchViewModel.updateBookmarkState(mediaInfo, isBookmarked, false)
            }
        )
    }

    private val searchViewModel: SearchViewModel by viewModels {
        container.searchContainer!!.createSearchResultViewModelFactory()
    }

    private val searchSharedViewModel: SearchSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container.searchContainer = SearchContainer(container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListener()
        setObserve()
    }

    private fun setRecyclerView() = with(binding) {
        rv.adapter = adapter
    }

    private fun setListener() = with(binding) {
        etQuery.doOnTextChanged { text, _, _, _ ->
            searchViewModel.updateQuery(text.toString())
        }
        rv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            private val layoutManager = rv.layoutManager as LinearLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == RecyclerView.NO_POSITION) {
                    return
                }
                if (lastVisiblePosition >= adapter.itemCount - 10) {
                    searchViewModel.searchNext()
                }
            }
        })
    }

    private fun setObserve() {
        searchViewModel.query.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { query ->
                searchViewModel.fetchDataFromServer(query)
            }
        }
        searchViewModel.results.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { mediaInfo ->
                adapter.submitList(mediaInfo)
            }
        }
        searchViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                searchViewModel.event.flowWithLifecycle(lifecycle)
                    .collectLatest { event ->
                        onEvent(event)
                    }
            }

            launch {
                searchSharedViewModel.searchMarkEvents.flowWithLifecycle(lifecycle)
                    .collectLatest { event ->
                        when (event) {
                            is SearchSharedEvent.UpdateBookmark -> {
                                searchViewModel.updateBookmarkState(
                                    event.mediaInfo,
                                    event.bookmarked,
                                    true
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateBookmark -> searchSharedViewModel.updateEvent(event)
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        container.searchContainer = null

        super.onDestroy()
    }
}