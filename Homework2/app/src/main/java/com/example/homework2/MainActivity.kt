package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework2.fragments.FirstFragment
import com.example.homework2.fragments.SecondFragment
import com.example.homework2.fragments.ThirdFragment
import com.example.homework2.interfaces.ActivityFragmentCommunication
import com.example.homework2.models.Album
import com.example.homework2.models.User

class MainActivity : AppCompatActivity() , ActivityFragmentCommunication {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFirstFragment()
    }
    private fun addFirstFragment(){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = FirstFragment::class.java.name
        val addTransaction = transaction.add(
                R.id.frame_layout, FirstFragment.newInstance("",""), tag
        )
        addTransaction.commit()
    }

    override fun openSecondFragment(user: User) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = SecondFragment::class.java.name
        val replaceTransaction = transaction.replace(
            R.id.frame_layout, SecondFragment.newInstance("","",user), tag
        )
        replaceTransaction.addToBackStack(tag)
        replaceTransaction.commit()

    }

    override fun openThirdFragment(album: Album) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val tag = ThirdFragment::class.java.name
        val replaceTransaction = transaction.replace(
                R.id.frame_layout, ThirdFragment.newInstance("","",album), tag
        )
        replaceTransaction.addToBackStack(tag)
        replaceTransaction.commit()
    }
}
