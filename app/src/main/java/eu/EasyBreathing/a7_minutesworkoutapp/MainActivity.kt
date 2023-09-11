package eu.EasyBreathing.a7_minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //  The binding este numita la fel ca și numele aspectului cu Binding attached
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            // Lansare  Exercise Activity
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            // Lansare  BMI Activity
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }


        binding?.flHistory?.setOnClickListener {
            // Lansarea History Activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}