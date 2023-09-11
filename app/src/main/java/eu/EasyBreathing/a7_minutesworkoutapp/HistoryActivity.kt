package eu.EasyBreathing.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    // creează o legătură pentru layout
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//inflate the layout
        binding = ActivityHistoryBinding.inflate(layoutInflater)
// leagă layout la această activitate
        setContentView(binding?.root)

//Configurarea barei de acțiuni în History Screen Activity și
// adăugând un buton săgeată înapoi vom facem clic pe eveniment pentru asta.)
        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //setarea butonului înapoi
            actionbar.title = "HISTORY" // Setarea unui titlu în bara de acțiuni.
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    // Funcția este utilizată pentru a obține datele de finalizare a exercițiului de listă.
    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
          historyDao.fetchALlDates().collect { allCompletedDatesList->
              //aici datele care au fost tipărite în jurnal.
              //  Vom transmite acea listă clasei de adaptoare pe care am creat-o și o vom lega la vizualizarea reciclatorului.)
              if (allCompletedDatesList.isNotEmpty()) {
                  // Aici, dacă dimensiunea listei este mai mare decât 0, vom afișa articolul în vizualizarea reciclatorului sau vom arăta vizualizarea text că nu sunt disponibile date.
                  binding?.tvHistory?.visibility = View.VISIBLE
                  binding?.rvHistory?.visibility = View.VISIBLE
                  binding?.tvNoDataAvailable?.visibility = View.GONE

                  // Creează vertical Layout Manager
                  binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                  // Adaptorul istoric este inițializat și lista este trecută în param.
                  val dates = ArrayList<String>()
                  for (date in allCompletedDatesList){
                      dates.add(date.date)
                  }
                  val historyAdapter = HistoryAdapter(ArrayList(dates))

                  // Aceseaza RecyclerView Adapter și încărcați datele în el
                  binding?.rvHistory?.adapter = historyAdapter
              } else {
                  binding?.tvHistory?.visibility = View.GONE
                  binding?.rvHistory?.visibility = View.GONE
                  binding?.tvNoDataAvailable?.visibility = View.VISIBLE
              }
          }
       }

    }

    override fun onDestroy() {
        super.onDestroy()
// resetați legarea la nul pentru a evita scurgerea memoriei
        binding = null
    }
}