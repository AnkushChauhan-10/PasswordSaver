package com.example.passwordsaver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordsaver.R
import com.example.passwordsaver.database.SaverEntity
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text

class ListAdapter(private val context: Context): RecyclerView.Adapter<ListAdapter.ListHolder>() {


    private lateinit var mlistener: ListAdapterListener

    interface ListAdapterListener{
        fun onClickListItem(saverEntity: SaverEntity)
        fun delete(saverEntity: SaverEntity)
        fun update(saverEntity: SaverEntity)
    }


    fun setOnclick(listener: ListAdapterListener){
        mlistener = listener
    }

    val allList = ArrayList<SaverEntity>()

    inner class ListHolder(view: View, listener:ListAdapterListener): RecyclerView.ViewHolder(view){

        val appName = view.findViewById<TextView>(R.id.rec_app_name)
        val date = view.findViewById<TextView>(R.id.rec_date)

        val userName = view.findViewById<TextView>(R.id.rec_user_name)
        val password = view.findViewById<TextView>(R.id.rec_password)
        val select = view.findViewById<LinearLayout>(R.id.rec_select_layout)

        val menu = view.findViewById<ImageButton>(R.id.menu_button)
        val l = listener

        init {

            menu.setOnClickListener {
                popupMenu(it)
            }

            select.setOnClickListener {
                listener.onClickListItem(allList[adapterPosition])
            }
        }

        private fun popupMenu(view: View){
            val popupMenu = PopupMenu(context,view)
            popupMenu.inflate(R.menu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.edit->{
                        l.update(allList[adapterPosition])
                        true
                    }
                    R.id.delete_menu->{
                        l.delete(allList[adapterPosition])
                        true
                    }
                    else-> true
                }
            }
            popupMenu.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val holder = LayoutInflater.from(context).inflate(R.layout.recycle_list,parent,false)
        return ListHolder(holder,mlistener)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val currentWord = allList[position]
        holder.appName.text = currentWord.name
        holder.userName.text = currentWord.userName
        holder.password.text = currentWord.password
        holder.date.text = currentWord.date
    }

    override fun getItemCount(): Int {
        return allList.size
    }

    fun upDate(newList: List<SaverEntity>){
        allList.clear()
        allList.addAll(newList)
        notifyDataSetChanged()
    }


}
