package com.example.kit1_08_kotlin_toast

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.kit1_08_kotlin_toast.R.string.*
import com.example.kit1_08_kotlin_toast.R.drawable.*
import com.example.kit1_08_kotlin_toast.R.layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showToast1(view: View) {
        Toast.makeText(applicationContext, "Пора вставать! Утро!", Toast.LENGTH_SHORT).show()

        //.gravity(Gravity.CENTER,0,0)
        // .setGravity(Gravity.CENTER,0,0)
        // .show()



    }
    fun showToast2(view: View) {
        val duration = Toast.LENGTH_LONG;
        val toast :  Toast
        toast = Toast.makeText(applicationContext,
            //    R.string.show_toast,
            show_toast,
            duration);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
    // Добавляем картинку
    fun showToast3(view: View) {
        val toast = Toast.makeText(applicationContext,show_toast, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val toastContainer = toast.view as LinearLayout
        val catImageView = ImageView(applicationContext)
        catImageView.setImageResource(soley)
        //ImageView(applicationContext).setImageResource(soley)
        //(toast.view as LinearLayout).addView(catImageView, 0)
        toastContainer.addView(catImageView, 0)
        toast.show()
    }
    // Создание собственных всплывающих уведомлений
    fun showToast4(view: View) {
        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layoutInflater.inflate(custom_layout,toast_layout)
        toast.show()
    }

    fun showToast_thread(view: View) {
        mainProcessing()
    }

    // Использование уведомлений Toast в рабочих потоках
    // Запустил работает не понял
    private fun mainProcessing() {
        val thread = Thread(null, doBackgroundThreadProcessing,
            "Background")
        thread.start()
    }

    private val doBackgroundThreadProcessing = Runnable { backgroundThreadProcessing() }

    private fun backgroundThreadProcessing()  {
        //val handler = Handler(Looper.getMainLooper())  // DO
        //return;
        //handler.post(doUpdateGUI)
        Handler(Looper.getMainLooper()).post(doUpdateGUI)
    }

    // Объект Runnable, который вызывает метод из потока GUI
    private val doUpdateGUI = Runnable {
        val context = applicationContext
        val msg = "Открыли мобильную разработку! из потока"
        val duration = Toast.LENGTH_LONG
        Toast.makeText(context, msg, duration).show()
    }
    /*Как элемент графического интерфейса Toast должен быть вызван в потоке GUI,
    иначе существует риск выброса межпотокового исключения.
    В листинге объект Handler используется для гарантии того,
    что уведомление Toast было вызвано в потоке GUI.
    */
}
/*
Напоследок хочу предупредить об одной потенциальной проблеме.
При вызове сообщения нужно указывать контекст в первом параметре метода makeText().
В интернете и, возможно и у меня на сайте будет попадаться пример makeText(MainActivity.this, ...).
Ошибки в этом нет, так как класс Activity является потомком Context
и в большинстве случаев пример будет работать.
Но иногда я получаю письма от пользователей, которые жалуются на непонятное поведение сообщения,
когда текст не выравнивается, обрезается и т.д.
Это связано с тем, что активность может использовать определённую тему или стиль,
которые вызывают такой побочный эффект.
Поэтому я рекомендую вам использовать метод getApplicationContext().

Второй момент - фрагменты, которые будете изучать позже, не являются потомками контекста.
Если вы захотите вызвать всплывающее сообщение в фрагменте, то проблема может поставить вас в тупик.
Вам нужно добавить новую переменную класса Activity через метод getActivity():


Activity activity = getActivity();
Toast.makeText(activity, "Кота покормили?", Toast.LENGTH_SHORT).show();

Такое же может случиться при вызове всплывающих сообщений из диалоговых окон,
которые тоже не относятся к классу Context.
Вместо getApplicationContext() также можно вызывать метод getBaseContext().
*/