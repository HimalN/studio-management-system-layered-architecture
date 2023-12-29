package lk.ijse.shadowStudio.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.SignUpDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {


    @FXML
    private Label lblBookings;

    @FXML
    private Label lblComplaims;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblProfit;

    @FXML
    private Label lblRentedItems;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalCustomers;

    @FXML
    private Label lblTotalItems;

    @FXML
    private Label lblwelcomeName;

    @FXML
    private AnchorPane rootHome;

    private SignUpDto userDto;

    @FXML
    private Label lbluser;

    @FXML
    static Label staticLabel;

    public void initialize() throws SQLException {
        setTime();
        countCustomer();
        countItems();
        countprofit();
        countBookings();
        countComplains();
        countRentItems();
        staticLabel = lblwelcomeName;
    }

    private void setTime() {
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }
    private void countCustomer()  {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            String query = "select count(*) from customer";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            lblTotalCustomers.setText(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void countItems()  {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            String query = "select count(*) from item";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            lblTotalItems.setText(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void countBookings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "select count(*) from bookings";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);
        lblBookings.setText(String.valueOf(count));
    }
    private void countprofit() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stmt = connection.createStatement();
        //Query to get the number of rows in a table
        String query = "SELECT SUM(payment) AS total_sum FROM bookings;";
        //Executing the query
        ResultSet rs = stmt.executeQuery(query);
        //Retrieving the result
        rs.next();
        int count = rs.getInt(1);

        Statement stmt1 = connection.createStatement();
        //Query to get the number of rows in a table
        String query1 = "SELECT SUM(price) AS total_sum FROM rent;";
        //Executing the query
        ResultSet rs1 = stmt.executeQuery(query1);
        //Retrieving the result
        rs1.next();
        int countRent = rs1.getInt(1);

        int finalProfit = countRent+count;
        lblProfit.setText(String.valueOf("Rs."+finalProfit));
    }
    private void countComplains()  {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            String query = "select count(*) from complains";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            lblComplaims.setText(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void countRentItems()  {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            String query = "select count(*) from rent";
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            lblRentedItems.setText(String.valueOf(count));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
