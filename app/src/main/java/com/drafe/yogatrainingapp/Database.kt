package com.drafe.yogatrainingapp

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.drafe.yogatrainingapp.asana.Asana
import com.drafe.yogatrainingapp.asana.Category
import com.drafe.yogatrainingapp.history.TrainHistory
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import java.util.concurrent.Executors

//private const val DATABASE_NAME = "yoga-train-db"
private const val DATABASE_NAME = "sqlite-15"

data class TrainHistoryWithAsana(
    val id: UUID,
    val asanaId: UUID,
    val date: Date = Date(),
    val score: Int = 50,
    val expertCom: String = "Good",
    val asanaName: String? = "",
    val category: String?= "",
    val difficulty: Int? = 1,
    val imgName: String?= ""

)
@Dao
interface TrainHistoryDao {

    @Query("""
        SELECT t.*, a.nameEng as asanaName
        FROM Train t
        LEFT JOIN Asana a ON t.asanaId = a.id
    """)
    fun getTrainHistory(): Flow<List<TrainHistoryWithAsana>>

    @Query("""
        SELECT t.*, a.nameEng as asanaName, a.imgName, a.difficulty, c.category as category
        FROM Train t
        LEFT JOIN Asana a ON t.asanaId = a.id
        LEFT JOIN Category c ON a.categoryId = c.id
        WHERE t.id = (:id)
    """)
    suspend fun getTrainHistoryById(id: UUID): TrainHistoryWithAsana
}
//@Dao
//interface TrainHistoryDao {
//
//    @Query("SELECT * FROM Train")
//    fun getTrainHistory(): Flow<List<TrainHistory>>
//
//    @Query("SELECT * FROM Train WHERE id = (:id)")
//    suspend fun getTrainHistoryById(id: UUID): TrainHistory
//}

data class AsanaWithCategory (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val nameEng: String = "Asana",
    val nameHin: String = "Asana",
    val imgName: String? = "Good",
    val description: String = "Asana",
    val pose: String? = "Asana",
    val difficulty: Int? = 1,
    val categoryId:  UUID? = null,
    val categoryName: String? = null
)
@Dao
interface AsanaDao {
    @Query("""
        SELECT a.*, c.category as categoryName
        FROM Asana a
        LEFT JOIN Category c ON a.categoryId = c.id
    """)
    fun getAllAsanas(): Flow<List<AsanaWithCategory>>

    @Query("""
        SELECT a.*, c.category as categoryName
        FROM Asana a
        LEFT JOIN Category c ON a.categoryId = c.id
        WHERE a.id = (:id)
    """)
    suspend fun getAsanaById(id: UUID): AsanaWithCategory

    @Query("SELECT nameEng FROM Asana WHERE id = (:id)")
    suspend fun getAsanaNameById(id: UUID): String
}
//@Dao
//interface AsanaDao {
//    @Query("SELECT * FROM Asana")
//    fun getAllAsanas(): Flow<List<Asana>>
//
//    @Query("SELECT * FROM Asana WHERE id = (:id)")
//    suspend fun getAsanaById(id: UUID): Asana
//
//    @Query("SELECT nameEng FROM Asana WHERE id = (:id)")
//    suspend fun getAsanaNameById(id: UUID): String
//}
@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM Category WHERE id = (:id)")
    suspend fun getCategoryById(id: UUID): Category
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

    fun getTrainHistory(): Flow<List<TrainHistoryWithAsana>> {
        return database.trainHistoryDao().getTrainHistory()
    }

    suspend fun getTrainHistoryById(id: UUID): TrainHistoryWithAsana {
        return database.trainHistoryDao().getTrainHistoryById(id)
    }

    fun getAsanas(): Flow<List<AsanaWithCategory>> {
        return database.asanaDao().getAllAsanas()
    }

    suspend fun getAsanaById(id: UUID): AsanaWithCategory {
        return database.asanaDao().getAsanaById(id)
    }

    suspend fun getAsanaNameById(id: UUID): String {
        return database.asanaDao().getAsanaNameById(id)
    }

    fun getCategories(): Flow<List<Category>> {
        return database.categoryDao().getAllCategories()
    }

    suspend fun getCategoryById(id: UUID): Category {
        return database.categoryDao().getCategoryById(id)
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


@Database(entities = [ TrainHistory::class, Asana::class, Category::class ], version=1)
@TypeConverters(YogaTypeConverters::class)
abstract class YogaAppDatabase : RoomDatabase() {
    abstract fun trainHistoryDao(): TrainHistoryDao

    abstract fun asanaDao(): AsanaDao

    abstract fun categoryDao(): CategoryDao
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