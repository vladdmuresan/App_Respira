package eu.respira.a7_minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.respira.a7_minutesworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //  The binding este numita la fel ca și numele aspectului cu Binding attached
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            // Lansare the BMI Activity
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }


        //  Adăugarea unui eveniment de clic pentru a lansarea History Screen Activity din Main Activity.)
        // START
        binding?.flHistory?.setOnClickListener {
            // Lansarea History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        //END
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}