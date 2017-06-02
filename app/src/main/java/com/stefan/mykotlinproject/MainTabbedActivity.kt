package com.stefan.mykotlinproject

import android.content.Intent
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_tabbed.*

class MainTabbedActivity : AppCompatActivity(), FeedFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)

        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        view_pager.adapter = mSectionsPagerAdapter


        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.setupWithViewPager(view_pager)
        tablayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main_tabbed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out -> signOut()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        mAuth.signOut()
        navigateToLogin()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if(currentUser==null){
            navigateToLogin()
        } else{
            // TODO set UI
        }
    }

    private fun navigateToLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItemPosition(`object`: Any?): Int {
            return super.getItemPosition(`object`)
        }

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when(position){
                0 -> return FeedFragment.newInstance()
                1 -> return FriendsFragment.newInstance()
                else -> return FeedFragment.newInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            when(position){
                0 -> return "Feed"
                1 -> return "Squad"
            }
            return super.getPageTitle(position)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }
}
