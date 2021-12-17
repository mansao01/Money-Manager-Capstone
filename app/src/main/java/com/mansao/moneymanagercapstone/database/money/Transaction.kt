package com.mansao.moneymanagercapstone.database.money

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title_transaction")
    var title_transaction: String? = null,

    @ColumnInfo(name = "description_transaction")
    var desc_transaction: String? = null,

    @ColumnInfo(name = "date_transaction")
    var date_transaction: String? = null,

    @ColumnInfo(name = "income")
    var income: String? = null,

    @ColumnInfo(name = "outcome")
    var outcome: String? = null,

    @ColumnInfo(name = "type_transaction")
    var typeTransaction: String? = null
) : Parcelable