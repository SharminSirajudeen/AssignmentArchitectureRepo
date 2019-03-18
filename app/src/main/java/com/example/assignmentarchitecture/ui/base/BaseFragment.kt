package com.example.assignmentarchitecture.ui.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignmentarchitecture.interfaces.SnackbarCallBack
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment<MyDataBinding : ViewDataBinding, MyViewModel : BaseViewModels<*>> : DaggerFragment(),
    SnackbarCallBack {

    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: MyDataBinding? = null
        private set
    private var mViewModel: MyViewModel? = null


    abstract val bindingVariable: Int


    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: MyViewModel

    override fun onSnackBarRetry() {
        if (isAdded)
            viewModel.retryAPICalling()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }


    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
