package server.networking.login;

import server.model.create.CreateHandler;
import shared.networking.serverInterfaces.CreateAccountServer;
import shared.utils.User.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CreateServerImpl implements CreateAccountServer {

   private CreateHandler createHandler;

    public CreateServerImpl(CreateHandler createHandler) throws RemoteException {
        this.createHandler = createHandler;
        UnicastRemoteObject.exportObject(this,0);
    }






    @Override
    public String addUser(User user) throws RemoteException {
        return createHandler.addUser(user);
    }
}
