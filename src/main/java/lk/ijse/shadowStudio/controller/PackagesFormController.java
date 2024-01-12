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
import lk.ijse.shadowStudio.BO.BOFactory;
import lk.ijse.shadowStudio.BO.custom.PackageBO;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.PackageDto;
import lk.ijse.shadowStudio.dto.tm.PackageTm;

public class PackagesFormController{

    @FXML
    private ComboBox<String> cmbPackageType;

    @FXML
    private JFXButton btnClear;

    @FXML
    private TableColumn<?, ?> colPackageDescription;

    @FXML
    private TableColumn<?, ?> colPackageId;

    @FXML
    private TableColumn<?, ?> colPackageName;

    @FXML
    private TableColumn<?, ?> colPackagePrice;

    @FXML
    private TableColumn<?, ?> colPackageType;

    @FXML
    private TextField txtPackageType;

    @FXML
    private Label lblPackageId;

    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextArea txtAboutPackage;

    @FXML
    private TextField txtPackageName;

    @FXML
    private TextField txtPackagePrice;

    @FXML
    private TableView<PackageTm> tblPackages;

    @FXML
    private TextField txtPackageSearch;

    //private final PackagesModel packagesModel = new PackagesModel();

    PackageBO packageBO = (PackageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PACKAGES);

    public void initialize(){
        generateNextPackageId();
        loadAllPackages();
        setCellValueFactory();
        tableListener();
        setType();

    }
    private void clearFields(){
        lblPackageId.setText("");
        txtPackageName.setText("");
        cmbPackageType.setValue("");
        txtAboutPackage.setText("");
        txtPackagePrice.setText("");
        txtPackageSearch.setText("");

    }
    private void generateNextPackageId() {
        try {
            String packageId = packageBO.generateNextPackageID();
            lblPackageId.setText(packageId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeletePackageOnAction(ActionEvent event) {
        String id = lblPackageId.getText();
        try {
            boolean isDeleted = packageBO.deletePackage(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
                loadAllPackages();
                clearFields();
                generateNextPackageId();

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Can not delete customer").show();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSavePackageOnAction(ActionEvent event) {
        String packageId = lblPackageId.getText();
        String packageName = txtPackageName.getText();
        String packageType = cmbPackageType.getValue();
        String packageAbout = txtAboutPackage.getText();
        String packagePrice = txtPackagePrice.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(packageName).matches();
        boolean isValidAbout = RegExPatterns.getValidText().matcher(packageAbout).matches();
        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(packagePrice).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            return;
        }if (!isValidAbout){
            new Alert(Alert.AlertType.ERROR,"Invalid About").show();
            return;
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Price").show();
        }else {
            var dto = new PackageDto(packageId,packageName,packageType,packageAbout,packagePrice);

            try {
                boolean isSaved = packageBO.savePackage(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Package Saved").show();
                    clearFields();
                    loadAllPackages();
                    generateNextPackageId();

                }else {
                    new Alert(Alert.AlertType.ERROR,"Error while Saving data").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void setCellValueFactory() {
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("package_id"));
        colPackageName.setCellValueFactory(new PropertyValueFactory<>("package_name"));
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("package_type"));
        colPackageDescription.setCellValueFactory(new PropertyValueFactory<>("package_description"));
        colPackagePrice.setCellValueFactory(new PropertyValueFactory<>("package_price"));
    }
    private void loadAllPackages() {


        ObservableList<PackageTm> obList = FXCollections.observableArrayList();
        try {
            List<PackageDto> dtoList = packageBO.getAllPackage();

            for (PackageDto dto : dtoList) {
                obList.add(
                        new PackageTm(
                                dto.getPackage_id(),
                                dto.getPackage_name(),
                                dto.getPackage_type(),
                                dto.getPackage_description(),
                                dto.getPackage_price()
                        )
                );
            }
            tblPackages.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableListener() {
        tblPackages.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblPackages.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });

    }

    private void setData(PackageTm row) {
        lblPackageId.setText(row.getPackage_id());
        txtPackageName.setText(row.getPackage_name());
        cmbPackageType.setValue(String.valueOf(row.getPackage_type()));
        txtAboutPackage.setText(String.valueOf(row.getPackage_description()));
        txtPackagePrice.setText(String.valueOf(row.getPackage_price()));
    }

    @FXML
    void btnUpdatePackageOnAction(ActionEvent event) {
        String id = lblPackageId.getText();
        String name = txtPackageName.getText();
        String type = cmbPackageType.getValue();
        String description = txtAboutPackage.getText();
        String price  = txtPackagePrice.getText();

        boolean isValidName = RegExPatterns.getValidName().matcher(name).matches();
        boolean isValidAbout = RegExPatterns.getValidText().matcher(description).matches();
        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(price).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            return;
        }if (!isValidAbout){
            new Alert(Alert.AlertType.ERROR,"Invalid About").show();
            return;
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Price").show();
        }else {
            var dto = new PackageDto(id, name, type, description, price);
            try {
                boolean isUpdated = packageBO.updatePackage(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Package is Updated").show();
                    clearFields();
                    generateNextPackageId();
                    loadAllPackages();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Package is Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void txtPckageSearchOnAction(ActionEvent event) {
        String id = txtPackageSearch.getText();
        try {

            PackageDto packageDto = packageBO.searchPackage(id);
            if (packageDto != null) {
                lblPackageId.setText(packageDto.getPackage_id());
                txtPackageSearch.setText(packageDto.getPackage_id());
                txtPackageName.setText(packageDto.getPackage_name());
                cmbPackageType.setValue(packageDto.getPackage_type());
                txtAboutPackage.setText(packageDto.getPackage_description());
                txtPackagePrice.setText(packageDto.getPackage_price());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void setType(){
        ObservableList List = FXCollections.observableArrayList("Photography","Videography");
        cmbPackageType.setItems(List);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextPackageId();
    }

    //Request Forcus
    @FXML
    void cmbPackageTypeOnAction(ActionEvent event) {
        txtAboutPackage.requestFocus();
    }
    @FXML
    void txtPackageName(ActionEvent event) {
        cmbPackageType.requestFocus();
    }
    @FXML
    void txtPackagePriceOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }

}