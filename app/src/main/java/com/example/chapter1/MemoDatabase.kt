package com.example.chapter1


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 메모 엔티티를 사용하며 사용할 테이블은 1개
@Database(entities = arrayOf(MemoEntity::class), version = 1) 
abstract class MemoDatabase : RoomDatabase() {
    abstract  fun memoDAO() : MemoDao

    // 앱 실행 시 한번만 생성하는 패턴 > 싱글톤 패턴
    companion object {
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase? {
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration() // 데이터 베이스를 한번 생성하고 중간에 엔티티를 수정, 추가를 할때 필요함
                        .build()
                }
            }
            return INSTANCE
        }
    }
}