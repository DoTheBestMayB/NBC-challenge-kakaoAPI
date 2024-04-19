package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentBookmarkBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.App
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.BookmarkContainer

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

//    private val adapter: MediaInfoAdapter by lazy {
//    }

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

    private fun setRecyclerView() = with(binding){
//        rv.adapter = adapter
    }

    private fun setObserve() {
        bookmarkViewModel.bookmarkedImageDocuments.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
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