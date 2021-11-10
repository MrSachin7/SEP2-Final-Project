package client.core;

import client.model.create.CreateImpl;
import client.model.create.CreateModel;
import client.model.login.LoginModel;
import client.model.login.LoginModelImpl;

/**
 * @author Sachin Baral
 * The class that creates and stores all models
 */
public class ModelFactory {
    private ClientFactory clientFactory;
    private LoginModel loginModel;
    private CreateModel createModel;

    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory=clientFactory;
    }

    public LoginModel getLoginModel() {
        if (loginModel == null) {
            loginModel = new LoginModelImpl(clientFactory.getLoginClient());
        }
        return loginModel;
    }

    public CreateModel getCreateModel() {
        if (createModel == null) {
            createModel = new CreateImpl(clientFactory.getCreateClient());
        }
        return createModel;
    }
}
