package com.dothebestmayb.nbc_challenge_kakaoapi.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentSearchObjectBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.App
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.di.SearchContainer

class SearchFragment : Fragment() {

    private val container by lazy {
        (requireActivity().application as App).appContainer
    }

    private var _binding: FragmentSearchObjectBinding? = null
    private val binding: FragmentSearchObjectBinding
        get() = _binding!!

    private val adapter: SearchAdapter by lazy {
        SearchAdapter()
    }

    private val searchViewModel: SearchViewModel by activityViewModels {
        container.searchContainer!!.createSearchResultViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        container.searchContainer = SearchContainer(container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchObjectBinding.inflate(inflater, container, false)
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
    }

    private fun setObserve() {
        searchViewModel.query.observe(viewLifecycleOwner) {
            searchViewModel.fetchDataFromServer()
        }
        searchViewModel.images.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        searchViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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