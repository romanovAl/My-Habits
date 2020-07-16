package ru.romanoval.testKotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val ADD_HABIT = 12
    val EDIT_HABBIT = 13
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = ArrayList<Habit>()



        adapter = MainAdapter(data){ habit, position ->

            val intent = Intent(this, AddAndEditActivity::class.java).run {
                putExtra("position", position)
                putExtra("object", habit)
            }
            startActivityForResult(intent, EDIT_HABBIT)
        }

        mainRecycler.adapter = adapter

        mainFab.setOnClickListener{
            val intent = Intent(this, AddAndEditActivity::class.java).run {

                startActivityForResult(this, ADD_HABIT)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == ADD_HABIT && resultCode == Activity.RESULT_OK){

            adapter.addItem(data?.getSerializableExtra("new") as Habit)
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }
        else if(requestCode == EDIT_HABBIT){
            println((data?.getSerializableExtra("new") as Habit).toString())
            adapter.changeItem(data.getSerializableExtra("new") as Habit, data.getIntExtra("position",0) as Int)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}