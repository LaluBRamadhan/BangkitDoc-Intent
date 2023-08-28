package com.code.myintentapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnClickListener{

    private lateinit var tvResult:TextView

    private val resultLauncher= registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result -> if(result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null){
            val selectedValue =
                 result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0)
            tvResult.text = "Hasil $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener(this)

        //mengirim data dengan explisit intent
        val btnMoveActivityWithData: Button = findViewById(R.id.btn_move_activity_data)
        btnMoveActivityWithData.setOnClickListener(this)

        //mengirim data dengan parcelable
        val btnMoveWithObject:Button = findViewById(R.id.btn_move_activity_object)
        btnMoveWithObject.setOnClickListener(this)

        //mengirim data dengan implisit intent
        val btnDialPhone: Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener(this)

        //mendapat nilai balik dari intent
        val btnMoveForREsult:Button = findViewById(R.id.btn_move_result)
        btnMoveForREsult.setOnClickListener(this)

        tvResult = findViewById(R.id.tv_result)

        //metode biasa yang saya pakai
//        btnMoveActivity.setOnClickListener{
//            val intent = Intent(this, MoveActivity::class.java)
//            startActivity(intent)
//        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_move_activity -> {
                val intent = Intent(this, MoveActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_move_activity_data -> {
                val moveWithDataIntent = Intent(this, MoveWithDataActivity::class.java)
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME,"Dicodings Boy")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE,5)
                startActivity(moveWithDataIntent)
            }

            R.id.btn_move_activity_object -> {
                val person = Person(
                    "Dicoding Academy",
                    5,
                    "Bangkit@academy.id",
                    "Lombok",
                )
                val moveWithObjectIntent = Intent(this, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                startActivity(moveWithObjectIntent)
            }

            R.id.btn_dial_number -> {
                val phoneNumber = "081210841382"
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phoneNumber"))
                startActivity(dialPhoneIntent)
            }

            R.id.btn_move_result -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }

}