package com.example.passwordsaver.fragment

import android.app.Application
import android.icu.util.LocaleData
import android.os.Bundle
import android.view.*
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ButtonBarLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordsaver.DatabaseApplication
import com.example.passwordsaver.MainActivity
import com.example.passwordsaver.R
import com.example.passwordsaver.adapter.ListAdapter
import com.example.passwordsaver.database.SaverEntity
import com.example.passwordsaver.databinding.FragmentAppListBinding
import com.example.passwordsaver.viewmodel.AllListViewModel
import com.example.passwordsaver.viewmodel.AllListViewModelFactory
import com.example.passwordsaver.viewmodel.SaverViewModel
import com.example.passwordsaver.viewmodel.SaverViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class AppListFragment : Fragment() {


   // private val binding get() = _binding!!

   private lateinit var adapter: ListAdapter
   private var searchList = ArrayList<SaverEntity>()

    private val viewModel: AllListViewModel by activityViewModels {
        AllListViewModelFactory(
            (requireContext().applicationContext as DatabaseApplication).repo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_app_list,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycleView(view)
        setAdapterClickListener(view)
        setViewModelObserver()
        search(view)

        view.findViewById<FloatingActionButton>(R.id.add_btn).setOnClickListener {
            navigateToInsertScreen()
        }
    }

//------------------------------------------------------------------------------------------------------------------------------

    private fun setAdapterClickListener(view: View) {
        adapter.setOnclick(object : ListAdapter.ListAdapterListener{
            override fun onClickListItem(saverEntity: SaverEntity) {
                findNavController().navigate(AppListFragmentDirections.actionAppListFragmentToMainScreenFragment(saverEntity.name))
            }

            override fun delete(saverEntity: SaverEntity) {
               deleteDialogBox(saverEntity)
            }

            override fun update(saverEntity: SaverEntity) {
                setDialog(saverEntity)
            }
        })
    }


    private fun setRecycleView(view: View){
        var recyclerView = view.findViewById<RecyclerView>(R.id.list_item)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListAdapter(requireContext())
        recyclerView.adapter = adapter
    }

    private fun search(view: View){
        val searchBar = view.findViewById<SearchView>(R.id.search_bar)
        searchBar.clearFocus()

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }

    private fun filter(newText: String?) {
        val newAllList = ArrayList<SaverEntity>()
        searchList.forEach {
            if(it.name.toLowerCase().contains(newText!!.toLowerCase())){
                newAllList.add(it)
            }
        }
        if(newAllList.isEmpty()){

        }else{
            adapter.upDate(newAllList)
        }
    }

    private fun setViewModelObserver(){
        viewModel.allData.observe(viewLifecycleOwner){
            it.let {
                adapter.upDate(it)
                searchList.addAll(it)
            }
        }
    }

    private fun navigateToInsertScreen(){
        val action = AppListFragmentDirections.actionAppListFragmentToInsertScreenFragment()
       findNavController().navigate(action)
    }


    fun setDialog(saverEntity: SaverEntity){
        val builder = AlertDialog.Builder(requireContext()).create()
        val view = layoutInflater.inflate(R.layout.add_dialog,null)
        builder.setView(view)

        val userID = view.findViewById<EditText>(R.id.userName_edit)
        userID.setText(saverEntity.userName)
        val password = view.findViewById<EditText>(R.id.password_edit)
        password.setText(saverEntity.password)

        val appName = view.findViewById<MaterialTextView>(R.id.appNAME)
        appName.text = saverEntity.name

        val saveButton = view.findViewById<Button>(R.id.save)
        val cancelButton = view.findViewById<Button>(R.id.cancel)
        saveButton.setOnClickListener {
            if(userID.text.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty()
                &&( !userID.text.toString().equals(saverEntity.userName) || !password.text.toString().equals(saverEntity.password)) ) {
                viewModel.update(
                    SaverEntity(
                        saverEntity.name,
                        userID.text.toString(),
                        password.text.toString(),
                        getCurrentDateTime()
                    )
                )
                builder.dismiss()
            }
        }
        cancelButton.setOnClickListener {
            builder.dismiss()
        }
        builder.show()
    }

    private fun deleteDialogBox(saverEntity: SaverEntity){
        var alertDialog =  AlertDialog.Builder(ContextThemeWrapper(requireContext(),R.style.AlertDialog))
        alertDialog.setTitle("Confirm Delete...!!")
        alertDialog.setIcon(R.drawable.ic_baseline_delete_forever_24)
        alertDialog.setMessage("Are you sure,you want to delete")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("yes"){_,_ ->
            viewModel.delete(saverEntity)
            Toast.makeText(requireContext(),"Deleted",Toast.LENGTH_SHORT).show()
        }
        alertDialog.setNegativeButton("Cancel"){_,_ ->

        }
        alertDialog.show()
    }

    fun getCurrentDateTime(): String{
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.LLL.yyyy HH:mmm:ss aaa")
        val dateTime = simpleDateFormat.format(calendar.time).toString()
        return dateTime
    }


}