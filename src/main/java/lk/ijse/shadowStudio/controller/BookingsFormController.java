package lk.ijse.shadowStudio.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.shadowStudio.RegExPatterns.RegExPatterns;
import lk.ijse.shadowStudio.dto.BookingDto;
import lk.ijse.shadowStudio.dto.CustomerDto;
import lk.ijse.shadowStudio.dto.PackageDto;
import lk.ijse.shadowStudio.dto.tm.BookingTm;
import lk.ijse.shadowStudio.model.BookingsModel;
import lk.ijse.shadowStudio.model.CustomerModel;
import lk.ijse.shadowStudio.model.PackagesModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingsFormController {

    @FXML
    private JFXButton btnRecipt;

    @FXML
    private TextField txtPaymentAmmount;

    @FXML
    private TableView<BookingTm> tblBookings;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private DatePicker bookingDate;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbPackageId;

    @FXML
    private TableColumn<?, ?> colBookingId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colPackageId;

    @FXML
    private TableColumn<?, ?> colPackageName;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private Label lblBookingId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private JFXButton btnSave;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TextArea txtCustomerIdea;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtTime;

    @FXML
    private Label lblPackageName;

    @FXML
    private TextField txtBookingSearch;

    private final BookingsModel bookingsModel = new BookingsModel();
    private final PackagesModel packagesModel = new PackagesModel();
    private CustomerModel customerModel = new CustomerModel();

    public void initialize() {
        generateNextBookingId();
        loadCustomerIds();
        loadPackageIds();
        loadAllBookings();
        setCellValueFactory();
        tableListener();
    }

    public void clearFields(){

        lblCustomerName.setText("");
        lblPackageName.setText("");
        txtCustomerIdea.setText("");
        bookingDate.setValue(null);
        txtTime.setText("");
        txtLocation.setText("");
        txtBookingSearch.setText("");
        txtPaymentAmmount.setText("");
    }

    private void generateNextBookingId() {
        try {
            String bookingId = BookingsModel.generateNextBookingId();
            lblBookingId.setText(bookingId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

    private void loadPackageIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PackageDto> idList = packagesModel.getAllPackages();

            for (PackageDto dto : idList) {
                obList.add(dto.getPackage_id());
            }
            cmbPackageId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteBookingOnAction(ActionEvent event) {
        String id = lblBookingId.getText();
        try {
            boolean isDeleted = bookingsModel.deleteBooking(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking Deleted Deleted").show();
                loadAllBookings();
                clearFields();
                generateNextBookingId();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Can not delete booking").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveBookingOnAction(ActionEvent event) throws SQLException {
        String id = lblBookingId.getText();
        String custId = cmbCustomerId.getValue();
        String custName = lblCustomerName.getText();
        String packageId = cmbPackageId.getValue();
        String packageName = lblPackageName.getText();
        String date = String.valueOf(bookingDate.getValue());
        String time = txtTime.getText();
        String location = txtLocation.getText();
        String custIdea = txtCustomerIdea.getText();
        String payment = txtPaymentAmmount.getText();

        boolean isValidLocation = RegExPatterns.getValidText().matcher(location).matches();
        boolean isValidIdea = RegExPatterns.getValidText().matcher(custIdea).matches();
        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(payment).matches();

        if (!isValidLocation){
            new Alert(Alert.AlertType.ERROR,"Invalid Location format").show();
            return;
        }if (!isValidIdea){
            new Alert(Alert.AlertType.ERROR,"Invalid Idea format").show();
            return;
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid price format").show();
        }else{
            var dto = new BookingDto(
                    id,
                    custId,
                    custName,
                    packageId,
                    packageName,
                    date,
                    time,
                    location,
                    custIdea,
                    payment
            );

            boolean isSaved = BookingsModel.saveBooking(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Complain Added").show();
                clearFields();
                loadAllBookings();
                generateNextBookingId();

            }else {
                new Alert(Alert.AlertType.ERROR,"Error While Saving data");
            }
        }
    }

    @FXML
    void btnUpdateBookingOnAction(ActionEvent event) {
        String bid = lblBookingId.getText();
        String custId = cmbCustomerId.getValue();
        String custName = lblCustomerName.getText();
        String packageId = cmbPackageId.getValue();
        String packageName = lblPackageName.getText();
        String date = String.valueOf(bookingDate.getValue());
        String time = txtTime.getText();
        String location = txtLocation.getText();
        String custIdea = txtCustomerIdea.getText();
        String paymemt = txtPaymentAmmount.getText();

        boolean isValidLocation = RegExPatterns.getValidText().matcher(location).matches();
        boolean isValidIdea = RegExPatterns.getValidText().matcher(custIdea).matches();
        boolean isValidPrice = RegExPatterns.getValidPrice().matcher(paymemt).matches();

        if (!isValidLocation){
            new Alert(Alert.AlertType.ERROR,"Invalid Location format").show();
            return;
        }if (!isValidIdea){
            new Alert(Alert.AlertType.ERROR,"Invalid Idea format").show();
            return;
        }if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid price format").show();
        }else{
            var dto = new BookingDto(bid,custId,custName,packageId,packageName,date,time,location,custIdea,paymemt);
            try {
                boolean isUpdated = bookingsModel.updateBookings(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Booking Updated").show();
                    clearFields();
                    generateNextBookingId();
                    loadAllBookings();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Booking is Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }

    @FXML
    void txtBookingSearchOnAction(ActionEvent event) {
        String id = txtBookingSearch.getText();
        try {

            BookingDto bookingDto = bookingsModel.searchBookings(id);
            if (bookingDto != null) {
                lblBookingId.setText(bookingDto.getBooking_id());
                txtBookingSearch.setText(bookingDto.getBooking_id());
                cmbCustomerId.setValue(bookingDto.getCust_id());
                lblCustomerName.setText(bookingDto.getCust_name());
                cmbPackageId.setValue(bookingDto.getPackage_id());
                lblPackageName.setText(bookingDto.getPackage_name());
                bookingDate.setValue(LocalDate.parse(bookingDto.getDate()));
                txtTime.setText(bookingDto.getTime());
                txtLocation.setText(bookingDto.getLocation());
                txtCustomerIdea.setText(bookingDto.getDescription());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Booking not found !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
        cmbPackageId.requestFocus();
    }

    @FXML
    void cmbPackageIdOnAction(ActionEvent event) {
        String id = cmbPackageId.getValue();

        try {
            PackageDto packageDto = packagesModel.searchPackage(id);
            lblPackageName.setText(packageDto.getPackage_name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        bookingDate.requestFocus();
    }
    private void setCellValueFactory() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cust_id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cust_name"));
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("package_id"));
        colPackageName.setCellValueFactory(new PropertyValueFactory<>("package_name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }

    private void loadAllBookings() {
        var model = new BookingsModel();

        ObservableList<BookingTm> obList = FXCollections.observableArrayList();
        List<BookingDto> dtoList = model.getAllBookings();

        for (BookingDto dto : dtoList) {
            obList.add(
                    new BookingTm(
                            dto.getBooking_id(),
                            dto.getCust_id(),
                            dto.getCust_name(),
                            dto.getPackage_id(),
                            dto.getPackage_name(),
                            dto.getDate(),
                            dto.getTime(),
                            dto.getLocation(),
                            dto.getDescription(),
                            dto.getPaymemt()
                    )
            );
        }
        tblBookings.setItems(obList);
    }
    private void tableListener() {
        tblBookings.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            if (tblBookings.getSelectionModel().getSelectedItem() != null) {
                setData(newValue);
            }
        });
    }

    private void setData(BookingTm row) {
        lblBookingId.setText(row.getBooking_id());
        cmbCustomerId.setValue(row.getCust_id());
        lblCustomerName.setText(row.getCust_name());
        cmbPackageId.setValue(row.getPackage_id());
        lblPackageName.setText(row.getPackage_name());
        bookingDate.setValue(LocalDate.parse(row.getDate()));
        txtTime.setText(row.getTime());
        txtLocation.setText(row.getLocation());
        txtCustomerIdea.setText(row.getDescription());
        txtPaymentAmmount.setText(row.getPayment());
    }

    @FXML
    void btnReciptEmailOnAction(ActionEvent event) {
        //String bookinId = lblBookingId.getText();



    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
       generateNextBookingId();
    }

    //Request forcus
    @FXML
    void bookingDateOnAction(ActionEvent event) {
        txtCustomerIdea.requestFocus();
    }

    @FXML
    void txtAmmountOnAction(ActionEvent event) {
        btnSave.requestFocus();
    }

    @FXML
    void txtLocationOnAction(ActionEvent event) {
        txtTime.requestFocus();
    }

    @FXML
    void txtTimeOnAction(ActionEvent event) {
        txtPaymentAmmount.requestFocus();
    }


}
