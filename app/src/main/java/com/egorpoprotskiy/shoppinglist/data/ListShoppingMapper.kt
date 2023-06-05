package com.egorpoprotskiy.shoppinglist.data

import com.egorpoprotskiy.shoppinglist.domain.ListShopping

class ListShoppingMapper {
    //метод, преобразует сущность domain-слоя в сущность data-слоя
    fun mapEntityToDbModel(listShopping: ListShopping): ListShoppingDbModel {
        return ListShoppingDbModel(
            id = listShopping.id,
            name = listShopping.name,
            count = listShopping.count,
            active = listShopping.active
        )
    }
    //метод, преобразует в обратном порядке
    fun mapDbModelToEntity(listShoppingDbModel: ListShoppingDbModel): ListShopping {
        return ListShopping(
            id = listShoppingDbModel.id,
            name = listShoppingDbModel.name,
            count = listShoppingDbModel.count,
            active = listShoppingDbModel.active
        )
    }
    //преобразет список объектов ListShoppingDbModel в список ListShopping
    fun mapListDbModelToListEntity(list: List<ListShoppingDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}