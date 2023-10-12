package com.example.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

abstract class BaseFragment<T : ViewBinding>(
    private val layoutInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    internal lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firebaseAnalytics = Firebase.analytics

        _binding = layoutInflater.invoke(inflater, container, false)
        return _binding!!.root
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to this.javaClass.canonicalName)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
