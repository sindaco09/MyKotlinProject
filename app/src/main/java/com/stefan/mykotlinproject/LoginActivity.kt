package com.stefan.mykotlinproject

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var imm: InputMethodManager
    lateinit var mAuth: FirebaseAuth
    lateinit var mCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()

        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(localClassName, "facebook:onSuccess:" + loginResult)
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(localClassName, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(localClassName, "facebook:onError", error)
                // ...
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mCallbackManager.onActivityResult(requestCode,resultCode, data)
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful){
                        val user = mAuth.currentUser
                        Log.d(localClassName,"signInWithCredential: success: "+user?.displayName)
                        navigateToDashboard()
                    }else{
                        Log.w(localClassName,"signInWithCredential: failure", task.exception)
                        Toast.makeText(baseContext,"Authentication failed.",Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.continue_btn -> {
                // Open viewflipper 2
                viewFlipper.setInAnimation(this,R.anim.slide_up)
                viewFlipper.setOutAnimation(this,R.anim.slide_down)
                viewFlipper.displayedChild = 2
                username_et.requestFocus()
                imm.showSoftInput(username_et,InputMethodManager.SHOW_IMPLICIT)
            }
            R.id.signup_btn -> {
                // Open viewflipper 1
                viewFlipper.displayedChild = 1
                signup_username.requestFocus()
                imm.showSoftInput(signup_username,InputMethodManager.SHOW_IMPLICIT)
            }
            R.id.forgot_password -> Toast.makeText(this,"That Sucks!",Toast.LENGTH_SHORT).show()
            R.id.signin_btn -> {
                val email = username_et.text.toString()
                val password = password_et.text.toString()
                if(validInput(email,password)) signInUser(email,password)
            }
            R.id.proceed_btn -> {
                val email = signup_username.text.toString()
                val password = signup_password.text.toString()
                val passwordConfirm = signup_password_confirm.text.toString()
                if(validInput(email,password,passwordConfirm)) signUpUser(email,password)
            }
        }
    }

    private fun  signInUser(email: String, password: String): Unit {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener("signInUser"))
    }

    private fun signUpUser(email: String, password: String): Unit {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(listener("signUpUser"))
    }

    private fun listener(message: String): OnCompleteListener<AuthResult>{
        return OnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(localClassName, message+":success")
                navigateToDashboard()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(localClassName, message+":failure", task.exception)
                Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                // updateUI(null)
            }

        }
    }

    private fun navigateToDashboard(){
        val intent = Intent(this,MainTabbedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun  validInput(email: String, password: String, passwordConfirm: String=password): Boolean {
        if(email.isEmpty()){
            Toast.makeText(this,"Please enter an email address",Toast.LENGTH_SHORT).show()
            return false
        } else if (password.isEmpty()){
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show()
            return false
        } else if(passwordConfirm.isEmpty() || passwordConfirm != password){
            Toast.makeText(this,"Please confirm password",Toast.LENGTH_SHORT).show()
            return false
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter a valid email address",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onBackPressed() {
        if(viewFlipper.displayedChild != 0){
            viewFlipper.displayedChild = 0
        } else {
            super.onBackPressed()
        }
    }
}
