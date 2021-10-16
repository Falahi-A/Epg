package com.dazn.codeassignment.epg.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.dazn.codeassignment.epg.databinding.ActivityBaseBinding

/**
 * A parent for every activity witch wanna use viewBinding
 */
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {


    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private lateinit var baseViewBinding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseViewBinding = ActivityBaseBinding.inflate(layoutInflater)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

    }

    /**
     * @param layoutResID layout of child activity
     */

    override fun setContentView(layoutResID: Int) {
        super.setContentView(baseViewBinding.root)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(layoutInflater.inflate(layoutResID, null)) //add the view to baseContainer
        initView()
    }


    /**
     * @param view layout of child activity
     */
    override fun setContentView(view: View?) {
        super.setContentView(baseViewBinding.root)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(view) //add the view to baseContainer
        initView()
    }

    /**
     * @param view layout of child activity
     */
    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(baseViewBinding.root, params)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(view) //add the view to baseContainer
        initView()
    }

    /**
     * A Base ProgressBar for all of activities
     */
    fun showProgressBar(isVisible: Boolean) {
        baseViewBinding.progressBarBase.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    /**
     * A Base Error View for all of activities
     */
    fun displayCustomErrorView(isDisplayed: Boolean) {
        baseViewBinding.customErrorView.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    fun getCustomErrorView() =
        baseViewBinding.customErrorView

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}