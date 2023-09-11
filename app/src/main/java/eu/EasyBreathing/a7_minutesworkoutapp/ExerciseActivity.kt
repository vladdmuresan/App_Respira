package eu.EasyBreathing.a7_minutesworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ActivityExerciseBinding
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Adăugarea unei variabile pentru cele 10 secunde REST temporizator
    private var restTimer: CountDownTimer? =
        null // Variabila pt Rest Timer iar mai târziu îl vom inițializa.
    private var restProgress =
        0 // Variabilă pentru progresul cronometrului. Ca valoare inițială, progresul de repaus este setat la 0. Pe măsură ce suntem pe cale să începem.

    // Adăugarea unei variabile pentru cronometrul de exercițiu de 30 de secunde
    private var exerciseTimer: CountDownTimer? = null // Variabilă pentru Cronometru de exerciții și mai târziu o vom inițializa.
    private var exerciseProgress = 0 // Variabilă pentru progresul cronometrului exercițiului. Ca valoare inițială, progresul exercițiului este setat la 0. Pe măsură ce suntem pe cale să începem.

    private var exerciseTimerDuration:Long = 30
    // Variabila pentru lista de exerciții și poziția curentă a exercițiului aici este -1 deoarece elementul de pornire a listei este 0

    private var exerciseList: ArrayList<ExerciseModel>? = null //Vom inițializa lista mai târziu.
    private var currentExercisePosition = -1 //Poziția curentă a exercițiului.

    //creați o variabilă binding
    private var binding:ActivityExerciseBinding? = null
    private var tts: TextToSpeech? = null // Variabilă pentru Text to Speech

    //  Declararea variabilei playerului media pentru redarea unui sunet de notificare atunci când exercițiul este pe cale să înceapă.)
    private var player: MediaPlayer? = null


    //  Declararea unei variabile a unei clase de adaptor pentru a o lega la vizualizarea recycler view.)
    // Declararea unui exerciseAdapter obiect care va fi inițializat ulterior.
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
// apoi setam bara de acțiuni de asistență și obținem toolBarExercise utilizand binding
//variable
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        //  Inițializarea variabilei de Text to Speech.)
        tts = TextToSpeech(this, this)

        //Initializarea si Atribuirea unei liste de exerciții implicite variabilei noastre de listă
        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        //  Apelarea funcției în care am legat adaptorul la vizualizarea reciclatorului pentru a afișa datele în UI.)
        // stabilirea exercițiului recycler view
        setupExerciseStatusRecyclerView()
        // END
    }


    //Configurarea vizualizării Pregătiți-vă cu 10 secunde de cronometru
    // Funcția este utilizată pentru a seta temporizatorul pentru REST.

    private fun setupRestView() {

        //  Redarea unui sunet de notificare atunci când exercițiul este pe cale să înceapă când vă aflați în starea de repaus
        //  fișierul de sunet este adăugat în folderul brut ca resursă.)

        // Aici este adăugat fișierul de sunet la "raw" folder în resurse.
        // Și utilizat folosind MediaPlayer. MediaPlayer-ul poate fi folosită pentru a controla redarea
        //de fișiere și fluxuri audio/video.

        try {
            val soundURI =
                Uri.parse("android.resource://eu.respira.a7_minutesworkoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false // Setează playerul să fie în buclă sau fără buclă.
            player?.start() // Începe redarea.
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.upcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE

        // Aici, în primul rând, vom verifica dacă cronometrul rulează și nu este nul, apoi anulăm cronometrul de rulare și porniți-l pe cel nou.
        // Și setați progresul la 0.

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        //Setarea numelui exercițiului viitor în elementul UI
        // Aici am setat numele exercițiului viitor la vizualizarea text
        // Aici, deoarece poziția curentă este -1 în mod implicit, pentru a fi selectată din listă ar trebui să fie 0, așa că am mărit-o cu +1.
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()
        // Această funcție este utilizată pentru a seta detaliile progresului.
        setRestProgressBar()
    }


    // Configurarea cronometrului de 10 secunde pentru vizualizarea de odihnă și actualizarea lui continuă.
    // Funcția este utilizată pentru a seta progresul cronometrului folosind progresul
    private fun setRestProgressBar() {

        binding?.progressBar?.progress = restProgress // Setează progresul curent la valoarea specificată.


        // @param millisInFuture Numărul de milisecunde în viitor de la apel
        //  ls {#start()} până se termină numărătoarea inversă și {#onFinish()} este apelata.
        // @param countDownInterval Intervalul de-a lungul drumului pentru a primi {#onTick(long)} callbacks.

        // Aici am pornit un cronometru de 10 secunde, astfel încât 10000 este milisecunde este de 10 secunde și intervalul de numărătoare inversă este de 1 secundă, deci 1000.
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // Este mărită cu 1
                binding?.progressBar?.progress = 10 - restProgress // Indică progresul barei de progres
                binding?.tvTimer?.text =
                    (10 - restProgress).toString()  // Progresul curent este setat la vizualizarea text în termeni de secunde.
            }

            override fun onFinish() {
                // Când cele 10 secunde se vor finaliza, aceasta va fi executată.
                currentExercisePosition++

                // Când primim o poziție actualizată a exercițiului, setați acel element din listă ca fiind selectat și notificați clasa adaptorului.)

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged() // Am notificat elementul curent către clasa adaptorului pentru a-l reflecta în interfața de utilizare.
           setupExerciseView()
            }
        }.start()
    }



    // Configurarea Vizualizării exercițiului cu un cronometru de 30 de secunde


    // Funcția este utilizată pentru a seta progresul temporizatorului folosind progresul pentru Exercise View.

    private fun setupExerciseView() {
// schimbarea etichetei exercițiului viitor și a vizibilității numelui.)
        // Aici, în funcție de vizualizare, faceți-o vizibilă deoarece aceasta este vizualizarea exercițiului, astfel încât vizualizarea exercițiului este vizibilă și vizualizarea de odihnă nu.
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.upcomingLabel?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE


        // Aici, în primul rând, vom verifica dacă cronometrul rulează și nu este nul, apoi anulăm cronometrul de rulare și porniți-l pe cel nou.
        //Și setați progresul la valoarea inițială care este 0.

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        // Obțineți numele exercițiului curent din listă și transmiteți-l metodei de vorbire pe care am creat-o.)
        speakOut(exerciseList!![currentExercisePosition].getName())

        // Configurarea numelui exercițiului curent și a vizualizării imaginii la elementul UI.
        // Aici numele și imaginea exercițiului curent sunt setate pentru vizualizarea exercițiului.

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()

    }


    // Dupa REST View Setting măriți cronometrul de 30 de secunde pentru vizualizarea Exercițiu și actualizați-l continuu
    // Funcția este utilizată pentru a seta progresul cronometrului folosind progresul pentru vizualizarea exercițiului timp de 30 de secunde

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerDuration.toInt() - exerciseProgress
               binding?.tvTimerExercise?.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false) // exercițiul este finalizat, astfel încât selecția este setată la fals
                    exerciseList!![currentExercisePosition].setIsCompleted(true) // actualizarea în listă că acest exercițiu este finalizat
                    exerciseAdapter?.notifyDataSetChanged()
                    setupRestView()
                } else {
                     finish()
                  val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }

            }
        }.start()

    }


    // Distrugerea temporizatorului la închiderea activității sau a aplicației
    // Aici, în funcția Destroy, vom reseta cronometrul de odihnă dacă rulează.

    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        // Oprirea funcției Text to Speech atunci când activitatea este distrusă
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }



        //  Când activitatea este distrusă dacă instanța playerului media nu este nulă, atunci opriți-o.)
        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
        binding = null
    }


    // Aceasta este funcția de înlocuire TextToSpeech
    //  Apelat pentru a semnala finalizarea inițializării motorului TextToSpeech.

    override fun onInit(status: Int) {

        //  După inițializarea variabilei, setați limba după un rezultat „de succes”.
        if (status == TextToSpeech.SUCCESS) {
            // setați engleza americană ca limbă pentru tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }



    // Crearea unei funcții pentru a rosti textul.)
    // Funcția este folosită pentru a rosti textul pe care îi transmitem.

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }




    // Funcția este utilizată pentru a configura recycler view to UI si a asigan Layout Manager and Adapter Class este atașat de acesta.
    private fun setupExerciseStatusRecyclerView() {
        // Definirea unui manager de layout pentru vizualizarea de reciclare
        // Aici am folosit un LinearLayout Manager cu scroll orizontal.
       binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Pe măsură ce adaptorul așteaptă lista de exerciții și contextul, inițializați-l trecându-l.
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)

        // Clasa adaptorului este atașată la vizualizarea reciclatorului
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }



    // Funcția este utilizată pentru a lansa dialogul personalizat de confirmare.
    //Efectuarea pașilor pentru a afișa dialogul personalizat pentru confirmarea butonului înapoi în timp ce exercițiul se desfășoară.
    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        //creeaza o variabila binding
         val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        // Setați conținutul ecranului de la a layout resource.
        // Resursa va fi inflated, adăugând toate vizualizările de nivel superior pe ecran.
        customDialog.setContentView(dialogBinding.root)
        //pentru a se asigura că utilizatorul face clic pe unul dintre butoane și că dialogul
        // este respins când se face clic pe părțile din jur ale ecranului
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        //Porniți dialogul și afișați-l pe ecran.
        customDialog.show()
    }

}