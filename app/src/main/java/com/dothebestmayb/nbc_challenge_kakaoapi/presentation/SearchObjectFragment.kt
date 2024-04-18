package com.dothebestmayb.nbc_challenge_kakaoapi.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dothebestmayb.nbc_challenge_kakaoapi.databinding.FragmentSearchObjectBinding
import com.dothebestmayb.nbc_challenge_kakaoapi.presentation.model.TabType

class SearchObjectFragment : Fragment() {

    private var _binding: FragmentSearchObjectBinding? = null
    private val binding: FragmentSearchObjectBinding
        get() = _binding!!

    private lateinit var type: TabType

    private val searchResultViewModel: SearchResultViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchObjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!checkType()) {
            return
        }
        fetchData()
    }

    private fun checkType(): Boolean {
        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_TYPE, TabType::class.java)
        } else {
            requireArguments().getParcelable(ARG_TYPE)
        } ?: run {
            // Fragmnet 실행에 필요한 데이터 전달이 누락되었습니다.
            return false
        }

        if (type == TabType.BOOKMARK) {
            binding.etQuery.visibility = View.GONE
        }

        return true
    }

    private fun fetchData() {
        when (type) {
            TabType.SEARCH -> searchResultViewModel.fetchFromServer()
            TabType.BOOKMARK -> searchResultViewModel.fetchFromLocal()
        }
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    companion object {
        const val ARG_TYPE = "TAB_TYPE"
    }
}