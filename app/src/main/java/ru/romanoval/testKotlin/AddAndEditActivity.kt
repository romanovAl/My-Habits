package ru.romanoval.testKotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.activity_add_and_edit.*

class AddAndEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_and_edit)

        val extras = intent.extras

        if (extras == null){

            val priorities = listOf("Высокий", "Средний", "Низкий");
            val adapterPriority = ArrayAdapter(this, R.layout.list_item,priorities)
            (habitPriorityInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPriority)

            val periods = listOf("Каждый день", "Будние дни", "Выходные дни", "Раз в неделю", "Раз в месяц");
            val adapterPeriod = ArrayAdapter(this, R.layout.list_item, periods)
            (habitPeriodInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPeriod)

            addAndEditFab.setOnClickListener{


                val hab = Habit(habitNameAddAndEdit.text.toString(),habitDescriptionAddAndEdit.text.toString(),
                    habitPriorityAddAndEdit.text.toString(), radioButtonGood.isActivated, habitPeriodAddAndEdit.text.toString(),
                    "#757de8", habitDoneAddAndEdit.text?.toString()?.toInt())

                val intent = Intent(this, MainActivity::class.java).run {
                    putExtra("new", hab)
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }

        else{
            val hab = intent.getSerializableExtra("object") as Habit
            val pos = intent.getIntExtra("position",0) as Int

            habitPriorityAddAndEdit.setText(hab.priority)
            habitPeriodAddAndEdit.setText(hab.period)

            val priorities = listOf("Высокий", "Средний", "Низкий");
            val adapterPriority = ArrayAdapter(this, R.layout.list_item,priorities)
            (habitPriorityInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPriority)

            val periods = listOf("Каждый день", "Будние дни", "Выходные дни", "Раз в неделю", "Раз в месяц");
            val adapterPeriod = ArrayAdapter(this, R.layout.list_item, periods)
            (habitPeriodInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapterPeriod)

            habitNameAddAndEdit.setText(hab.name)
            habitDescriptionAddAndEdit.setText((hab.description))

            if (hab.type) {
                radioButtonGood.isChecked = true
            }else{
                radioButtonBad.isChecked = true
            }
            habitDoneAddAndEdit.setText(hab.times?.toString())

            addAndEditFab.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_24))

            addAndEditFab.setOnClickListener {

                val habit  = Habit(habitNameAddAndEdit.text.toString(), habitDescriptionAddAndEdit.text.toString(),
                    habitPriorityAddAndEdit.text.toString(), radioButtonGood.isChecked, habitPeriodAddAndEdit.text.toString()
                    ,"#757de8", habitDoneAddAndEdit.text?.toString()?.toInt())

                val intent = Intent(this, MainActivity::class.java).run {
                    putExtra("new", habit)
                    putExtra("position", pos)
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }
}