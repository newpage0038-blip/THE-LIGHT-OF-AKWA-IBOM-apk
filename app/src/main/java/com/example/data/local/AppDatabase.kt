package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Announcement
import com.example.data.model.Business
import com.example.data.model.CommunityEvent
import com.example.data.model.CommunityPoll
import com.example.data.model.CommunityPost
import com.example.data.model.CultureItem
import com.example.data.model.Opportunity
import com.example.data.model.PostComment
import com.example.data.model.UserProfile

@Database(
    entities = [
        Announcement::class,
        CommunityPost::class,
        PostComment::class,
        CommunityPoll::class,
        CultureItem::class,
        Opportunity::class,
        Business::class,
        CommunityEvent::class,
        UserProfile::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "light_of_akwa_ibom.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
