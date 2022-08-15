package com.example.passwordsaver.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.passwordsaver.DatabaseApplication
import com.example.passwordsaver.R
import com.example.passwordsaver.viewmodel.MainScreenViewModel
import com.example.passwordsaver.viewmodel.MainScreenViewModelFactory

class MainScreenFragment: Fragment() {

    val args: MainScreenFragmentArgs by navArgs()
    private lateinit var appName: String
    private val mainScreenViewModel : MainScreenViewModel by activityViewModels{
        MainScreenViewModelFactory(
            (requireContext().applicationContext as DatabaseApplication).repo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appName = args.appName
        mainScreenViewModel.getData(appName)
        mainScreenViewModel.data.observe(viewLifecycleOwner){
            it.let {
                view.findViewById<TextView>(R.id.appNameText).text = it.name

                view.findViewById<EditText>(R.id.userName_editText_change).setText(it.userName)
                view.findViewById<EditText>(R.id.userName_editText_change).isEnabled = false
                view.findViewById<EditText>(R.id.password_editText_change).setText(it.password)
                view.findViewById<EditText>(R.id.password_editText_change).isEnabled = false
                view.findViewById<EditText>(R.id.password_editText_change).isClickable = true
            }
        }
        view.findViewById<Button>(R.id.delete).setOnClickListener {
            deleteDialogBox()
        }
    }


    private fun deleteDialogBox(){
        var alertDialog =  AlertDialog.Builder(ContextThemeWrapper(requireContext(),R.style.AlertDialog))
        alertDialog.setTitle("Confirm Delete...!!")
        alertDialog.setIcon(R.drawable.ic_baseline_delete_forever_24)
        alertDialog.setMessage("Are you sure,you want to delete")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("yes"){_,_ ->
            mainScreenViewModel.data.value?.let { it1 -> mainScreenViewModel.delete(it1) }
            findNavController().navigate(MainScreenFragmentDirections.actionMainScreenFragmentToAppListFragment())
        }
        alertDialog.setNegativeButton("Cancel"){_,_ ->

        }
        alertDialog.show()
    }


}