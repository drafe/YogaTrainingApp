package com.drafe.yogatrainingapp

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

//private const val DATABASE_NAME = "yoga-train-db"
private const val DATABASE_NAME = "sqlite-2"

@Dao
interface TrainHistoryDao {

    @Query("SELECT * FROM Train")
    fun getTrainHistory(): Flow<List<TrainHistory>>

    @Query("SELECT * FROM Train WHERE id = :id")
    suspend fun getTrainHistoryById(id: UUID): TrainHistory
}


class TrainHistoryRepository private constructor(context: Context) {

    private val database: TrainHistoryDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TrainHistoryDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getTrainHistory(): Flow<List<TrainHistory>> {
        return database.trainHistoryDao().getTrainHistory()
    }

    suspend fun getTrainHistoryById(id: UUID): TrainHistory {
        return database.trainHistoryDao().getTrainHistoryById(id)
    }

    companion object {
        private var INSTANCE: TrainHistoryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TrainHistoryRepository(context)
            }
        }

        fun get(): TrainHistoryRepository {
            return INSTANCE ?:
            throw IllegalStateException("TrainHistoryRepository must be initialized")
        }
    }
}


@Database(entities = [ TrainHistory::class ], version=1)
@TypeConverters(TrainTypeConverters::class)
abstract class TrainHistoryDatabase : RoomDatabase() {
    abstract fun trainHistoryDao(): TrainHistoryDao
}


class TrainTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}