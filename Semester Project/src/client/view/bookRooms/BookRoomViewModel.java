package client.view.bookRooms;

import client.core.ModelFactory;
import client.model.login.LoginModel;
import client.model.rooms.RoomsModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.utils.Observer;
import shared.utils.Request;
import shared.utils.room.Room;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class BookRoomViewModel {
    private RoomsModel roomsModel;
    private LoginModel loginModel;
    private ObservableList<Room> listRooms;
    private List<Room> selectedRooms;
    private StringProperty error;

    public BookRoomViewModel(ModelFactory modelFactory) {
        roomsModel = modelFactory.getViewRoomsModel();
        this.loginModel = modelFactory.getLoginModel();
        listRooms = FXCollections.observableArrayList();
        selectedRooms= new ArrayList<>();
        roomsModel.addListener(Observer.AVAILABLEROOMS.toString(), this::roomFromServer);
        error = new SimpleStringProperty();
    }

    private void roomFromServer(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            listRooms.clear();
            List<Room> rooms = (List<Room>) event.getNewValue();
            listRooms.addAll(rooms);
        });
    }

    public ObservableList<Room> getRoomList() {
        return listRooms;
    }

    public void bookRoom() {
        Request request = roomsModel.bookRoom(loginModel.getUsername(), selectedRooms, roomsModel.getTempStartDate(), roomsModel.getTempEndDate());
        error.set(request.getType());

    }

    public void setSelectedRoom(ObservableList<Room> selectedRooms) {
        for (Room i : selectedRooms
        ) {
            this.selectedRooms.add(i);
        }
    }

    public StringProperty getError() {
        return error;
    }
}
