package com.egorpoprotskiy.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.parseIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
import com.egorpoprotskiy.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout


class ItemShoppingActivity : AppCompatActivity() {

    //3.5.4 Переменная, в которую будет сохранятся проверка(по-умолчанию - пустая строка)
    private var screenMode = MODE_UNKNOW
    //3.5.4 Переменная, которая будет хранить ID(по-усолчанию = -1)
    private var itemShoppingId = ListShopping.ID_NOTFOUND

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_shopping)
        //3.5.4 Вызов функции, проверяющей полученный интент, и получение ID
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    //4.3 Открытие нужного фрагмента в зависимости от полученного значения
    private fun launchRightMode() {
    val fragment = when (screenMode) {
            MODE_EDIT -> ItemShoppingFragment.newInstanceEditItem(itemShoppingId)
            MODE_ADD -> ItemShoppingFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknow screen mode $screenMode")
        }
    //4.3 Запуск фрагмента
    supportFragmentManager.beginTransaction().replace(R.id.item_shopping_container, fragment).commit()
    }

    //3.5.4 Проверка, что все необходимые параметры были переданы, в противном случае будет исключение
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        // Проверка, если переданные параметры не равны ни одному из режимов MODE_ADD или MODE_EDIT
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknow screen mode $mode")
        }
        //3.5.4 Сохраняем проверку в переменную
        screenMode = mode
        //3.5.4 Проверка, если screenMode == MODE_EDIT
        if (screenMode == MODE_EDIT) {
            //если при этом НЕ содержит в себе ID, то будет исключение
            if (!intent.hasExtra(EXTRA_ITEM_SHOPPING_ID)) {
                throw java.lang.RuntimeException("Param shop item id is absent")
            }
            //получение ID из интента
            itemShoppingId = intent.getIntExtra(EXTRA_ITEM_SHOPPING_ID, ListShopping.ID_NOTFOUND)
        }
    }

    companion object {
        // 3.4 Создание констант для интентов
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_ITEM_SHOPPING_ID = "extra_item_shopping_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOW = ""
        //3.4 Функция для вызова интента для добавления нового элемента
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ItemShoppingActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
        //3.4 Функция для вызова интента для редактировнаия элемента
        fun newIntentEditItem(context: Context, itemShoppingId: Int): Intent {
            val intent = Intent(context, ItemShoppingActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_SHOPPING_ID, itemShoppingId)
            return intent
        }
    }
}