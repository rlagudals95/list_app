package com.example.chapter1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "memo")
class MemoEntity (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var memo:String = "", )