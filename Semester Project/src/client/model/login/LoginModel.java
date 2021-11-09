package client.model.login;

import shared.utils.User;

public interface LoginModel {
    boolean isConnectionPossible(String username);
    String isLoginPossible(String username,String password);
}