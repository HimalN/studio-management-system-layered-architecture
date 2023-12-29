package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.ItemDto;
import lk.ijse.shadowStudio.dto.PackageDto;
import lk.ijse.shadowStudio.dto.tm.ItemTm;
import lk.ijse.shadowStudio.model.PackagesModel;
import lk.ijse.shadowStudio.model.RentItemModel;

import java.sql.SQLException;
import java.util.List;

public class RentItemFormController{

    @FXML
    private ComboBox<String> cmbItemType;

    @FXML
    private TextField txtItemQty;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colItemType;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;


    @FXML
    private TableColumn<?, ?> colRentalPrice;


    @FXML
    private TextField txtItemType;

    @FXML
    private Label lblItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtRentalPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    private final RentItemModel rentItemModel = new RentItemModel();

    public void initialize() throws SQLException {
        generateNextItemId();
        loadAllItem();
        setCellValueFactory();
        tableListener();
        setType();

    }
    private void generateNextItemId() throws SQLException {
        String itemId =RentItemModel.generateNextItemId();
        lblItemId.setText(itemId);
    }
    public void clearFields(){
        txtItemName.setText("");
        txtRentalPrice.setText("");
        txtSearch.setText("");
        txtItemQty.setText("");
        cmbItemType.setValue("");
    }
    private void tableListener() {
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblItem.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(ItemTm row) {
        lblItemId.setText(row.getItemId());
        txtItemName.setText(row.getItemName());
        cmbItemType.setValue(row.getItemType());
        txtRentalPrice.setText(String.valueOf(row.getRentalPrice()));
        txtItemQty.setText(row.getQty());
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        String id = lblItemId.getText();
        try {
            boolean isDeleted = RentItemModel.deleteItem(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted").show();
                loadAllItem();
                clearFields();
                generateNextItemId();

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Can not delete Item").show();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemId.getText();
        String itemName = txtItemName.getText();
        String itemType = cmbItemType.getValue();
        String itemPrice = txtRentalPrice.getText();
        String qty = txtItemQty.getText();

        ItemDto dto = new ItemDto(itemId,itemName,itemType,itemPrice,qty);

        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(itemPrice).matches();
        boolean isValidCount = RegExPatterns.getValidCount().matcher(qty).matches();

        if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Item Price").show();
            return;
        }if (!isValidCount){
            new Alert(Alert.AlertType.ERROR,"Invalid Item Count").show();
        }else{
            try {
                boolean isSaved = RentItemModel.saveItem(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Saved").show();
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
    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colRentalPrice.setCellValueFactory(new PropertyValueFactory<>("rentalPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }


    private void loadAllItem() {
        var model = new RentItemModel();

        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try{
            List<ItemDto> dtoList = model.getAllItem();

            for (ItemDto dto : dtoList) {
                obList.add(
                        new ItemTm(
                                dto.getItemId(),
                                dto.getItemName(),
                                dto.getItemType(),
                                dto.getRentalPrice(),
                                dto.getQty()
                        )
                );
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        String id = lblItemId.getText();
        String name = txtItemName.getText();
        String type = cmbItemType.getValue();
        String price = txtRentalPrice.getText();
        String qty = txtItemQty.getText();

        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(price).matches();
        boolean isValidCount = RegExPatterns.getValidCount().matcher(qty).matches();


        if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Item Price").show();
            return;
        }if (!isValidCount){
            new Alert(Alert.AlertType.ERROR,"Invalid Item Count").show();
        }else{
            var dto = new ItemDto(id, name, type, price,qty);
            try {
                boolean isUpdated = RentItemModel.updateItem(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item is Updated").show();
                    clearFields();
                    generateNextItemId();
                    loadAllItem();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item is Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }



    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        try {
            generateNextItemId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error While doing Action").show();
        }
    }

    @FXML
    void txtSearchItemOnAction(ActionEvent event) {
        String id = txtSearch.getText();
        try {

            ItemDto itemDto = RentItemModel.searchItem(id);
            if (itemDto != null) {
                lblItemId.setText(itemDto.getItemId());
                txtItemName.setText(itemDto.getItemName());
                cmbItemType.setValue(itemDto.getItemType());
                txtRentalPrice.setText(itemDto.getRentalPrice());
                txtItemQty.setText(itemDto.getQty());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Item not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setType(){
        ObservableList List = FXCollections.observableArrayList("Camera","Lence");
        cmbItemType.setItems(List);
    }

    //Request Forcus

    @FXML
    void cmbItemTypeOnAction(ActionEvent event) {
        txtRentalPrice.requestFocus();
    }
    @FXML
    void txtItemNameOnAction(ActionEvent event) {
        cmbItemType.requestFocus();
    }

    @FXML
    void txtItemQtyOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }

    @FXML
    void txtRentalPriceOnAction(ActionEvent event) {
        txtItemQty.requestFocus();
    }

}
