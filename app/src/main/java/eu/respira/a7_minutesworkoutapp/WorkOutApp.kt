package eu.respira.a7_minutesworkoutapp

import android.app.Application
// creează clasa aplicației
class WorkOutApp: Application() {

    val db:HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}