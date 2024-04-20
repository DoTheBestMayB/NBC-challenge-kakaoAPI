package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentBookmarkBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.App
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.adapter.MediaInfoOnClickListener
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.BookmarkContainer
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.MediaInfo
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search.SearchEventHandler

class BookmarkFragment : Fragment(), MediaInfoOnClickListener {

    private val container by lazy {
        (requireActivity().application as App).appContainer
    }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding: FragmentBookmarkBinding
        get() = _binding!!

    private val bookmarkViewModel: BookmarkViewModel by viewModels {
        container.bookmarkContainer!!.createSearchResultViewModelFactory()
    }

    private val adapter: BookmarkAdapter by lazy {
        BookmarkAdapter(this)
    }

    private val bookmarkEventHandler: BookmarkEventHandler by lazy {
        BookmarkEventHandler()
    }

    private val searchEventHandler: SearchEventHandler by lazy {
        SearchEventHandler()
    }

    override fun onBookmarkChanged(mediaInfo: MediaInfo, isBookmarked: Boolean) = Unit

    override fun remove(mediaInfo: MediaInfo) {
        bookmarkViewModel.remove(mediaInfo)
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

        setEventBus()
        init()
        setRecyclerView()
        setObserve()
    }

    private fun setEventBus() {
        bookmarkViewModel.registerEventBus(bookmarkEventHandler, searchEventHandler)

        bookmarkEventHandler.subscribeEvent(viewLifecycleOwner) {
            bookmarkViewModel.update(it)
        }
    }

    private fun init() {
        bookmarkViewModel.fetch()
    }

    private fun setRecyclerView() = with(binding) {
        rv.adapter = adapter
    }

    private fun setObserve() {
        bookmarkViewModel.bookmarkedDocuments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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