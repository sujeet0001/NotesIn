package com.mynotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mynotes.R
import kotlinx.android.synthetic.main.passcode.*

class PassCode : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.passcode)

        pc_0.setOnClickListener (this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.pc_0 -> Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
    }
}
