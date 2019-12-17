package com.nacl.android.Model;

public class LoginSignup{
    public String error;
    public boolean checkEmail(String email){
        error = "Email cannot be empty.";
        if (email != null){
            if (email.isEmpty()) {
                error = "Email cannot be empty.";
                return true;
            }
            else if (email.contains(" ")) {
                error = "Email cannot contain space";
                return true;
            }else if (!(email.contains("@") && email.contains("."))) {
                error = "Email must contain . and @ for it to be valid.";
                return true;
            }else
                return false;
        }else
            return true;
    }
    public boolean checkUsername(String username){
        error = "Username cannot contain spaces.";
        if (username != null)
            return (username.contains(" "));
        else
            return true;
    }
    public boolean checkLoginPassword(String pass){
        if ((pass !=null)) {
            if (pass.isEmpty()) {
                error = "Password cannot be empty.";
                return true;
            }else
                return false;
        }else
            return true;
    }
    public boolean checkPassword(String pass,String conf_pass){

        if ((pass !=null) && (conf_pass != null)){
            if (pass.isEmpty() || conf_pass.isEmpty()) {
                error = "Password or Confirm password is empty.";
                return true;
            }else if (!pass.equals(conf_pass)){
                error = "Password don't match";
                return true;
            }else if (pass.length() < 8 || conf_pass.length() < 8 ){
                error = "Password and Confirm password length must be grater than 8 characters";
                return true;
            }else
                return false;
        }else
            return true;
    }

}
