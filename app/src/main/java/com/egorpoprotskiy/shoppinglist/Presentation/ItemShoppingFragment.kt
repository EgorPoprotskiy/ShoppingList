package com.egorpoprotskiy.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.egorpoprotskiy.shoppinglist.Domain.ListShopping
import com.egorpoprotskiy.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class ItemShoppingFragment(
    //4.2 Вставляем нужные параметры в конструктор фрагмента и делаем их val
    //4.2 Переменная, в которую будет сохранятся проверка(по-умолчанию - пустая строка)
    private val screenMode: String = MODE_UNKNOW,
    //4.2 Переменная, которая будет хранить ID(по-усолчанию = -1)
    private val itemShoppingId: Int = ListShopping.ID_NOTFOUND
): Fragment() {

    //3.5.3 Создание ссылки на ViewModel
    private lateinit var viewModel: ItemShoppingViewModel

    //3.5.1 Добавление ссылок на элементы из макета
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button


    //Метод onCreateView нужен, чтобы из макета создать View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_shopping, container, false)
    }

    //Метод onViewCreated вызывается когда View точно уже будет создано
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //3.5.4 Вызов функции, проверяющей полученный интент, и получение ID
        parseParams()
        //3.5.3 Инициалицация ссылки на ViewModel
        viewModel = ViewModelProvider(this)[ItemShoppingViewModel::class.java]
        //3.5.2 Инициализация переменных
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        //3.5.6 Ошибка в поле Count
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        //3.5.6 Ошибка в поле Name
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        //3.5.6 Если работа с экраном завершена
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            finish()
        }
    }

    private fun addTextChangeListeners() {
        //3.5.6 Отслеживание поля Name, в режиме редактировния, чтобы появлялась ошибка
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        //3.5.6 Отслеживание поля Count, в режиме редактировния, чтобы появлялась ошибка
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    //3.5.5 Открытие нужного экрана в зависимости от полученного интента
    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    //3.5.5 Октрытие экрана редактирования элемента
    private fun launchEditMode() {
        //3.5.5 Получение элемента из ViewModel
        viewModel.getItemShopping(itemShoppingId)
        //3.5.5 Отслеживание и передача в элемент полей Name и Count
        viewModel.itemShopping.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        //3.5.5 При нажатии кнопки "сохранить", вызывается метод редактирования объекта из ViewModel
        buttonSave.setOnClickListener {
            viewModel.editItemShopping(etName.text?.toString(), etCount.text?.toString())
        }
    }
    //3.5.5 Октрытие экрана добавления элемента
    private fun launchAddMode() {
        //3.5.5 При нажатии кнопки "сохранить", вызывается метод добавления объекта из ViewModel.
        // Поля Name и Count заполнять не надо т.к. они должны быть пустыми при добавлении нового элемента
        buttonSave.setOnClickListener {
            viewModel.addItemShopping(etName.text?.toString(), etCount.text?.toString())
        }
    }

    //4.2 Проверка, что все необходимые параметры были переданы, в противном случае будет исключение
    private fun   parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Param screen mode is absent")
        }
        //4.2 Проверка, если screenMode == MODE_EDIT и если ID элемента не определён
        if (screenMode == MODE_EDIT && itemShoppingId == ListShopping.ID_NOTFOUND) {
            throw java.lang.RuntimeException("Param shop item id is absent")
        }
    }

    //4.2.4 Инициализация переменных
    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
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