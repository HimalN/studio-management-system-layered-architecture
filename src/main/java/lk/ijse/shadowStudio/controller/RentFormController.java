package lk.ijse.shadowStudio.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.ItemDto;
import lk.ijse.shadowStudio.dto.RentDto;
import lk.ijse.shadowStudio.dto.tm.ItemTm;
import lk.ijse.shadowStudio.dto.tm.RentTm;
import lk.ijse.shadowStudio.model.CustomerModel;
import lk.ijse.shadowStudio.model.RentItemDetailsModel;
import lk.ijse.shadowStudio.model.RentItemModel;
import lk.ijse.shadowStudio.model.RentModel;

public class RentFormController{

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colBroughtDate;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDayCount;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colRentId;

    @FXML
    private TableView<RentTm> tblRent;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblRentID;

    @FXML
    private TextField txtDayCount;

    @FXML
    private DatePicker broughtDate;

    @FXML
    private Label lblQty;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblTotalPrice;

    private final RentModel rentModel = new RentModel();
    private CustomerModel customerModel = new CustomerModel();
    private RentItemModel rentItemModel = new RentItemModel();

    private RentItemDetailsModel rentItemDetailsModel = new RentItemDetailsModel();
    private ObservableList<RentTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        generateNextRentId();
        loadCustomerIds();
        loadItemIds();
        loadAllRents();
        setCellValueFactory();
        tableListener();
    }



    private void generateNextRentId() {
        try {
            String RentId = RentModel.generateNextRentId();
            lblRentID.setText(RentId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteRentOnAction(ActionEvent event) throws SQLException {
        String id = lblRentID.getText();
        String custId = cmbCustomerId.getValue();

        boolean isDeleted = rentModel.deleteRent(id);
        boolean isDeletedRecord = rentItemDetailsModel.deleteRent(custId);

        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Rent Information Deleted").show();
            loadAllRents();
            generateNextRentId();


        } if (isDeletedRecord){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully").show();
            loadAllRents();
            generateNextRentId();

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Can not delete customer").show();
        }
    }
    private void clearFields(){
        lblRentID.setText("");
        //cmbCustomerId.setValue("");
        lblCustomerName.setText("");
        //cmbItemId.setValue("");
        lblItemName.setText("");
        txtDayCount.setText("");
        txtQty.setText("");
    }

    @FXML
    void btnSaveRentOnAction(ActionEvent event) {
        String id = lblRentID.getText();
        String custId = cmbCustomerId.getValue();
        String custName = lblCustomerName.getText();
        String itemId = cmbItemId.getValue();
        String itemName = lblItemName.getText();
        String dayCount = txtDayCount.getText();
        String broughtdate = String.valueOf(broughtDate.getValue());
        int qty = Integer.parseInt(txtQty.getText());
        String price = lblTotalPrice.getText();

        boolean isValidDayCount = RegExPatterns.getValidCount().matcher(dayCount).matches();

        if (!isValidDayCount){
            new Alert(Alert.AlertType.ERROR,"Invalid Day Count").show();
        }else {
            var dto = new RentDto(id, custId, custName, itemId,itemName,dayCount,broughtdate,qty,price);

            try {
                boolean isSuccess = rentModel.saveRentDetails(dto);
                if (!isSuccess) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Rent Details Added").showAndWait();
                    loadAllRents();
                    generateNextRentId();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnUpdateRentrOnAction(ActionEvent event) {
        String rentId = lblRentID.getText();
        String customerId = cmbCustomerId.getValue();
        String customerName = lblCustomerName.getText();
        String itemId = cmbItemId.getValue();
        String itemName = lblItemName.getText();
        String dayCount = txtDayCount.getText();
        String date = String.valueOf(broughtDate.getValue());
        int qty = Integer.parseInt(txtQty.getText());
        String price = lblTotalPrice.getText();

        boolean isValidDayCount = RegExPatterns.getValidCount().matcher(dayCount).matches();

        if (!isValidDayCount){
            new Alert(Alert.AlertType.ERROR,"Invalid Day Count").show();
        }else {
            var dto = new RentDto(rentId,customerId,customerName,itemId,itemName,dayCount,date,qty,price);
            try {
                boolean isUpdated = rentItemModel.updateRent(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Rent is Updated").show();
                    loadAllRents();
                    generateNextRentId();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }


    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();

        try {
            CustomerDto customerDto = customerModel.searchCustomer(id);
            lblCustomerName.setText(customerDto.getCust_Name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbItemId.requestFocus();
    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        String id = cmbItemId.getValue();

        try {
            ItemDto itemDto = rentItemModel.searchItem(id);
            lblItemName.setText(itemDto.getItemName());

            lblTotalPrice.setText(itemDto.getRentalPrice());

            lblQty.setText(itemDto.getQty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        broughtDate.requestFocus();
    }

    @FXML
    void dateOnAction(ActionEvent event){
        lblQty.requestFocus();
    }
    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = customerModel.getAllCustomer();

            for (CustomerDto dto : idList) {
                obList.add(dto.getCust_id());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<ItemDto> idList = rentItemModel.getAllItem();

            for (ItemDto dto : idList) {
                obList.add(dto.getItemId());
            }

            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rent_id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cust_name"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colDayCount.setCellValueFactory(new PropertyValueFactory<>("day_count"));
        colBroughtDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    private void loadAllRents() {
        var model = new RentModel();

        ObservableList<RentTm> obList = FXCollections.observableArrayList();
        try {
            List<RentDto> dtoList = model.getAllRent();

            for (RentDto dto : dtoList) {
                obList.add(
                        new RentTm(
                                dto.getRentId(),
                                dto.getCustId(),
                                dto.getCustName(),
                                dto.getItemId(),
                                dto.getItemName(),
                                dto.getDaycount(),
                                dto.getDate(),
                                dto.getQty(),
                                dto.getPrice()

                        )
                );
            }
            tblRent.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void tableListener() {
        tblRent.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblRent.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(RentTm row) {
        lblRentID.setText(row.getRent_id());
        cmbCustomerId.setValue(row.getCust_id());
        lblCustomerName.setText(row.getCust_name());
        cmbItemId.setValue(String.valueOf(row.getItem_id()));
        lblItemName.setText(row.getItem_name());
        broughtDate.setValue(LocalDate.parse(row.getDate()));
        txtDayCount.setText(row.getDay_count());
        txtQty.setText(String.valueOf(row.getQty()));
        lblTotalPrice.setText(row.getPrice());

    }

    //Request Forcus
    @FXML
    void lblQtyOnAction(ActionEvent event) {
        txtDayCount.requestFocus();
    }

    @FXML
    void txtDayCountOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }
}