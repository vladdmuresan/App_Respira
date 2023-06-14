package eu.respira.a7_minutesworkoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  o entitate cu @param [tableName]
 * Utilizare @param [date] ca cheie primară
 * */
@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val date:String)
