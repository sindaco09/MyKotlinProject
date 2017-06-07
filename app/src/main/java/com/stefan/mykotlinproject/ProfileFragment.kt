package com.stefan.mykotlinproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stefan.mykotlinproject.Models.UpdateFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : UpdateFragment(), View.OnClickListener {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.save?.setOnClickListener(this)
        view?.take_photo?.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            Log.e(tag,"must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(v: View) {
        when(v.id){
            save.id -> Toast.makeText(context,"click",Toast.LENGTH_SHORT).show()
            take_photo.id -> {
                //TODO Start Camera
            }
            camera_roll.id -> {
                //TODO Start Camera Roll
            }
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(bundle: Bundle?)
    }


    override fun updateInstance(bundle: Bundle?){
        Toast.makeText(context,"UPDATE",Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
