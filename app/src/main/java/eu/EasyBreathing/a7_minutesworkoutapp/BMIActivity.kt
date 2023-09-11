package eu.EasyBreathing.a7_minutesworkoutapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    // Am adăugat variabile pentru vizualizarea unitatilor in sistem METRIC și US UNITS și o variabilă pentru afișarea vizualizării curente selectate..)
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // pt sist Metric
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // pt sist US Units
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW // O variabilă pentru a păstra o valoare si pentru a face vizibilă o vizualizare selectată
    // creeam legături pentru activitate

    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //aspect
        binding = ActivityBmiBinding.inflate(layoutInflater)
        //conectaaza aspectul la această activitate
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //setarea butonului înapoi
        supportActionBar?.title = "CALCULATE BMI" // Setarea unui titlu în bara de acțiuni.
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        // Când se lansează activitatea, face vizibilă VEDEREA UNITĂȚILOR METRICE.

        makeVisibleMetricUnitsView()

        // Radio Group schimba listener care este setat la radio group care se adauga in XML.
        //folosim ' _' pentru prima valoare, pentru ca nu avem nevoie de ea
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            // Aici, dacă ID-ul de control este setat in UNITĂȚI METRICE, atunci faceți vizualizarea vizibilă, altfel este UNITĂȚI SUA.
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        // Butonul va calcula valorile de intrare în unități metrice
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun calculateUnits(){
        // Gestionarea vizualizării curente vizibile și calcularea US UNITS vizualiza valorile de intrare dacă acestea sunt valide.)

        if (currentVisibleView == METRIC_UNITS_VIEW) {
            // Valorile sunt validate.
            if (validateMetricUnits()) {

                // Valoarea înălțimii este convertită în valoare flotantă și împărțită la 100 pentru a o converti în metru.
                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                // Valoarea greutății este convertită în valoare flotantă.
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                // Valoarea IMC este calculată în UNITĂȚI METRICE folosind valoarea înălțimii și greutății.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {

            // Valorile sunt validate.
            if (validateUsUnits()) {

                val usUnitHeightValueFeet: String =
                    binding?.etUsMetricUnitHeightFeet?.text.toString() // Valoarea înălțimii picioarelor introdusă în componenta EditText.
                val usUnitHeightValueInch: String =
                    binding?.etUsMetricUnitHeightInch?.text.toString() // Valoarea înălțimii inch introdusă în componenta EditText.
                val usUnitWeightValue: Float = binding?.etUsMetricUnitWeight?.text.toString()
                    .toFloat() // Valoarea de greutate introdusă în componenta EditText.

                // Aici valorile Height Feet și Inch sunt îmbinate și multiplicate cu 12 pentru a le converti în inci.
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                // Aceasta este formula pentru rezultatul US UNITS.
                // Link de referință: https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi) // Afișarea rezultatului în UI
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    // Crearea unei funcții pentru a face vizibilă vizualizarea UNITĂȚI METRICE.
    //Funcția este utilizată pentru a face vizibilă METRIC UNITS VIEW și pentru a ascunde US UNITS VIEW.

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Vizualizarea curentă este actualizată aici.
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE // METRIC Înălțime UNITS VIEW este vizibilă
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE // METRIC Greutate UNITS VIEW este vizibilă
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE // face vedere greutate Gone.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE // face vedere înălțimea picioarelor Gone.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE // face vedere înălțimea inch Gone.

        binding?.etMetricUnitHeight?.text!!.clear() // valoarea înălțimii este ștearsă dacă este adăugată.
        binding?.etMetricUnitWeight?.text!!.clear() // valoarea ponderii este ștearsă dacă este adăugată.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }
    // sfarsit

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Vizualizarea curentă este actualizată aici.
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE // METRIC Înălțime UNITS VIEW este InVisible
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE // METRIC Greutate UNITĂȚI VIEW este InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // face vizibilă vizualizarea greutății.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE // face vizibilă înălțimea picioarelor.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE // face vizibilă înălțimea în inch.

        binding?.etUsMetricUnitWeight?.text!!.clear() // valoarea ponderii este ștearsă.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // valoarea înălțimii picioarelor este ștearsă.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // înălțimea inch este ștersa.

       binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }


    // Funcția este utilizată pentru a valida valorile de intrare pentru UNITĂȚI METRICE.

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }


    // Validarea valorilor de intrare pentru vizualizarea US UNITS.)
    // Funcția este utilizată pentru a valida valorile de intrare pentru UNITĂȚI SUA.

    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }

    // Funcția este utilizată pentru a afișa rezultatul UNITĂȚII METRICE.

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Folosiți pentru a seta aspectul rezultat vizibil
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE

        // Acesta este folosit pentru a rotunji valoarea rezultatului la 2 valori zecimale după "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Valoarea este setată la TextView
        binding?.tvBMIType?.text = bmiLabel // Eticheta este setată la TextView
        binding?.tvBMIDescription?.text = bmiDescription // Descrierea este setată la TextView
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}