package com.stefan.mykotlinproject

import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity


import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_tabbed.*
import kotlinx.android.synthetic.main.app_bar_navigation.*

class MainTabbedActivity : AppCompatActivity(),
        FeedFragment.OnFragmentInteractionListener,
        FriendsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)

        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(fragmentManager)

        // Set up the ViewPager with the sections adapter.
        view_pager.adapter = mSectionsPagerAdapter


        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.setupWithViewPager(view_pager)
        tablayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val headerview = navigationView.getHeaderView(0)
        val header = headerview.findViewById(R.id.nav_header) as LinearLayout
        header.setOnClickListener {
            val intent = Intent(baseContext,SimpleActivity::class.java).
                    putExtra("fragment",ProfileFragment::class.java.simpleName)
            startActivity(intent) }

        nav_view.setNavigationItemSelectedListener(this)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_navigation_drawer, menu)
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(v: View) {

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        Toast.makeText(baseContext,"SOMETHING",Toast.LENGTH_SHORT).show()
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
                Toast.makeText(baseContext,"CAMERA",Toast.LENGTH_SHORT).show()
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
