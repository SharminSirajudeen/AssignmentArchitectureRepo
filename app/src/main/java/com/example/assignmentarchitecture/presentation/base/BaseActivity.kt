package  com.example.assignmentarchitecture.presentation.base


import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.assignmentarchitecture.LocaleHelper
import com.example.assignmentarchitecture.R
import com.example.assignmentarchitecture.interfaces.SnackbarCallBack
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.lang.Exception
import java.util.*

abstract class BaseActivity<MyDataBinding : ViewDataBinding, MyViewModel : BaseViewModels<*>> :
    DaggerAppCompatActivity(), BaseFragment.Callback, SnackbarCallBack {

    internal var params = WeakHashMap<String, String>()
    private val mProgressDialog: ProgressDialog? = null
    var viewDataBinding: MyDataBinding? = null
        private set
    private var mViewModel: MyViewModel? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: MyViewModel?

    fun setmViewModel(viewModel: MyViewModel) {
        this.mViewModel = viewModel
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onSnackBarRetry() {
        if (!isFinishing)
            if (viewModel != null) {
                viewModel!!.retryAPICalling()
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        try {
            performDataBinding()
        } catch (rme: NullPointerException) {
            rme.printStackTrace()
        }
    }

    /**
     * hides keyboard onClick anywhere besides edit text
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken,
                    0
                )
        }
        return super.dispatchTouchEvent(ev)
    }


    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    fun addFragment(@IdRes resourceID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        transaction.add(resourceID, fragment, fragment.javaClass.simpleName)
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }


    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    fun addFragmentWithoutAnimation(@IdRes resourceID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.add(resourceID, fragment, fragment.javaClass.simpleName)
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)
        
        transaction.commitAllowingStateLoss()
    }


    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    fun addFragmentWithoutExitAnimation(@IdRes resourceID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, 0, R.anim.enter_from_right, 0)

        transaction.add(resourceID, fragment, fragment.javaClass.simpleName)
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }


    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    fun replaceFragment(@IdRes resourceID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        transaction.replace(resourceID, fragment, fragment.javaClass.simpleName)
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    /**
     * @param resourceID-->       layout id
     * @param fragment-->fragment object
     * @param addToBackStack-->   flag to onAddItem to back stack
     */
    fun replaceFragmentWithoutAnimation(@IdRes resourceID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.replace(resourceID, fragment, fragment.javaClass.simpleName)
        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.cancel()
        }
    }

    /*
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }
*/

    fun performDependencyInjection() {
        try {
            AndroidInjection.inject(this)
        } catch (ile: Exception) {
            ile.printStackTrace()
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    /*
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }
*/

    @Throws(NullPointerException::class)
    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        if (viewDataBinding == null)
            return
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }


    /**
     * used to get the current fragment in the Acitivity's Fragment container
     *
     * @param id--> id of the container
     * @return
     */
    fun getCurrentFragment(id: Int): Fragment? {
        return supportFragmentManager.findFragmentById(id)
    }

}

