package com.egorpoprotskiy.shoppinglist.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

class ItemShoppingFragment: Fragment() {

    //3.5.3 Создание ссылки на ViewModel
    private lateinit var viewModel: ItemShoppingViewModel
    //4.6.3 Создаем переменную интерфейсного типа
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    //3.5.1 Добавление ссылок на элементы из макета
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    //4.2 Переменная, в которую будет сохранятся проверка(по-умолчанию - пустая строка)
    private var screenMode: String = MODE_UNKNOW
    //4.2 Переменная, которая будет хранить ID(по-усолчанию = -1)
    private var itemShoppingId: Int = ListShopping.ID_NOTFOUND

    // 4.6.6 Переопределить метод onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

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
            //4.6.4 Вызваем функцию, созданную в активити
            onEditingFinishedListener.onEditingFinished()
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

    //4.4.5 Получение переданных аргументов
    private fun   parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        // Проверка, если переданные параметры не равны ни одному из режимов MODE_ADD или MODE_EDIT
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknow screen mode $mode")
        }
        //3.5.4 Сохраняем проверку в переменную
        screenMode = mode
        //3.5.4 Проверка, если screenMode == MODE_EDIT
        if (screenMode == MODE_EDIT) {
            //если при этом НЕ содержит в себе ID, то будет исключение
            if (!args.containsKey(ITEM_SHOPPING_ID)) {
                throw java.lang.RuntimeException("Param shop item id is absent")
            }
            //получение ID из интента
            itemShoppingId = args.getInt(ITEM_SHOPPING_ID, ListShopping.ID_NOTFOUND)
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
    //4.6.2 Создание интерфейса для обеспечения связи между активити и фрагментом
    interface  OnEditingFinishedListener {
        fun onEditingFinished()
    }
    companion object {
        // 3.4 Создание констант для интентов
        private const val SCREEN_MODE = "extra_mode"
        private const val ITEM_SHOPPING_ID = "extra_item_shopping_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOW = ""

        //4.3 Фабричные и статичные методы для создания экземпляра фрагмента в активити
        fun newInstanceAddItem(): ItemShoppingFragment {
            //4.4 Передача параметров во фрагментах
            return ItemShoppingFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }}
        fun newInstanceEditItem(itemShoppingId: Int): ItemShoppingFragment {
            return ItemShoppingFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_SHOPPING_ID, itemShoppingId)
                }
            }
        }
        //3.4 Функция для вызова интента для добавления нового элемента
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ItemShoppingActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }
        //3.4 Функция для вызова интента для редактировнаия элемента
        fun newIntentEditItem(context: Context, itemShoppingId: Int): Intent {
            val intent = Intent(context, ItemShoppingActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(ITEM_SHOPPING_ID, itemShoppingId)
            return intent
        }
    }
}