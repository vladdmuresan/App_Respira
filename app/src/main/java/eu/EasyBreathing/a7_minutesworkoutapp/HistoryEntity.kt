package eu.EasyBreathing.a7_minutesworkoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

// utilizeaza o entitate cu @param [tableName]
// utilizeaza @param [date] ca cheie primară
@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val date:String)
