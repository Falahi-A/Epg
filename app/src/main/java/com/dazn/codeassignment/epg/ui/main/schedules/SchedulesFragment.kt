package com.dazn.codeassignment.epg.ui.main.schedules


import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dazn.codeassignment.epg.databinding.FragmentSchedulesBinding
import com.dazn.codeassignment.epg.ui.base.BaseBindingFragment
import com.dazn.codeassignment.epg.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "SchedulesFragment"

@AndroidEntryPoint
class SchedulesFragment : BaseBindingFragment<FragmentSchedulesBinding>() {

    private val viewModel: SchedulesViewModel by viewModels()

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private val adapter: SchedulesListAdapter by lazy {
        SchedulesListAdapter()
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSchedulesBinding =
        { layoutInflater, viewGroup, attachToParent ->
            FragmentSchedulesBinding.inflate(layoutInflater, viewGroup, attachToParent)
        }

    override fun initView() {

        getSchedulesList()

        binding.recyclerSchedules.adapter = adapter

        // prevent recycler from flickering while refreshing data
        binding.recyclerSchedules.itemAnimator?.let { animator ->
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }

        viewModel.schedules.observe(viewLifecycleOwner, { state ->

            when {
                state.loading -> {
                    (activity as MainActivity).apply {
                        showProgressBar(true)
                        displayCustomErrorView(false)
                    }
                }
                state.error.isNotEmpty() -> {
                    (activity as MainActivity).apply {
                        showProgressBar(false)
                        displayCustomErrorView(true)
                        getCustomErrorView().setError(state.error)
                        getCustomErrorView().setReloadListener {
                            getSchedulesList()
                        }
                    }
                }
                else -> {
                    (activity as MainActivity).apply {
                        showProgressBar(false)
                        displayCustomErrorView(false)
                    }
                    adapter.submitList(state.schedulesList)
                }

            }

        })


        refreshList(30000)
    }

    /**
     * refreshing schedules list after every 30 seconds
     */
    private fun refreshList(milliSeconds: Long) {
        runnable = Runnable {
            getSchedulesList()
            refreshList(milliSeconds)
        }
        handler = Handler(Looper.getMainLooper()).apply {
            postDelayed(runnable, milliSeconds)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    fun getSchedulesList() {
        viewModel.getSchedules()
    }

}