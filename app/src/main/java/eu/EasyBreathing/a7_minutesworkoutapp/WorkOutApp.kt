package eu.EasyBreathing.a7_minutesworkoutapp

import android.app.Application
// creează clasa aplicației pt history
class WorkOutApp: Application() {

    val db:HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}