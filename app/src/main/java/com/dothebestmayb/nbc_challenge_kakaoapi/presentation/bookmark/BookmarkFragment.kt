package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentBookmarkBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.App
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.BookmarkContainer
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.shared.SearchSharedEvent
import com.dothebestmayb.nbc_challenge_kakaoapi.shared.SearchSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {

    private val container by lazy {
        (requireActivity().application as App).appContainer
    }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding: FragmentBookmarkBinding
        get() = _binding!!

    private val bookmarkViewModel: BookmarkViewModel by viewModels {
        container.bookmarkContainer!!.createSearchResultViewModelFactory()
    }

    private val searchSharedViewModel: SearchSharedViewModel by activityViewModels()

    private val adapter: BookmarkAdapter by lazy {
        BookmarkAdapter(
            onRemove = { mediaInfo: MediaInfo ->
                bookmarkViewModel.remove(mediaInfo)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container.bookmarkContainer = BookmarkContainer(container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setRecyclerView()
        setObserve()
    }

    private fun init() {
        bookmarkViewModel.fetch()
    }

    private fun setRecyclerView() = with(binding) {
        rv.adapter = adapter
    }

    private fun setObserve() {
        bookmarkViewModel.bookmarkedDocuments.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { item ->
                adapter.submitList(item)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            searchSharedViewModel.bookMarkEvents.flowWithLifecycle(lifecycle)
                .collectLatest { event ->
                    when (event) {
                        is SearchSharedEvent.UpdateBookmark -> {
                            bookmarkViewModel.update(event)
                        }
                    }
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            bookmarkViewModel.event.flowWithLifecycle(lifecycle)
                .collectLatest { event ->
                    onEvent(event)
                }
        }
    }

    private fun onEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.UpdateBookmark -> searchSharedViewModel.updateEvent(event)
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        container.bookmarkContainer = null

        super.onDestroy()
    }

}