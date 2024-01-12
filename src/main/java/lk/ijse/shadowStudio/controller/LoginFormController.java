package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.shadowStudio.BO.BOFactory;
import lk.ijse.shadowStudio.BO.custom.LoginBO;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.LoginDAO;
import lk.ijse.shadowStudio.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane rootNode;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);
    @FXML
    void btnLoginOmAction(ActionEvent event) throws IOException, SQLException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        boolean login = loginBO.login(userName, password);
        if(userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Empty").show();
            return;
        }
        if (login) {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/views/main_form.fxml"));
            Scene scene =new Scene(rootNode);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Shadow Studio");
            stage.centerOnScreen();

        } else {
            new Alert(Alert.AlertType.ERROR, "oops! credentials are wrong!").show();

        }
        DashboardFormController.staticLabel.setText(txtUserName.getText()+" !");
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        System.out.println("navigating to signup");
        Parent rootNode = FXMLLoader.load(getClass().getResource("/views/signup_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("SignUp");
        stage.setScene(scene);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLogin.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }



}

