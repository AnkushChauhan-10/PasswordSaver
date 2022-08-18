package com.example.passwordsaver.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.passwordsaver.R
import com.itsxtt.patternlock.PatternLockView
import java.util.ArrayList

class SetPatternFragment: Fragment(){

    var currentPattern :String?= null
    var confirmPattern : String ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_set_pattern,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var saveButton: Button = view.findViewById<Button>(R.id.save)
        var sharedPreferences = requireContext().getSharedPreferences("PREFS", AppCompatActivity.MODE_PRIVATE)
        var paternScreen = view.findViewById<PatternLockView>(R.id.patternView)
        var stateText = view.findViewById<TextView>(R.id.stateText)
        paternScreen.setOnPatternListener(object : PatternLockView.OnPatternListener {
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                currentPattern = ids.toString()

                if(confirmPattern == null){
                    confirmPattern = currentPattern
                    stateText.text = "Redraw Pattern"
                }else if(confirmPattern.equals(currentPattern)){
                    stateText.text = "Pattern Recorded"
                    saveButton.isEnabled = true
                    saveButton.alpha = 1.0f
                }else{
                    toast()
                }
                return true
            }
        })

        saveButton.setOnClickListener {
            sharedPreferences.edit().putString("password",confirmPattern).commit()
            goBack()
        }

    }
    fun goBack(){
        findNavController().navigate(SetPatternFragmentDirections.actionSetPatternFragmentToAppListFragment())
    }

    fun toast(){
        Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
    }
}