package server.database.Reservation;

import server.database.DataBaseConnection;
import shared.utils.Request;
import shared.utils.reservation.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReservationDAOImp implements ReservationDAO {

    private static ReservationDAO instance;
    private static Lock lock = new ReentrantLock();

    private ReservationDAOImp() {

    }

    public static ReservationDAO getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ReservationDAOImp();
                }
            }
        }
        return instance;
    }

    @Override
    public Request addReservation(String username, LocalDate startDate, LocalDate endDate, String roomName) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT into \"Reservation\"(\"userName\",\"startDate\",\"endDate\",\"roomName\") VALUES(?,?,?,?);");
            statement.setString(1, username);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            statement.setString(4, roomName);
            statement.executeUpdate();
            return new Request("Room is booked", null);
        } catch (SQLException throwables) {
            return new Request(throwables.getMessage(), null);
        }
    }

    @Override
    public Request getReservationByUsername(String username) {
        try (Connection connection = DataBaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Reservation\" WHERE \"userName\"=?;");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            List<String> roomList = new ArrayList<>();
            List<Reservation> reservations = new ArrayList<>();
            LocalDate startDate = null;
            LocalDate endDate = null;
            while (resultSet.next()) {
                String roomName = resultSet.getString("roomName");
                roomList.add(roomName);
                startDate = (resultSet.getDate("startDate")).toLocalDate();
                endDate = (resultSet.getDate("endDate")).toLocalDate();
            }
            if (startDate == null || endDate == null || roomList.isEmpty()) {
                return new Request("No reservation found", null);
            } else {
                return new Request("Reservation found..", new Reservation(username, startDate, endDate, roomList));
            }

        } catch (SQLException throwables) {
            return new Request(throwables.getMessage(), null);
        } catch (Exception e) {
            return new Request(e.getMessage(), null);
        }

    }
}