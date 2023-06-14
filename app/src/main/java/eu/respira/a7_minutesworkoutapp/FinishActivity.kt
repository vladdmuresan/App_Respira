package eu.respira.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import eu.respira.a7_minutesworkoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        //obțineți dao prin baza de date din clasa aplicației
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }

    // START
    /**
     * Funcția este utilizată pentru a introduce data curentă a sistemului în sqlite database.
     */
    private fun addDateToDatabase(historyDao: HistoryDao) {

        val c = Calendar.getInstance() // Calendar Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.

        /**
         * Aici am luat un exemplu de Date Formatter deoarece ne va formata
         * data selectată în formatul pe care îl transmitem ca parametru și Locale.
         * Aici am trecut formatul ca dd MMM yyyy HH:mm:ss.
         *
         * The Locale : Obține valoarea curentă a localizării implicite pentru această instanță
         * al Java Virtual Machine.
         */
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime este formatat în formatul dat.
        Log.e("Formatted Date : ", "" + date) // Data formatată este tipărită în jurnal.

      lifecycleScope.launch {
          historyDao.insert(HistoryEntity(date)) //Se apelează funcția de adăugare a datei.
          Log.e(
              "Date : ",
              "Added..."
          ) // Tipărit în jurnal care este tipărit dacă execuția completă este finalizată.
      }
      }
    //END
}