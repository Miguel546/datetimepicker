package com.sociedadagricoladrokasa.moviles.datehour.metodos

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.sociedadagricoladrokasa.moviles.datehour.fragment.DatePickerFragment
import com.sociedadagricoladrokasa.moviles.datehour.fragment.TimePickerFragment
import com.sociedadagricoladrokasa.moviles.datehour.getTime
import com.sociedadagricoladrokasa.moviles.datehour.setTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class DateTimePickerMetodos(applicationContext: Context) {

    var context: Context = applicationContext
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private lateinit var fechaInicioDate:EditText
    private lateinit var fechaFinDate:EditText
    private lateinit var horaInicioTime: EditText
    private lateinit var horaFinalTime: EditText
    var contador = 1


    public fun showDateTimePicker(tipo: String, fechaInicio:EditText, fechaFin:EditText, horaInicio: EditText, horaFinal: EditText, fm: FragmentManager){

        fechaInicioDate = fechaInicio
        fechaFinDate = fechaFin
        horaInicioTime = horaInicio
        horaFinalTime = horaFinal
        Log.i("fecha", "fechaInicioDate ${fechaInicioDate.text}")
        if(tipo.equals("fechaInicio")){
            showDatePicker(tipo, fechaInicio, fechaFin, horaInicio, horaFinal, fm)
        }else if(tipo.equals("fechaFinal")){

            showDatePicker(tipo, fechaInicio, fechaFin, horaInicio, horaFinal, fm)
        }else if(tipo.equals("horaInicio")){

            showTimePicker(tipo, fechaInicio, fechaFin, horaInicio, horaFinal, fm)
        }else if(tipo.equals("horaFinal")){

            showTimePicker(tipo, fechaInicio, fechaFin, horaInicio, horaFinal, fm)
        }
    }

    public fun showDatePicker(tipo: String, fechaInicio:EditText, fechaFin:EditText, horaInicio: EditText, horaFinal: EditText, fm: FragmentManager) {
        if(tipo.equals("fechaInicio")){
            setDate("fechaInicio", fechaInicio, fm)
        }else if(tipo.equals("fechaFinal")){
            setDate("fechaFinal", fechaFin, fm)
        }
    }

    private fun setTime(tipo: String, tiempo:EditText, fm: FragmentManager){
        val time = tiempo.getTime()
        Log.i("time", time.toString())
        showDialog(time.hour, time.minute, fm) { _, hour, minute ->
            val currentTime = LocalTime.of(hour, minute)

            if(tipo.equals("horaInicio")){
                horaInicioTime.setTime(currentTime)
                Log.i("currenttime", currentTime.toString())
                validarTime(tiempo, currentTime,time)
            }else if(tipo.equals("horaFinal")){
                horaFinalTime.setTime(currentTime)
                Log.i("currenttime", currentTime.toString())
                validarTime(tiempo, currentTime,time)
            }

        }
    }

    private fun showDialog(initialHour: Int, initialMinute: Int, fm: FragmentManager, observer: TimePickerDialog.OnTimeSetListener) {
        TimePickerFragment.newInstance(initialHour, initialMinute, observer)
            .show(fm, "time-picker")
    }

    public fun showTimePicker(tipo: String, fechaInicio:EditText, fechaFin:EditText, horaInicio: EditText, horaFinal: EditText, fm: FragmentManager) {
        if(tipo.equals("horaInicio")){
            setTime("horaInicio", horaInicio, fm)
        }else if(tipo.equals("horaFinal")){
            setTime("horaFinal", horaFinal, fm)
        }

    }
    private fun setDate(tipo:String, fecha:EditText, fm: FragmentManager){
        val date = getCurrentDate(fecha)
        Log.i("date", date.toString())
        Log.i("contador", ""+contador)
        contador++
        lateinit var datePickerFragment: DatePickerFragment
        DatePickerFragment.newInstance(
            date.year,
            date.monthValue,
            date.dayOfMonth
        ) { _, year, month, day ->

            if(tipo.equals("fechaInicio")){
                fechaInicioDate.setText(formatDate(year, month, day))
                //Log.i("date fechaInicioDate", getCurrentDate(fechaInicioDate).toString())
                validarDate(fecha, year, month, day, date)
            }else if(tipo.equals("fechaFinal")){
                fechaFinDate.setText(formatDate(year, month, day))
                validarDate(fecha, year, month, day, date)
            }
        }.show(fm, "date-picker")

    }

    private fun validarTime(time: EditText, currentTime: LocalTime, time1: LocalTime){
        if(isValidDateTime()){
            time.setTime(currentTime)
        }else{
            Snackbar.make(time, "La fecha de inicio no puede ser mayor a la fecha de fin", Snackbar.LENGTH_SHORT).show()
            time.setTime(time1)
        }
    }

    private fun validarDate(date: EditText, year: Int, month: Int, day: Int, date1: LocalDate){
        if(isValidDateTime()){
            //date.setText(formatDate(year, month, day))
            Log.i("isValidTime","isValidTime")
        }else{
            Snackbar.make(date, "La fecha de inicio no puede ser mayor a la fecha de fin", Snackbar.LENGTH_SHORT).show()
            date.setText(formatDate2(date1.year, date1.monthValue, date1.dayOfMonth))
            //fechaInicioDate.setText(formatDate(date1.year, date1.monthValue, date1.dayOfMonth))
            Log.i("isNotValidTime","isNotValidTime")
        }
    }


    private fun getCurrentDate(editText: EditText): LocalDate {
        val date = editText.text.toString()
        return LocalDate.parse(date, formatter)
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val sanitizeMonth = month + 1
        return LocalDate.of(year, sanitizeMonth, day).format(formatter)
    }

    private fun formatDate2(year: Int, month: Int, day: Int): String {
        return LocalDate.of(year, month, day).format(formatter)
    }

    private fun isValidDateTime(): Boolean {
        val fi = getCurrentDate(fechaInicioDate)
        val hi = horaInicioTime.getTime()
        val ff = getCurrentDate(fechaFinDate)
        val hf = horaFinalTime.getTime()
        var fechaInicio : Calendar = Calendar.getInstance()
        var fechaFin : Calendar = Calendar.getInstance()
        Log.i("fecha fi",fi.toString())
        Log.i("fecha hi",hi.toString())
        Log.i("fecha ff",ff.toString())
        Log.i("fecha hf",hf.toString())
        fechaInicio.set(fi.year, fi.monthValue, fi.dayOfMonth, hi.hour, hi.minute, 0)
        fechaFin.set(ff.year, ff.monthValue, ff.dayOfMonth, hf.hour, hi.minute, 0)
        return fechaInicio < fechaFin
    }

}