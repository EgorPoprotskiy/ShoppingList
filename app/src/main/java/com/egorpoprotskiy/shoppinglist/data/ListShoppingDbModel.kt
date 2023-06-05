package com.egorpoprotskiy.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
@Entity(tableName = "list_shopping")
class ListShoppingDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val active: Boolean,
)