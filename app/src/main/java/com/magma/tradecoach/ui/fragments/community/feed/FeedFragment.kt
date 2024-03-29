package com.magma.tradecoach.ui.fragments.community.feed

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magma.tradecoach.R
import com.magma.tradecoach.databinding.FragmentFeedBinding
import com.magma.tradecoach.interfaces.BlogPostClickedInterface
import com.magma.tradecoach.model.BlogPostModel
import com.magma.tradecoach.utilities.Utils
import com.magma.tradecoach.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FeedFragment:Fragment(),BlogPostClickedInterface {
    private lateinit var adapter :FeedAdapter
    private lateinit var binding: FragmentFeedBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater)

        init()
        return binding.root
    }

    private fun init() {
        adapter = FeedAdapter(arrayListOf(),this)
        Utils.setRecycler(binding.feedRV,adapter)

        viewModel.getUserData()
        viewModel.getPosts()
        viewModel.blogPostsLiveData.observe(this) {
            adapter.setData(it)
        }

        listeners()
    }
    private fun listeners(){
        binding.createPostButton.setOnClickListener {
            viewModel.currentUser.observe(this){
                if (it == null) return@observe

                if (Utils.isPremiumUser(it)){
                    showOverlay(true)
                } else {
                    Toast.makeText(context, "Only Premium Users Can Create Posts", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.publishButton.setOnClickListener {
            viewModel.currentUser.value?.emailAddress?.let { emailAddress ->
                viewModel.createBlogPost(
                    Utils.getETText(binding.titleTV), Utils.getETText(binding.contentTV),
                    emailAddress
                ).let {
                    Utils.animateViewToBottom(binding.createPostView)
                    hideOverlay()
                    Toast.makeText(activity, "Post Created Successfully", Toast.LENGTH_SHORT).show()
                    viewModel.getPosts()
                }
            }
        }

        binding.icClose.setOnClickListener {
            hideOverlay()
        }
    }

    private fun showOverlay(toPost: Boolean) {
        val darkOverlay = activity?.findViewById<View>(R.id.darkOverlay)
        Utils.animateViewFromBottom(binding.createPostView)

        if (toPost) {
            binding.titleTV.setText("")
            binding.contentTV.setText("")
        }

        darkOverlay?.let { Utils.animateViewAppear(it) }
    }

    private fun hideOverlay(){
        val darkOverlay = activity?.findViewById<View>(R.id.darkOverlay)
        Utils.animateViewToBottom(binding.createPostView)
        darkOverlay?.isVisible = false

        binding.publishButton.isVisible = true
        binding.titleTV.isEnabled = true
        binding.contentTV.isEnabled = true
        binding.titleTV.setText("")
        binding.contentTV.setText("")
    }

    override fun postClicked(post: BlogPostModel) {
        showOverlay(false)

        binding.publishButton.isVisible = false
        binding.titleTV.setText(post.postTitle)
        binding.contentTV.setText(post.postContent)
        binding.titleTV.isEnabled = false
        binding.contentTV.isEnabled = false
    }
}