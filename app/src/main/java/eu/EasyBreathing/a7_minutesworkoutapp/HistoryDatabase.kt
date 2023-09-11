package eu.EasyBreathing.a7_minutesworkoutapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// creează baza de date
@Database(entities = [HistoryEntity::class],version = 1)
abstract class HistoryDatabase:RoomDatabase(){

    abstract fun historyDao():HistoryDao

    // Defineste un obiect însoțitor, acest lucru ne permite să adăugăm funcții pe HistoryDatabase class.

    companion object {
        // INSTANCE va păstra o referință la orice bază de date returnată prin getInstance.
        // Acest lucru ne va ajuta să evităm inițializarea în mod repetat a bazei de date.
        // Valoarea unei variabile volatile nu va fi niciodată memorată în cache și toate scrierile și citirile vor fi făcute către și din memoria principală. Înseamnă că modificările făcute de unul
        // firele către datele partajate sunt vizibile pentru alte fire.
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        // Funcția de ajutor pentru a obține baza de date.
        // Dacă o bază de date a fost deja preluată, baza de date anterioară va fi returnată.
        // În caz contrar, creați o nouă bază de date.
        // Această funcție este threadsafe, iar apelanții ar trebui sa depoziteze  rezultatul pentru mai multe baze de date pentru a evita overhead.
        // Acesta este un exemplu de simplu Singleton model care ia alt Singleton ca un argument in Kotlin.
        // @param context Contextul aplicației Singleton, folosit pentru a avea acces la filesystem.

        fun getInstance(context: Context): HistoryDatabase {
            // Mai multe fire pot solicita baza de date în același timp, asigurați-vă că inițializam doar o dată folosind sincronizat. Un singur fir poate intra într-un bloc sincronizat la un timp.
            synchronized(this) {
                // Copiați valoarea curentă a INSTANCE la o variabilă locală deci Kotlin poate folosi distribuția inteligentă.
                // Distribuția inteligentă este disponibila numai pentru variabilele locale.
                var instance = INSTANCE

                // Daca instanța is `null` face o nouă bază de date instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    )
                        // Șterge și reconstruiește în loc de migrare dacă nu există obiect Migration.
                        .fallbackToDestructiveMigration()
                        .build()
                    // Atribuiți INSTANCE la noua bază de date creată.
                    INSTANCE = instance
                }

                // Returnează instanța; smart cast să fie non-null.
                return instance
            }
        }
    }

}