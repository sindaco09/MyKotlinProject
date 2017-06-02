package com.stefan.mykotlinproject

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.stefan.mykotlinproject.Adapters.FeedRAdapter
import com.stefan.mykotlinproject.Models.FeedItem
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(),View.OnClickListener {

    private var mListener: OnFragmentInteractionListener? = null
    val MESSAGE_CODE = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_feed, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onResume() {
        super.onResume()
        val list: MutableList<FeedItem> = ArrayList()
        (0..3).mapTo(list) { FeedItem(id= it.toString()) }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FeedRAdapter(context,list = list)


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(v: View) {
        when(v.id){
            fab.id -> startActivityForResult(Intent(context,MessageActivity()::class.java),MESSAGE_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==MESSAGE_CODE && resultCode == RESULT_OK){
            // TODO: do something with result
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        fun newInstance(): FeedFragment {
            val fragment = FeedFragment()
            return fragment
        }
    }
}
