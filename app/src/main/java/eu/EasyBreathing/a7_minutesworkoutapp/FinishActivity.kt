package eu.EasyBreathing.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater) // reprezintă legăturile pentru elementele de interfață grafică (UI) din fișierul XML al activității.
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

        // o clasă de acces la date care oferă metode pentru a lucra cu o bază de date locală.
        val dao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)
    }


    // adăuga data curentă în baza de date utilizând obiectul "dao" (HistoryDao).
    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date : ", "" + dateTime)


        // am luat un exemplu de Date Formatter deoarece ne va formata data selectată în formatul pe care îl transmitem ca parametru.
        // am trecut formatul ca dd MMM yyyy HH:mm:ss.
        // The Locale : Obține valoarea curentă a localizării implicite pentru această instanță al Java Virtual Machine.
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date : ", "" + date) // Data formatată este tipărită în jurnal.

      lifecycleScope.launch {
          historyDao.insert(HistoryEntity(date)) //Se adaugă datele în baza de date utilizând metoda "insert" a obiectului "historyDao"
          Log.e(
              "Date : ",
              "Added..."
          ) // scris în jurnalul care este tipărit dacă execuția completă a exercitiului este finalizată.
      }
      }

}