package client.model.login;

import client.networking.login.LoginClient;
import shared.utils.User.User;

/**
 * @author Sachin
 * The model that deals with login in the system
 */
public class LoginModelImpl implements LoginModel{

    private LoginClient client;
    private User user;

    public LoginModelImpl(LoginClient loginClient){
        this.client=loginClient;
    }


    @Override
    public boolean isConnectionPossible(String username) {
        return false;
    }


    @Override
    public String isLoginPossible(String username,String password) {
       User tempUser = new User(username,password);
       String result= client.isLoginPossible(tempUser);
       if (result.equals("Approved")){
           this.user=tempUser;
       }
       return result;
    }
}
