package com.clean.newsapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.clean.newsapp.data.model.NewsItem
import com.clean.newsapp.databinding.FragmentNewsDetailBinding
import com.clean.newsapp.extensions.gone
import com.clean.newsapp.extensions.launchAndRepeatWithViewLifecycle
import com.clean.newsapp.extensions.loadImage
import com.clean.newsapp.extensions.visible
import com.clean.newsapp.ui.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private val args: NewsDetailFragmentArgs by navArgs()

    private val newsFeedViewModel: NewsFeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsItemId = args.newsItemId
        newsFeedViewModel.getNewsItemById(newsItemId)
        launchAndRepeatWithViewLifecycle {
            newsFeedViewModel.newsDetailScreenState.collect { uiState ->
                handleNewsItem(uiState.newsItem)
                handleLoadingState(uiState.isLoading)
            }
        }
    }

    private fun handleNewsItem(newsItem: NewsItem?) {
        newsItem ?: return
        binding.newsTitleTextview.text = newsItem.title
        binding.newsDescTextview.text = newsItem.description
        binding.newsMediaImv.loadImage(newsItem.newsMediaImageUrl)
        binding.moreInfoView.setOnClickListener {
            openMoreInfo(newsItem.moreInfoUrl)
        }
    }

    private fun handleLoadingState(loading: Boolean) = if (loading) {
        binding.progressBar.visible()
    } else {
        binding.progressBar.gone()
    }

    private fun openMoreInfo(moreInfoUrl: String) {
        val defaultBrowser = Intent.makeMainSelectorActivity(
            Intent.ACTION_MAIN,
            Intent.CATEGORY_APP_BROWSER
        )
        defaultBrowser.data = Uri.parse(moreInfoUrl)
        startActivity(defaultBrowser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}