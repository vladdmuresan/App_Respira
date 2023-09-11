package eu.EasyBreathing.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val loginText: TextView = findViewById(R.id.textView_login_now)
        loginText.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }

        val registerButton: Button = findViewById(R.id.button_register)
        registerButton.setOnClickListener {
            performSingUp()
        }



    }

    private fun performSingUp() {
        val email = findViewById<EditText>(R.id.editText_email_register)
        val passwod = findViewById<EditText>(R.id.editText_password_register)

        if (email.text.isEmpty() || passwod.text.isEmpty()){
            Toast.makeText(this, "Va rog completati toate campurile", Toast.LENGTH_SHORT)
                .show()
            return

        }

        val imputEmail = email.text.toString()
        val imputPassword = passwod.text.toString()

        auth.createUserWithEmailAndPassword(imputEmail,imputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext,
                        "Autentificare cu succes.",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Autentificare esuata.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Eroare logare ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }

    }
}