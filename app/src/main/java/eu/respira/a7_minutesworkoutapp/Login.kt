package eu.respira.a7_minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val registertext: TextView = findViewById(R.id.textViewRegister)

        registertext.setOnClickListener{
            val intent = Intent(this, Register::class.java )
            startActivity(intent)

        }




    }
}