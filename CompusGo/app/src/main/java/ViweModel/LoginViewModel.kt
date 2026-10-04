package ViweModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel()  {
    var USN by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var ConfirmPassword by mutableStateOf("")
        private set

    var Name by mutableStateOf("")
        private set

    var Email by mutableStateOf("")
        private set

    var Phone by mutableStateOf("")
        private set
    fun onUSNChange(value: String) {
        USN = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onConfirmPasswordChange(value: String) {
        ConfirmPassword = value
    }
    fun onNameChange(value: String) {
        Name = value
    }
    fun onEmailChange(value: String) {
        Email = value
    }
    fun onPhoneChange(value: String) {
        Phone = value
    }


    fun RegisterUser(){

    }

    fun chakepassword(){
        if(password == ConfirmPassword){
            RegisterUser()
        }
    }
}
