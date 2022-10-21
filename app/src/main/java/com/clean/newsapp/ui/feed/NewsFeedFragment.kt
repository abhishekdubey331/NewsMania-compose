package com.clean.newsapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.clean.newsapp.data.model.NewsItem
import com.clean.newsapp.databinding.FragmentNewsFeedBinding
import com.clean.newsapp.databinding.RetryLayoutBinding
import com.clean.newsapp.extensions.gone
import com.clean.newsapp.extensions.launchAndRepeatWithViewLifecycle
import com.clean.newsapp.extensions.visible
import com.clean.newsapp.ui.NewsFeedViewModel
import com.clean.newsapp.ui.adapter.NewsListAdapter
import com.clean.newsapp.util.DateTimeUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    @Inject
    lateinit var dateTimeUtil: DateTimeUtil

    private var _binding: FragmentNewsFeedBinding? = null
    private val binding get() = _binding!!

    private val newsFeedViewModel: NewsFeedViewModel by activityViewModels()

    private val newsListAdapter: NewsListAdapter by lazy {
        NewsListAdapter(dateTimeUtil) { navigateToNewsDetailScreen(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        binding.newsFeedRecycler.adapter = newsListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchAndRepeatWithViewLifecycle {
            newsFeedViewModel.newsFeedScreenState.collect { uiState ->
                handleNewsFeed(uiState.newsFeed)
                handleLoadingState(uiState.isLoading)
                handleErrorState(uiState.errorMessage)
            }
        }
    }

    private fun handleErrorState(errorMessage: String?) {
        errorMessage?.let {
            setUpRetryView()
        } ?: run {
            hideRetryView()
        }
    }

    private fun hideRetryView() = binding.retryLayoutView.gone()

    private fun setUpRetryView() {
        val retryLayoutBinding = RetryLayoutBinding.bind(binding.root)
        retryLayoutBinding.retryButton.setOnClickListener {
            newsFeedViewModel.fetchNewsFeed()
        }
        binding.retryLayoutView.visible()
    }

    private fun handleNewsFeed(newsList: List<NewsItem>) {
        if (newsList.isEmpty().not()) {
            newsListAdapter.submitList(newsList)
        }
    }

    private fun navigateToNewsDetailScreen(newsItemId: Int) {
        findNavController().navigate(NewsFeedFragmentDirections.toNewsDetailFragment(newsItemId))
    }

    private fun handleLoadingState(loading: Boolean) = if (loading) {
        binding.progressBar.visible()
    } else {
        binding.progressBar.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}