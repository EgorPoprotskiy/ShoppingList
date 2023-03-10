package com.egorpoprotskiy.shoppinglist.Domain

//1 Добавление Domain-слоя

data class ListShopping(val name: String, val count: Int, val value: Boolean, var id: Int = ID_NOTFOUND) {

    companion object {
        const val ID_NOTFOUND = -1
    }
}