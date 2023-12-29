package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.Label;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.ComplainDto;
import lk.ijse.shadowStudio.dto.EmployeeDto;
import lk.ijse.shadowStudio.dto.ItemDto;
import lk.ijse.shadowStudio.dto.tm.ComplainTm;
import lk.ijse.shadowStudio.dto.tm.EmployeeTm;
import lk.ijse.shadowStudio.model.ComplainModel;
import lk.ijse.shadowStudio.model.CustomerModel;
import lk.ijse.shadowStudio.model.EmployeeModel;
import lk.ijse.shadowStudio.model.RentItemModel;

public class EmployeeFormController{

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colEmployeeNic;

    @FXML
    private TableColumn<?, ?> colEmployeeTelephone;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextField txtEmlployeeAddress;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeNic;

    @FXML
    private TextField txtEmployeeTp;

    @FXML
    private TextField txtEmlployeeSearch;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    private final EmployeeModel employeeModel = new EmployeeModel();

    public void initialize() throws SQLException {
        generateNextEmployeeId();
        loadAllEmployee();
        setCellValueFactory();
        tableListener();
    }

    private void generateNextEmployeeId() throws SQLException {
        String empId =EmployeeModel.generateNextEmployeeId();
        lblEmployeeId.setText(empId);
    }


    private void clearFields() {
        txtEmployeeName.setText("");
        txtEmlployeeAddress.setText("");
        txtEmployeeNic.setText("");
        txtEmployeeTp.setText("");
        txtEmlployeeSearch.setText("");
    }

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) throws SQLException {
        String empId = lblEmployeeId.getText();
        String empName = txtEmployeeName.getText();
        String empAddress = txtEmlployeeAddress.getText();
        String empNic = txtEmployeeNic.getText();
        String empTp = txtEmployeeTp.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(empName).matches();
        boolean isValidAddress = RegExPatterns.getValidText().matcher(empAddress).matches();
        boolean isValidNic = RegExPatterns.getValidNic().matcher(empNic).matches();
        boolean isValidTp = RegExPatterns.getValidPhoneNumber().matcher(empTp).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Invalid Name Format").show();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Invalid Address Format").show();
            return;
        }if (!isValidNic){
            new Alert(Alert.AlertType.ERROR,"Invalid Nic Format").show();
            return;
        }if (!isValidTp){
            new Alert(Alert.AlertType.ERROR,"Invalid Telephone Format").show();
        }else {
            EmployeeDto dto = new EmployeeDto(empId,empName,empAddress,empNic,empTp);

            try {
                boolean isSaved = EmployeeModel.saveEmployee(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Empoyee Saved").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error While Saving Data").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }


    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) throws SQLException {
        String emp_id  = txtEmlployeeSearch.getText();

        try {
            boolean isDeleted = EmployeeModel.deleteEmployee(emp_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted").show();
                clearFields();
                loadAllEmployee();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Deleted").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmlployeeAddress.getText();
        String nic = txtEmployeeNic.getText();
        String tp = txtEmployeeTp.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidAddress = RegExPatterns.getValidText().matcher(address).matches();
        boolean isValidNic = RegExPatterns.getValidNic().matcher(nic).matches();
        boolean isValidTp = RegExPatterns.getValidPhoneNumber().matcher(tp).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Invalid Name Format").show();
            return;
        }if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Invalid Address Format").show();
            return;
        }if (!isValidNic){
            new Alert(Alert.AlertType.ERROR,"Invalid Nic Format").show();
            return;
        }if (!isValidTp){
            new Alert(Alert.AlertType.ERROR,"Invalid Telephone Format").show();
        }else {
            var dto = new EmployeeDto(id,name,address,nic,tp);
            try {
                boolean isUpdated = EmployeeModel.updateEmployee(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee is Updated").show();
                    clearFields();
                    loadAllEmployee();
                    generateNextEmployeeId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee is Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmployeeNic.setCellValueFactory(new PropertyValueFactory<>("employeeNic"));
        colEmployeeTelephone.setCellValueFactory(new PropertyValueFactory<>("employeeTp"));
    }

    @FXML
    void txtEmployeeSearchOnAction(ActionEvent event) {
        String id = txtEmlployeeSearch.getText();
        try {

            EmployeeDto employeeDto = EmployeeModel.searchEmployee(id);
            if (employeeDto != null) {
                lblEmployeeId.setText(employeeDto.getEmp_id());
                txtEmployeeName.setText(employeeDto.getEmp_name());
                txtEmlployeeAddress.setText(employeeDto.getEmp_address());
                txtEmployeeNic.setText(employeeDto.getEmp_id());
                txtEmployeeTp.setText(employeeDto.getEmp_tp());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Item not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void tableListener() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblEmployee.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(EmployeeTm row) {
        lblEmployeeId.setText(row.getEmployeeId());
        txtEmployeeName.setText(row.getEmployeeName());
        txtEmployeeNic.setText(row.getEmployeeNic());
        txtEmlployeeAddress.setText(row.getEmployeeAddress());
        txtEmployeeTp.setText(row.getEmployeeTp());
        txtEmlployeeSearch.setText(row.getEmployeeId());
    }


    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try{
            List<EmployeeDto> dtoList = model.getAllEmployee();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmp_id(),
                                dto.getEmp_name(),
                                dto.getEmp_address(),
                                dto.getEmp_nic(),
                                dto.getEmp_tp()
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Request Forcus

    @FXML
    void txtEmployeeAddressOnAction(ActionEvent event) {
        txtEmployeeTp.requestFocus();
    }

    @FXML
    void txtEmployeeNameOnAction(ActionEvent event) {
        txtEmployeeNic.requestFocus();
    }

    @FXML
    void txtEmployeeNicOnAction(ActionEvent event) {
        txtEmlployeeAddress.requestFocus();
    }

    @FXML
    void txtTelephoneOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }
}
