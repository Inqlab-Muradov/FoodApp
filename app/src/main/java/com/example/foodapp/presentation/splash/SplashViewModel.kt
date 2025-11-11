package com.example.foodapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel@Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _isLogin = Channel<Boolean>()
    val isLogin = _isLogin.receiveAsFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    init {
        checkLogin()
    }

    private fun checkLogin(){
       val currentUser =  firebaseAuth.currentUser
        if(currentUser != null ){
            viewModelScope.launch {
                _email.value = currentUser.email.orEmpty()
                _isLogin.send(true)
            }
        }
    }
}