package com.stefan.mykotlinproject

import android.app.Fragment
import android.app.FragmentManager
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.stefan.mykotlinproject.Models.UpdateFragment
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : AppCompatActivity(),
        MessageFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener{


    val message_tag: String = MessageFragment::class.java.simpleName
    val camera_tag: String = CameraFragment::class.java.simpleName
    val profile_tag: String = ProfileFragment::class.java.simpleName

    val tags = listOf(message_tag,camera_tag,profile_tag)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)


        if(fragment_container!=null){
            if(savedInstanceState!=null){
                return
            }

            // Create a new Fragment to be placed in the activity layout
            val fragment = getFragment(intent.getStringExtra("fragment"))

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            fragment?.arguments = intent.extras

            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentManager.beginTransaction().add(fragment_container.id, fragment).commit()

        }
    }

    private fun replaceFragment(fragmentName: String, args: Bundle?=null): Unit{
        val fragment = getFragment(fragmentName)
        fragment?.arguments = args
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_right,R.animator.slide_out_left,
                        R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(fragment_container.id, fragment)
                .addToBackStack(null)
                .commit()

    }


    private fun getFragment(fragmentName: String): Fragment? {
        Log.d(localClassName,"getFragment: "+fragmentName)
        when(fragmentName){
            message_tag -> return MessageFragment()
            camera_tag -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                return CameraFragment()
            }
            profile_tag -> return ProfileFragment()
            else -> return null
        }
    }


    override fun onFragmentInteraction(bundle: Bundle?) {
        if(bundle?.get("fragment")!=null){
            replaceFragment(bundle.getString("fragment"))
        } else if (bundle?.get("image")!=null){
            fragmentManager.popBackStack()
            fragmentManager.addOnBackStackChangedListener {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                updateFragment(bundle)
            }
        }
    }

    private fun updateFragment(bundle: Bundle?) {
        val fragment = fragmentManager.findFragmentById(fragment_container.id)
        if(fragment is UpdateFragment) fragment.updateInstance(bundle)
    }

}
