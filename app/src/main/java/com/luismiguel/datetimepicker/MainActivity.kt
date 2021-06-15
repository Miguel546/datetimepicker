package com.luismiguel.datetimepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.databinding.DataBindingUtil
import com.luismiguel.datetimepicker.databinding.ActivityMainBinding
import com.sociedadagricoladrokasa.moviles.datehour.metodos.DateTimePickerMetodos
import com.sociedadagricoladrokasa.moviles.datehour.toTimeText
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var scrollView: ScrollView
    private lateinit var btnConsultar: Button
    private lateinit var fechaInicio: EditText
    private lateinit var fechaFin: EditText
    private lateinit var horaInicio: EditText
    private lateinit var horaFin: EditText
    private lateinit var dateTimePickerMetodos: DateTimePickerMetodos
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private lateinit var horaStart : LocalTime
    private lateinit var horaEnd : LocalTime
    var fechaInicioCalendar : Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fechaInicioCalendar.time
        val year = fechaInicioCalendar.get(Calendar.YEAR)
        val month = fechaInicioCalendar.get(Calendar.MONTH)
        val day = fechaInicioCalendar.get(Calendar.DAY_OF_MONTH)

        val hour = fechaInicioCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = fechaInicioCalendar.get(Calendar.MINUTE)
        fechaInicioCalendar.add(Calendar.HOUR, 2);
        val year2 = fechaInicioCalendar.get(Calendar.YEAR)
        val month2 = fechaInicioCalendar.get(Calendar.MONTH)
        val day2 = fechaInicioCalendar.get(Calendar.DAY_OF_MONTH)

        val hour2 = fechaInicioCalendar.get(Calendar.HOUR_OF_DAY)
        val minute2 = fechaInicioCalendar.get(Calendar.MINUTE)
        System.out.println(fechaInicioCalendar.time)
        val defaultDateString = LocalDate.of(year, month, day).format(formatter)
        val defaultDateString2 = LocalDate.of(year2, month2, day2).format(formatter)
        horaStart = LocalTime.of(hour,minute,0)
        horaEnd = LocalTime.of(hour2,minute2,0)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        dateTimePickerMetodos = DateTimePickerMetodos(applicationContext)

        scrollView = binding.scrollView
        btnConsultar = binding.btnConsultar
        fechaInicio = binding.fechaInicio
        fechaFin = binding.fechaTermino
        horaInicio = binding.horaInicio
        horaFin = binding.horaTermino

        fechaInicio.setText(defaultDateString)
        fechaFin.setText(defaultDateString2)
        horaInicio.setText(horaStart.toTimeText())
        horaFin.setText(horaEnd.toTimeText())
        fechaInicio.setOnClickListener {
            dateTimePickerMetodos.showDateTimePicker("fechaInicio", fechaInicio, fechaFin, horaInicio, horaFin, supportFragmentManager)
        }

        fechaFin.setOnClickListener{

            dateTimePickerMetodos.showDateTimePicker("fechaFinal", fechaInicio, fechaFin, horaInicio, horaFin, supportFragmentManager)
        }

        horaInicio.setOnClickListener{

            dateTimePickerMetodos.showDateTimePicker("horaInicio", fechaInicio, fechaFin, horaInicio, horaFin, supportFragmentManager)
        }


        horaFin.setOnClickListener{

            dateTimePickerMetodos.showDateTimePicker("horaFinal", fechaInicio, fechaFin, horaInicio, horaFin, supportFragmentManager)
        }
    }
}