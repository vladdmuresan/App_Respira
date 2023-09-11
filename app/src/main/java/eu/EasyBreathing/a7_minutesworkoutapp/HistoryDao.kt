package eu.EasyBreathing.a7_minutesworkoutapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// o interfață dao cu metoda insert
@Dao
interface HistoryDao {

    @Insert
   suspend fun insert(historyEntity: HistoryEntity)

    @Query("Select * from `history-table`")
    fun fetchALlDates():Flow<List<HistoryEntity>>
}