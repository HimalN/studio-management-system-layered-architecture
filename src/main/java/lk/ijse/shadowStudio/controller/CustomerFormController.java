package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.tm.CustomerTm;
import lk.ijse.shadowStudio.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    public JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colCustAddress;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colCustName;

    @FXML
    private TableColumn<?, ?> colCustNic;

    @FXML
    private TableColumn<?, ?> colCustTelephone;

    @FXML
    private Label lblcustId;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextField txtEmailOnAction;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerNic;

    @FXML
    private TextField txtCustomerTp;

    @FXML
    private TextField txtCustomerSearch;

    @FXML
    private JFXButton btnClear;

    private final CustomerModel customerModel = new CustomerModel();


    public void initialize() {
        generateNextCustomerId();
        setCellValueFactory();
        loadAllCustomer();
        tableListener();
    }

    private void setCellValueFactory() {
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustNic.setCellValueFactory(new PropertyValueFactory<>("customerNic"));
        colCustTelephone.setCellValueFactory(new PropertyValueFactory<>("customerTp"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
    }


    private void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtoList = model.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getCust_id(),
                                dto.getCust_Name(),
                                dto.getCust_address(),
                                dto.getCust_nic(),
                                dto.getCust_tp(),
                                dto.getCust_email()
                        )
                );
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    private void clearFields() {
        lblcustId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerNic.setText("");
        txtCustomerTp.setText("");
        txtCustomerSearch.setText("");
        txtEmailOnAction.setText("");
    }

    private void tableListener() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblCustomer.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(CustomerTm row) {
        lblcustId.setText(row.getCustomerId());
        txtCustomerName.setText(row.getCustomerName());
        txtCustomerAddress.setText(String.valueOf(row.getCustomerAddress()));
        txtCustomerNic.setText(String.valueOf(row.getCustomerNic()));
        txtCustomerTp.setText(String.valueOf(row.getCustomerTp()));
        txtEmailOnAction.setText(row.getCustomerEmail());
    }

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        String custId = lblcustId.getText();
        String custName = txtCustomerName.getText();
        String custAddress = txtCustomerAddress.getText();
        String custNic = txtCustomerNic.getText();
        String custTp = txtCustomerTp.getText();
        String email = txtEmailOnAction.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(custName).matches();
        boolean isValidNic = RegExPatterns.getValidNic().matcher(custNic).matches();
        boolean isValidTp = RegExPatterns.getValidPhoneNumber().matcher(custTp).matches();
        boolean isValidAddress = RegExPatterns.getValidText().matcher(custAddress).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            return;
        }if (!isValidNic) {
            new Alert(Alert.AlertType.ERROR, "Invalid Nic").show();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Invalid Address Format").show();
            return;
        }if (!isValidTp){
            new Alert(Alert.AlertType.ERROR,"Invalid Telephone Format").show();
        }else {

            var dto = new CustomerDto(custId, custName, custAddress, custNic, custTp,email);

            try {
                boolean isSaved = CustomerModel.saveCustomer(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Savd").show();
                    loadAllCustomer();
                    clearFields();
                    generateNextCustomerId();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Error While Saving Data").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void generateNextCustomerId() {
        try {
            String custId = CustomerModel.generateNextCustomerId();
            lblcustId.setText(custId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        String id = lblcustId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String nic = txtCustomerNic.getText();
        String tp = txtCustomerTp.getText();
        String email = txtEmailOnAction.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidNic = RegExPatterns.getValidNic().matcher(nic).matches();
        boolean isValidTp = RegExPatterns.getValidPhoneNumber().matcher(tp).matches();
        boolean isValidAddress = RegExPatterns.getValidText().matcher(address).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name").show();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return;
        }if (!isValidNic){
            new Alert(Alert.AlertType.ERROR,"Invalid Nic").show();
            return;
        }if (!isValidTp){
            new Alert(Alert.AlertType.ERROR,"Invalid Telephone Format").show();
        }else {
            var dto = new CustomerDto(id, name, address, nic, tp,email);
            try {
                boolean isUpdated = customerModel.updateCustomer(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer is Updated").show();
                    clearFields();
                    generateNextCustomerId();
                    loadAllCustomer();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer is Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        String id = lblcustId.getText();
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
                loadAllCustomer();
                clearFields();
                generateNextCustomerId();

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Can not delete customer").show();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtCustomerSearchOnAction(ActionEvent event) {
        String SearchInput = txtCustomerSearch.getText();

        CustomerDto customerDto;
        try {
            if (SearchInput.matches("\\d+")){
                customerDto = customerModel.searchCustomerByTp(SearchInput);
            }else {
                customerDto = customerModel.searchCustomer(SearchInput);
            }

            if (customerDto != null) {
                lblcustId.setText(customerDto.getCust_id());
                //txtCustomerSearch.setText(customerDto.getCust_id());
                txtCustomerName.setText(customerDto.getCust_Name());
                txtCustomerAddress.setText(customerDto.getCust_address());
                txtCustomerNic.setText(customerDto.getCust_nic());
                txtCustomerTp.setText(customerDto.getCust_tp());
                txtEmailOnAction.setText(customerDto.getCust_email());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextCustomerId();
    }



    //Requesy Forcus on text field
    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtEmailOnAction.requestFocus();
    }
    @FXML
    void txtCustomerNameOnAction(ActionEvent event) {
        txtCustomerNic.requestFocus();
    }
    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtCustomerAddress.requestFocus();
    }
    @FXML
    void txtTelephoneOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }
    @FXML
    void txtEmailOnAction(ActionEvent event) {txtCustomerTp.requestFocus();}

}