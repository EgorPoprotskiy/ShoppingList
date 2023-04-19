package com.egorpoprotskiy.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.shoppinglist.data.ShoppingRepositoryImpl
import com.egorpoprotskiy.shoppinglist.domain.AddItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.EditItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.GetItemShoppingUseCase
import com.egorpoprotskiy.shoppinglist.domain.ListShopping
//3.2.1 Создать класс для ViewModel
class ItemShoppingViewModel: ViewModel() {
    //3.2.2 Создание экземпляров объектов
    private val reposytory = ShoppingRepositoryImpl
    private val getItemShoppingUseCase = GetItemShoppingUseCase(reposytory)
    private val addItemShoppingUseCase = AddItemShoppingUseCase(reposytory)
    private val editItemShoppingUseCase = EditItemShoppingUseCase(reposytory)
    //3.3 Добавляем объекты, куда будем вставлять значения. Если значение true - будет ошибка и наоборот
    // C _errorInputName и _errorInputCount можно работать только в ViewModel
    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    // C errorInputName и errorInputCount можно работать в Activity
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    //3.2.4 Создание объекта LiveData для работы в ViewModel при получении объекта
    private val _itemShopping = MutableLiveData<ListShopping>()
    val itemShopping: LiveData<ListShopping>
        get() = _itemShopping

    //3.2.6 Создание объекта LiveData, который отвечает за завершение работы других методов.
    // Т.к. нам не важно, какое значение мы передадим в activity, то сделаем его Unit
    private val  _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    //3.2.4 Создание функции получения элемента
    fun getItemShopping(itemShoppingId: Int) {
        val item = getItemShoppingUseCase.getItemShopping(itemShoppingId)
        _itemShopping.value = item
    }

    //3.2.3 Создание функции по добавлению элемента. В параметрах указваем вводимые в поля значения(типа String)
    fun addItemShopping(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val itemShopping = ListShopping(name, count, true)
            addItemShoppingUseCase.addItemShopping(itemShopping)
            finishWork()
        }
    }
    //3.2.3 Функция для принимаемых значений. В данном лучаем name.
    private fun parseName(inputName: String?): String {
        //если inputName!=null, тогда возвращаем его, иначе возвращаем пустую строку. Метод trim() убирает пробелы между словами
        return inputName?.trim() ?: ""
    }
    //3.2.3 Функция для принимаемых значений. В данном лучаем count.
    private fun parseCount(inputCount: String?): Int {
        //в это поле вводится строка, которая затем переводится в число. Если нельзя перевести в число, то вернется 0
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: java.lang.Exception) {
            0
        }
    }
    //3.2.3 Функция, проверяющая данные, введенные в поля, на корректность. В этом же методе будет выводится ошибка.
    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            //3.3 Показ ошибки, если поле пустое(true)
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            //3.3 Показ ошибки, если поле пустое(true)
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    //3.3 Метод сброса ошибки для ввода имени, который хранит false для нашего объекта-ошибки.
    fun resetErrorInputName() {
        _errorInputName.value = false
    }
    //3.3 Метод сброса ошибки для ввода количества, который хранит false для нашего объекта-ошибки.
    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    //3.2.5 Создание функции редактирования элемента
    fun editItemShopping(inputName: String?, inputCount: String?) {
        //получаем имя
        val name = parseName(inputName)
        //получаем количество
        val count = parseCount(inputCount)
        //проверяем, что поля введены корректно
        val fieldsValid = validateInput(name, count)
        //если поля введены корректно, то
        if (fieldsValid) {
            //получаем объект из LiveData
            _itemShopping.value?.let {
                //Создаем объект путем копирования уже существующего и устанавливаем у него новые значения name и count
                val item = it.copy(name = name, count = count)
                //Отправляем этот объект в editItemShopping
                editItemShoppingUseCase.editItemShopping(item)
                finishWork()
            }
        }
    }

    //Вынесли общий код в отдельную функцию
    private fun finishWork() {
        //значение устанавливаем Unit, т.к. нам не важно какое значение будет возвращено
        _shouldCloseScreen.value = Unit
    }
}