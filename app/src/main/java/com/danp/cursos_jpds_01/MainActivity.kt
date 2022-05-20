package com.danp.cursos_jpds_01

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val Context.dataStore by preferencesDataStore(NotePrefs.PREFS_NAME)
    lateinit var notePrefs: NotePrefs
    lateinit var tvPeriodo: TextView
    lateinit var tvEscuela: TextView
    lateinit var tvCodigo: TextView
    lateinit var tvNombre: TextView
    lateinit var tvSemestre: TextView
    lateinit var tvDuracion: TextView
    lateinit var etPeriodo: EditText
    lateinit var etEscuela: EditText
    lateinit var etCodigo: EditText
    lateinit var etNombre: EditText
    lateinit var etSemestre: EditText
    lateinit var etDuracion: EditText
    lateinit var layoutEjemplo: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPeriodo = findViewById(R.id.tvPeriodo)
        tvEscuela = findViewById(R.id.tvEscuela)
        tvCodigo = findViewById(R.id.tvCodigo)
        tvNombre = findViewById(R.id.tvNombre)
        tvSemestre = findViewById(R.id.tvSemestre)
        tvDuracion = findViewById(R.id.tvDuracion)
        etPeriodo = findViewById(R.id.etPeriodo)
        etEscuela = findViewById(R.id.etEscuela)
        etCodigo = findViewById(R.id.etCodigo)
        etNombre = findViewById(R.id.etNombre)
        etSemestre = findViewById(R.id.etSemestre)
        etDuracion = findViewById(R.id.etDuracion)
        layoutEjemplo = findViewById(R.id.layoutEjemplo)
        notePrefs = NotePrefs(dataStore)

        val spFondo: Spinner = findViewById(R.id.spFondo)
        val spEstilo: Spinner = findViewById(R.id.spEstilo)
        val spTamanio: Spinner = findViewById(R.id.spTamanio)
        ArrayAdapter.createFromResource(
            this,
            R.array.fondos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spFondo.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.fuentes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spEstilo.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.tamanios,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spTamanio.adapter = adapter
        }

        lifecycleScope.launch {
            notePrefs.backgroundColor.collect { mycolor ->
                layoutEjemplo.setBackgroundColor(Integer.parseInt(mycolor.toString()))
            }
        }

        lifecycleScope.launch {
            notePrefs.fontStyle.collect { mystyle ->
                cargarEstilo(Integer.parseInt(mystyle.toString()))
            }
        }

        lifecycleScope.launch {
            notePrefs.fontSize.collect { mysize ->
                cargarTamanio(mysize.toFloat())
            }
        }

        spFondo.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lifecycleScope.launch{
                    if(position == 1) {
                        layoutEjemplo.setBackgroundColor(Color.WHITE)
                        notePrefs.saveNoteBackgroundColor(Color.WHITE.toString())
                    }
                    else if(position == 2) {
                        layoutEjemplo.setBackgroundColor(Color.BLUE)
                        notePrefs.saveNoteBackgroundColor(Color.BLUE.toString())
                    }
                    else if(position == 3) {
                        layoutEjemplo.setBackgroundColor(Color.GREEN)
                        notePrefs.saveNoteBackgroundColor(Color.GREEN.toString())
                    }
                    else if(position == 4) {
                        layoutEjemplo.setBackgroundColor(Color.YELLOW)
                        notePrefs.saveNoteBackgroundColor(Color.YELLOW.toString())
                    }
                    else if(position == 5){
                        layoutEjemplo.setBackgroundColor(Color.GRAY)
                        notePrefs.saveNoteBackgroundColor(Color.GRAY.toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spEstilo.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lifecycleScope.launch{
                    if(position == 1) {
                        cargarEstilo(Typeface.NORMAL)

                        notePrefs.saveNoteFontStyle(Typeface.NORMAL.toString())
                    }
                    else if(position == 2) {
                        cargarEstilo(Typeface.BOLD)
                        notePrefs.saveNoteFontStyle(Typeface.BOLD.toString())
                    }
                    else if(position == 3) {
                        cargarEstilo(Typeface.ITALIC)
                        notePrefs.saveNoteFontStyle(Typeface.ITALIC.toString())
                    }
                    else if(position == 4) {
                        cargarEstilo(Typeface.BOLD_ITALIC)
                        notePrefs.saveNoteFontStyle(Typeface.BOLD_ITALIC.toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spTamanio.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lifecycleScope.launch{
                    if(position == 1) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f)
                        notePrefs.saveNoteFontSize(12.0f.toString())
                    }
                    else if(position == 2) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f)
                        notePrefs.saveNoteFontSize(13.0f.toString())
                    }
                    else if(position == 3) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f)
                        notePrefs.saveNoteFontSize(14.0f.toString())
                    }
                    else if(position == 4) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f)
                        notePrefs.saveNoteFontSize(15.0f.toString())
                    }
                    else if(position == 5) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
                        notePrefs.saveNoteFontSize(16.0f.toString())
                    }
                    else if(position == 6) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0f)
                        notePrefs.saveNoteFontSize(17.0f.toString())
                    }
                    else if(position == 7) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0f)
                        notePrefs.saveNoteFontSize(18.0f.toString())
                    }
                    else if(position == 8) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.0f)
                        notePrefs.saveNoteFontSize(19.0f.toString())
                    }
                    else if(position == 9) {
                        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0f)
                        notePrefs.saveNoteFontSize(20.0f.toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun cargarTamanio(tam: Float) {
        tvPeriodo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        tvEscuela.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        tvCodigo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        tvNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        tvSemestre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        tvDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etPeriodo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etEscuela.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etCodigo.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etSemestre.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
        etDuracion.setTextSize(TypedValue.COMPLEX_UNIT_SP, tam)
    }

    private fun cargarEstilo(estilo: Int) {
        tvPeriodo.setTypeface(null, estilo)
        tvEscuela.setTypeface(null, estilo)
        tvCodigo.setTypeface(null, estilo)
        tvNombre.setTypeface(null, estilo)
        tvSemestre.setTypeface(null, estilo)
        tvDuracion.setTypeface(null, estilo)
        etPeriodo.setTypeface(null, estilo)
        etEscuela.setTypeface(null, estilo)
        etCodigo.setTypeface(null, estilo)
        etNombre.setTypeface(null, estilo)
        etSemestre.setTypeface(null, estilo)
        etDuracion.setTypeface(null, estilo)
    }

}