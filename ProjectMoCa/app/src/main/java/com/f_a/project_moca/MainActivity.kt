package com.f_a.project_moca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.f_a.project_moca.ui.fragment.AdminFragment
import com.f_a.project_moca.ui.fragment.FavouriteFragment
import com.f_a.project_moca.ui.fragment.MovieFragment
import com.f_a.project_moca.ui.fragment.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateFragment(MovieFragment())
        nav_bottom.setOnItemSelectedListener { id ->
            when(id){
                R.id.movie -> navigateFragment(MovieFragment())
                R.id.tv_show -> navigateFragment(TvShowFragment())
                R.id.favourite -> navigateFragment(FavouriteFragment())
                R.id.admin -> navigateFragment(AdminFragment())
            }
        }
    }

    private fun navigateFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
