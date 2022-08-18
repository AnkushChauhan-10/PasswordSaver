package com.example.passwordsaver.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.passwordsaver.R
import com.itsxtt.patternlock.PatternLockView
import java.util.ArrayList

class PatternScreenFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pattern_screen,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patterCheck(view)
        onCheck("0")
    }

    private fun patterCheck(view: View){
        var paternScreen = view.findViewById<PatternLockView>(R.id.patternView)
        paternScreen.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onStarted() {
                super.onStarted()
            }
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                onCheck(ids.toString())
                return true
            }
        })
    }

    private fun onCheck(enterPattern : String) {
        var sharedPreferences = requireContext().getSharedPreferences("PREFS", 0)
        var password = sharedPreferences.getString("password", "0")
        if (password.equals("0") || password.equals(enterPattern)) {
            findNavController().navigate(PatternScreenFragmentDirections.actionPatternScreenFragmentToAppListFragment())
        }
    }

}