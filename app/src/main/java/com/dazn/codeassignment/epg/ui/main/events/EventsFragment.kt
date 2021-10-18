package com.dazn.codeassignment.epg.ui.main.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dazn.codeassignment.epg.R
import com.dazn.codeassignment.epg.databinding.FragmentEventsBinding
import com.dazn.codeassignment.epg.ui.base.BaseBindingFragment
import com.dazn.codeassignment.epg.ui.main.MainActivity
import com.dazn.codeassignment.epg.ui.player.PlayerActivity
import com.dazn.codeassignment.epg.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : BaseBindingFragment<FragmentEventsBinding>() {

    private val viewModel: EventsViewModel by viewModels()

    private val adapter: EventsListAdapter by lazy {
        EventsListAdapter { videoUrl ->
            Intent(activity, PlayerActivity::class.java).apply {
                putExtra(Constants.VIDEO_URL_KEY, videoUrl)
                startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEventsBinding =
        { layoutInflater, viewGroup, attachToParent ->
            FragmentEventsBinding.inflate(layoutInflater, viewGroup, attachToParent)
        }

    override fun initView() {

        getEventsList()

        binding.recyclerEvents.adapter = adapter

        viewModel.eventsState.observe(viewLifecycleOwner, { state ->

            when {
                state.loading -> {
                    (activity as MainActivity).apply {
                        showProgressBar(true)
                        displayCustomErrorView(false)
                    }
                }
                state.error != "" -> {
                    (activity as MainActivity).apply {
                        showProgressBar(false)
                        displayCustomErrorView(true)
                        getCustomErrorView().setError(state.error)
                        getCustomErrorView().setReloadListener {
                            getEventsList()
                        }
                    }

                }
                else -> {
                    (activity as MainActivity).apply {
                        showProgressBar(false)
                        displayCustomErrorView(false)
                    }
                    adapter.submitList(state.eventsList)

                }
            }


        })

    }

    private fun getEventsList() =
        viewModel.getEvents()


}