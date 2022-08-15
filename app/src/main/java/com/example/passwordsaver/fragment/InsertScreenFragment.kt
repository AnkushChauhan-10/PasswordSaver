package com.example.passwordsaver.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.passwordsaver.DatabaseApplication
import com.example.passwordsaver.R
import com.example.passwordsaver.databinding.FragmentInsertScreenBinding
import com.example.passwordsaver.viewmodel.SaverViewModel
import com.example.passwordsaver.viewmodel.SaverViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


class InsertScreenFragment: Fragment() {

    private lateinit var binding: FragmentInsertScreenBinding

    private val saverViewModel: SaverViewModel by activityViewModels{
        SaverViewModelFactory(
            (requireContext().applicationContext as DatabaseApplication).repo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_screen,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSave(view)
        onCancel(view)

    }

    private fun onCancel(view: View) {
        view.findViewById<Button>(R.id.cancel).setOnClickListener {
            findNavController().navigate(InsertScreenFragmentDirections.actionInsertScreenFragmentToAppListFragment())
        }
    }

    private fun onSave(view: View) {
        view.findViewById<MaterialButton>(R.id.save_button).setOnClickListener{
            addData(view.findViewById<TextInputEditText>(R.id.appName_editText).text.toString(),
                view.findViewById<TextInputEditText>(R.id.userName_editText).text.toString(),
                view.findViewById<TextInputEditText>(R.id.password_editText).text.toString())
        }
    }

    fun addData(app:String,user:String, password:String){

        if(app.trim().isNotEmpty() && user.trim().isNotEmpty() && password.trim().isNotEmpty()) {
            saverViewModel.addNewItem(app, user, password,getCurrentDateTime())
            findNavController().navigate(InsertScreenFragmentDirections.actionInsertScreenFragmentToAppListFragment())
        }
    }

    fun getCurrentDateTime(): String{
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.LLL.yyyy HH:mmm:ss aaa")
        val dateTime = simpleDateFormat.format(calendar.time).toString()
        return dateTime
    }
}