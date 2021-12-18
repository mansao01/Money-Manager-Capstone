package com.mansao.moneymanagercapstone.database.money

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Money(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name ="title_note")
    var title_note: String? = null,

    @ColumnInfo(name = "description_note")
    var desc_note: String? = null,

    @ColumnInfo(name = "date_note")
    var date_note: String? = null,

) : Parcelable