package client.core;

import client.networking.login.LoginClient;
import client.networking.login.LoginClientImpl;
import client.networking.create.CreateClient;
import client.networking.create.CreateClientImpl;
import client.networking.rooms.RoomsClient;
import client.networking.rooms.RoomsClientImp;

/**
 * @author Sachin
 * The class that creates and stores all the clients
 */
public class ClientFactory {
    private LoginClient loginClient;
    private CreateClient createClient;
    private RoomsClient roomsClient;



    public LoginClient getLoginClient() {
        if (loginClient == null) {
            loginClient = new LoginClientImpl();
        }
        return loginClient;
    }

    public CreateClient getCreateClient() {
        if (createClient == null) {
            createClient = new CreateClientImpl();
        }
        return createClient;
    }

    public RoomsClient getViewRoomCLient() {
        if (roomsClient == null) {
            roomsClient = new RoomsClientImp();
        }
        return roomsClient;
    }
}
