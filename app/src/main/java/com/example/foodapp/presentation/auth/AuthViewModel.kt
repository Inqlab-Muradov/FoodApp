package com.example.foodapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _errorMessage = Channel<String>()
    val errorMessage = _errorMessage.receiveAsFlow()

    private val _successfulAuth = Channel<Boolean>()
    val successfulAuth = _successfulAuth.receiveAsFlow()

    fun setNewText(label: String, text: String) {
        when (label) {
            "userNameLogin" -> _authState.value = _authState.value.copy(usernameLogin = text)
            "passwordLogin" -> _authState.value = _authState.value.copy(passwordLogin = text)
            "userNameRegister" -> _authState.value = _authState.value.copy(usernameRegister = text)
            "passwordRegister" -> _authState.value = _authState.value.copy(passwordRegister = text)
            "confirmPasswordRegister" -> _authState.value =
                _authState.value.copy(confirmPasswordRegister = text)
        }
    }


    fun register() {
        val state = authState.value
        checkRegisterValidation()
        if (!checkRegisterValidation()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = firebaseAuth.createUserWithEmailAndPassword(
                        state.usernameRegister,
                        state.passwordRegister
                    ).await()
                    withContext(Dispatchers.Main) {
                        _authState.value =
                            _authState.value.copy(userEmail = result.user?.email.orEmpty())
                        _successfulAuth.send(true)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.send(e.message ?: "Registration failed")
                    }
                }
            }
        }
    }

    fun login() {
        val state = authState.value
        checkLoginValidation()
        if (!checkLoginValidation()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = firebaseAuth.signInWithEmailAndPassword(
                        state.usernameLogin,
                        state.passwordLogin
                    ).await()
                    withContext(Dispatchers.Main){
                        _authState.value =
                            _authState.value.copy(userEmail = result.user?.email.orEmpty())
                        _successfulAuth.send(true)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.send(e.message ?: "Login failed")
                    }
                }
            }
        }
    }

    fun checkRegisterValidation(): Boolean {
        val state = authState.value
        if (state.usernameRegister.isEmpty() || state.passwordRegister.isEmpty() || state.confirmPasswordRegister.isEmpty()) {
            viewModelScope.launch {
                _errorMessage.send("Fill all fields")
            }
            return true
        } else if (
            state.usernameRegister.length <= 10 || state.passwordRegister.length < 6 || state.confirmPasswordRegister.length < 6
        ) {
            viewModelScope.launch {
                _errorMessage.send("Increase the number of symbols")
            }
            return true
        } else if (
            state.passwordRegister != state.confirmPasswordRegister
        ) {
            viewModelScope.launch {
                _errorMessage.send("Password and confirmed password aren't the same")
            }
            return true
        } else {
            return false
        }
    }

    fun checkLoginValidation(): Boolean {
        val state = authState.value
        if (state.usernameLogin.isEmpty() || state.passwordLogin.isEmpty()) {
            viewModelScope.launch {
                _errorMessage.send("Fill all fields")
            }
            return true
        } else {
            return false
        }
    }
}