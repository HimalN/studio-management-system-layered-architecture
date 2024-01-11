package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.Label;
import lk.ijse.shadowStudio.BO.BOFactory;
import lk.ijse.shadowStudio.BO.custom.ComplainBO;
import lk.ijse.shadowStudio.BO.custom.CustomerBO;
import lk.ijse.shadowStudio.dao.custom.CustomerDAO;
import lk.ijse.shadowStudio.dao.custom.Impl.CustomerDAOImpl;
import lk.ijse.shadowStudio.dto.ComplainDto;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.tm.ComplainTm;
import lombok.SneakyThrows;

public class ComplainsFormController {

    @FXML
    private TableView<ComplainTm> tblComplains;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<?, ?> colComplainId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private Label lblComplainsid;

    @FXML
    private Label lblCustName;

    @FXML
    private TextArea txtComplain;

    @FXML
    private TextField txtSearchComplain;

    @FXML
    private JFXButton btnClear;

    ComplainBO complainBO = (ComplainBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COMPLAIN);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() throws ClassNotFoundException {
        clearFields();
        generateNextComplainId();
        loadCustomerIds();
        setCellValueFactory();
        loadAllComplains();
        tableListener();
    }

    private void loadCustomerIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = customerBO.getAllCustomers();

            for (CustomerDto dto : idList) {
                obList.add(dto.getCust_id());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextComplainId() throws ClassNotFoundException {
        try {
            String complainId = complainBO.generateNextComplainID();
            lblComplainsid.setText(complainId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnDeleteComplainOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblComplainsid.getText();
        boolean isDeleted = complainBO.deleteComplain(id);
        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Complain Deleted").show();
            loadAllComplains();
            clearFields();
            generateNextComplainId();

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Can not delete customer").show();

        }

    }


    @SneakyThrows
    @FXML
    void btnSaveComplainOnAction(ActionEvent event) {
        String id = lblComplainsid.getText();
        String custId = cmbCustomerId.getValue();
        String custName = lblCustName.getText();
        String complain = txtComplain.getText();



        var dto = new ComplainDto(id, custId, custName, complain);

        boolean isSaved = complainBO.saveComplain(dto);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Complain Added").show();
            clearFields();
            loadAllComplains();
            generateNextComplainId();

        }else {
            new Alert(Alert.AlertType.ERROR,"Error While Saving data");
        }

    }

    @FXML
    void btnUpdateComplainOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = lblComplainsid.getText();
        String custId = cmbCustomerId.getValue();
        String custName = lblCustName.getText();
        String complain = txtComplain.getText();

        var dto = new ComplainDto(id, custId, custName,complain);
        try {
            boolean isUpdated = complainBO.updateComplain(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Complain is Updated").show();
                clearFields();
                loadAllComplains();
                generateNextComplainId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Complain is Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
        generateNextComplainId();
    }

    private void clearFields() {
        lblCustName.setText(null);
        txtComplain.setText("");
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = cmbCustomerId.getValue();

        try {
            CustomerDto customerDto = customerBO.searchCustomer(id);
            lblCustName.setText(customerDto.getCust_Name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtSearchComplainOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtSearchComplain.getText();
        try {

            ComplainDto complainDto = complainBO.searchComplain(id);

            if (complainDto != null) {
                lblComplainsid.setText(complainDto.getComplain_id());
                cmbCustomerId.setValue(complainDto.getCust_id());
                lblCustName.setText(complainDto.getCust_name());
                txtComplain.setText(complainDto.getAbout());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setCellValueFactory() {
        colComplainId.setCellValueFactory(new PropertyValueFactory<>("comId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("aboutComplain"));
    }

    private void loadAllComplains() throws ClassNotFoundException {
        ObservableList<ComplainTm> obList = FXCollections.observableArrayList();
        try {
            List<ComplainDto> dtoList = complainBO.getAllComplains();

            for (ComplainDto dto : dtoList) {
                obList.add(
                        new ComplainTm(
                                dto.getComplain_id(),
                                dto.getCust_id(),
                                dto.getCust_name(),
                                dto.getAbout()
                        )
                );
            }
            tblComplains.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void tableListener() {
        tblComplains.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblComplains.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(ComplainTm row) {
        lblComplainsid.setText(row.getComId());
        cmbCustomerId.setValue(row.getCusId());
        lblCustName.setText(row.getCusName());
        txtComplain.setText(String.valueOf(row.getAboutComplain()));
    }
}