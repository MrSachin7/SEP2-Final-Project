package client.networking.rooms;

import client.networking.GetServer;
import shared.networking.clientInterfaces.RoomsCallBack;
import shared.networking.serverInterfaces.Server;
import shared.utils.Observer;
import shared.utils.Request;
import shared.utils.reservation.Reservation;
import shared.utils.room.Room;
import shared.utils.room.RoomType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;

/** @author Emil
 * creating a class for the rooms view
 *
 */
public class RoomsClientImp implements RoomsClient, RoomsCallBack {
    private Server server;
    private PropertyChangeSupport support;

    /**
     * Constructor initializing the server
     */

    public RoomsClientImp()  {
        try {
            UnicastRemoteObject.exportObject(this,0);
            support= new PropertyChangeSupport(this);
            server = GetServer.getServerFromRmi();
            server.getRoomsServer().registerRoomClient(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param dateFrom the date to search from
     * @param dateTo the date to search upto
     */
    @Override
    public void searchRooms(LocalDate dateFrom, LocalDate dateTo, RoomType roomType) {
        try {
            System.out.println("View room impl");
            server.getRoomsServer().searchRooms(dateFrom, dateTo, roomType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getDescriptionByCategory(RoomType roomType) {
        try {
            return server.getRoomsServer().getDescriptionByCategory(roomType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPriceByCategory(RoomType roomType) {
        try {
            return server.getRoomsServer().getPriceByCategory(roomType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Error...";
    }

    @Override
    public Request bookRoom(Reservation reservation) {
        System.out.println("Rooms client impl");
        try {
            return server.getRoomsServer().bookRoom(reservation);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new Request("Error connecting to server",null);
    }

    @Override
    public void roomsFromServer(List<Room> roomList) throws RemoteException {
        System.out.println(roomList);
        support.firePropertyChange(Observer.AVAILABLEROOMS.toString(),null,roomList);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);

    }
}