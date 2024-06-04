package com.drafe.yogatrainingapp

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.drafe.yogatrainingapp.asana.Asana
import com.drafe.yogatrainingapp.history.TrainHistory
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import java.util.concurrent.Executors

//private const val DATABASE_NAME = "yoga-train-db"
private const val DATABASE_NAME = "sqlite-7"

@Dao
interface TrainHistoryDao {

    @Query("SELECT * FROM Train")
    fun getTrainHistory(): Flow<List<TrainHistory>>

    @Query("SELECT * FROM Train WHERE id = (:id)")
    suspend fun getTrainHistoryById(id: UUID): TrainHistory
}

@Dao
interface AsanaDao {
    @Query("SELECT * FROM Asana")
    fun getAllAsanas(): Flow<List<Asana>>

    @Query("SELECT * FROM Asana WHERE id = (:id)")
    suspend fun getAsanaById(id: UUID): Asana
}


class YogaRepository private constructor(context: Context) {

    private val database: YogaAppDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            YogaAppDatabase::class.java,
            DATABASE_NAME
        )
        .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
        Log.d("RoomQuery", "SQL Query: $sqlQuery SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getTrainHistory(): Flow<List<TrainHistory>> {
        return database.trainHistoryDao().getTrainHistory()
    }

    suspend fun getTrainHistoryById(id: UUID): TrainHistory {
        return database.trainHistoryDao().getTrainHistoryById(id)
    }

    fun getAsanas(): Flow<List<Asana>> {
        return database.asanaDao().getAllAsanas()
    }

    suspend fun getAsanaById(id: UUID): Asana {
        return database.asanaDao().getAsanaById(id)
    }

    companion object {
        private var INSTANCE: YogaRepository? = null

        fun initialize(context: Context) {
            Log.d("YogaRepository", "initialize")
            if (INSTANCE == null) {
                INSTANCE = YogaRepository(context)
            }
        }

        fun get(): YogaRepository {
            return INSTANCE ?:
            throw IllegalStateException("YogaRepository must be initialized")
        }
    }
}


@Database(entities = [ TrainHistory::class, Asana::class ], version=1)
@TypeConverters(YogaTypeConverters::class)
abstract class YogaAppDatabase : RoomDatabase() {
    abstract fun trainHistoryDao(): TrainHistoryDao

    abstract fun asanaDao(): AsanaDao
}




class YogaTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

}