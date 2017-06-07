package com.stefan.mykotlinproject

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.stefan.mykotlinproject.Models.UpdateFragment
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.view.*


class MessageFragment : UpdateFragment(), View.OnClickListener {



    private var mListener: OnFragmentInteractionListener? = null
    private var mImageString: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view?.takePhoto?.setOnClickListener(this)
        view?.download_image?.setOnClickListener(this)
        view?.show_hide?.setOnClickListener(this)
        view?.rotate_right?.setOnClickListener(this)
        view?.rotate_left?.setOnClickListener(this)
        view?.remove_image?.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val message = editText?.text?.toString()
        Log.d(tag,"onViewStateRestored "+message)
        outState?.putString("message",message)
        if(mImageString!=null) outState?.putString("image",mImageString)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val message = savedInstanceState?.get("message") as String?
        editText?.setText(message)
        editText?.requestFocus()
        mImageString = savedInstanceState?.get("image") as String?
        if(mImageString!=null) setImage(BitmapFactory.decodeFile(mImageString!!))
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

    override fun onPause() {
        super.onPause()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(bundle: Bundle?)
    }

    override fun onClick(v: View) {
        when(v.id){
            takePhoto.id -> {
                //TODO start camera activity
                Toast.makeText(context,"take photo",Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("fragment",CameraFragment::class.java.simpleName)
                mListener?.onFragmentInteraction(bundle)
            }
            download_image.id -> {}
            rotate_right.id -> {
                val drawable = imageView.drawable as BitmapDrawable
                setImage(CameraFragment.rotateImage(drawable.bitmap,90F))
            }
            rotate_left.id -> {
                val drawable = imageView.drawable as BitmapDrawable
                setImage(CameraFragment.rotateImage(drawable.bitmap,270F))
            }
            show_hide.id -> toggleView(imageView)
            remove_image.id -> removeImage()
        }
    }

    private fun toggleView(imageView: ImageView) {
        if(imageView.visibility == View.VISIBLE){
            imageView.visibility=View.GONE
            show_hide_img.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp))
        } else{
            imageView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down_top))
            imageView.visibility = View.VISIBLE
            show_hide_img.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp))
        }
    }

    private fun removeImage() {
        AlertDialog.Builder(context)
                .setTitle("Are You Sure?")
                .setMessage("Clicking 'Yes' will delete the photo")
                .setPositiveButton("Yes",{ _, _ ->
                    mImageString = null
                    imageView.setImageDrawable(null)
                    image_container?.visibility = View.GONE
                })
                .setNegativeButton("No",null)
                .show()

    }

    override fun updateInstance(bundle: Bundle?){
        mImageString= bundle?.get("image") as String?
        if(mImageString!=null) {
            setImage(CameraFragment.checkOrientation(mImageString!!))
        }
    }

    fun setImage(bm: Bitmap){
        image_container.visibility = View.VISIBLE
        imageView.setImageBitmap(bm)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
