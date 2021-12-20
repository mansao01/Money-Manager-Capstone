package com.mansao.moneymanagercapstone.database

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

    @ColumnInfo(name ="title_money")
    var titleMoney: String? = null,

    @ColumnInfo(name = "description_money")
    var descMoney: String? = null,

    @ColumnInfo(name = "date_money")
    var dateMoney: String? = null,

) : Parcelable