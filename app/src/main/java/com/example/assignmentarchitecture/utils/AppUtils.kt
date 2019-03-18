package com.example.assignmentarchitecture.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.assignmentarchitecture.App
import com.example.assignmentarchitecture.R
import com.example.assignmentarchitecture.interfaces.SnackbarCallBack


/**
 * Provides convenience methods and abstractions to some tasks in Android
 *
 *
 * <br></br>
 * <br></br>
 */
class AppUtils {
    private val TAG = AppUtils::class.java.simpleName
    internal var mProgressDialog: ProgressDialog? = null
    internal var mDialog: ProgressDialog? = null
    private val newRole: Int = 0

    /**
     * Checks if the Internet connection is available.
     *
     * @return Returns true if the Internet connection is available. False otherwise.
     */
    // using received context (typically activity) to get SystemService causes memory link as this holds strong reference to that activity.
    // use application level context instead, which is available until the app dies.
    // if network is NOT available networkInfo will be null
    // otherwise check if we are connected
    val isInternetAvailable: Boolean
        get() {
            val connectivityManager =
                App.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            var networkInfo: NetworkInfo? = null
            if (connectivityManager != null) {
                networkInfo = connectivityManager.activeNetworkInfo
            }
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else false

        }

    /**
     * Shows a long time duration toast message.
     *
     * @param msg Message to be show in the toast.
     * @return Toast object just shown
     */
    fun showToast(msg: CharSequence) {
        showToast(App.applicationContext(), msg, Toast.LENGTH_SHORT)
    }

    /**
     * Shows the message passed in the parameter in the Toast.
     *
     * @param msg      Message to be show in the toast.
     * @param duration Duration in milliseconds for which the toast should be shown
     * @return Toast object just shown
     */
    fun showToast(ctx: Context, msg: CharSequence, duration: Int) {
        val toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT)
        toast.duration = duration
        toast.show()

    }


    /**
     * used for showing the snackbar
     *
     * @param mSnackView--> root view
     * @param message-->    message to be shown
     * @return
     */
    fun showSnackBar(mSnackView: View, message: String): Snackbar? {
        if (mSnackView.parent != null) {
            val snackbar = Snackbar.make(mSnackView, message, Snackbar.LENGTH_LONG)
            // Changing text color
            val sbView = snackbar.view
            sbView.setBackgroundColor(ContextCompat.getColor(App.applicationContext(), R.color.colorPrimary))
            val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
            val typeface = ResourcesCompat.getFont(App.applicationContext(), R.font.work_sans_regular)

            textView.typeface = typeface

            textView.setTextColor(Color.WHITE)

            snackbar.show()
            return snackbar
        }

        return null
    }

    fun showInfiniteSnackBar(mSnackView: View, message: String): Snackbar {
        val snackbar = Snackbar.make(mSnackView, message, Snackbar.LENGTH_INDEFINITE)

        snackbar.setActionTextColor(Color.WHITE)
        // Changing text color
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(mSnackView.context, R.color.colorAccent))
        sbView.setBackgroundColor(ContextCompat.getColor(App.applicationContext(), R.color.colorPrimary))
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView

        val typeface = ResourcesCompat.getFont(App.applicationContext(), R.font.work_sans_regular)

        textView.typeface = typeface
        textView.setTextColor(Color.WHITE)
        snackbar.show()

        return snackbar
    }


    /**
     * used for showing the snackbar with an option to retry the action
     *
     * @param mSnackView-->       root view
     * @param message-->          message to be shown
     * @param snackbarCallBack--> call back on the retry option clicked
     * @return
     */
    fun showSnackBarWithAction(mSnackView: View, message: String, snackbarCallBack: SnackbarCallBack?): Snackbar {
        val snackbar = Snackbar.make(mSnackView, message, Snackbar.LENGTH_INDEFINITE).setAction(
            App.applicationContext().resources.getString(R.string.retry)
        ) {
            //do what ever you want
            snackbarCallBack?.onSnackBarRetry()
        }
        snackbar.setActionTextColor(Color.WHITE)
        // Changing text color
        val sbView = snackbar.view
        sbView.setBackgroundColor(ContextCompat.getColor(mSnackView.context, R.color.colorAccent))
        sbView.setBackgroundColor(ContextCompat.getColor(App.applicationContext(), R.color.colorPrimary))
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView

        val typeface = ResourcesCompat.getFont(App.applicationContext(), R.font.work_sans_regular)

        textView.typeface = typeface
        textView.setTextColor(Color.WHITE)
        snackbar.show()
        return snackbar
    }

    companion object {

        private var instance: AppUtils? = null

        fun getInstance(): AppUtils {
            if (instance == null) {
                instance = AppUtils()
            }
            return instance as AppUtils
        }
    }


}