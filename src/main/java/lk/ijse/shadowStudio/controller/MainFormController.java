package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController  {

    @FXML
    private JFXButton btnBookings;

    @FXML
    private JFXButton btnComplains;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnPackages;

    @FXML
    private JFXButton btnRent;

    @FXML
    private JFXButton btnRentItem;

    @FXML
    private JFXButton btnReports;


    @FXML
    private AnchorPane rootHome;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/views/dashboard_form.fxml"));
        rootHome.getChildren().clear();
        rootHome.getChildren().add(rootNode);

    }



    @FXML
    void btnBookingsOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Bookings");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/book_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);

    }

    @FXML
    void btnComplainsOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Complains");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/complains_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Customer");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/customer_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Dashboard");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/dashboard_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);


    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Employee");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/employee_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        System.out.println("navigating to signup");
        Parent rootNode = FXMLLoader.load(getClass().getResource("/views/login_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnPackagesOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Package");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/packages_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnRentItemOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Rent Item");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/renting_items_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnRentOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Rent");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/rent_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) throws IOException {
        System.out.println("Navigating to Manage Reports");
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/reports_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }



}
