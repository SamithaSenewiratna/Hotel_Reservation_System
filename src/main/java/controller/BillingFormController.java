package controller;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import dto.Customer;
import dto.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import service.custom.BillingService;
import service.serviceFactory;


import util.CrudUtil;
import util.ReservationStatus;
import util.ServiceType;


import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;



public class BillingFormController implements Initializable {

    public Label lblCustomerName, lblRoomNumber, lblCheck_in_date, lblCheck_out_date, lblPaymentMethod, lblDate, lblTotalAmount, lblReservationID, lblEmail,lblTotal,lblEmailInvoice,lblCustomerID,lblRoomID;
    public ComboBox<String> cmbCustomerID, cmbPaymentMethod, cmbSelectRoom;
    public TextField txtCustomerName,txtRoomType,txtRoomNumber;
    public DatePicker txtCheck_in_date, txtCheck_out_date;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbCustomerID.setItems(getCustomerID());
        cmbSelectRoom.setItems(getRoomID());

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) return;
                Customer customer = searchCustomer(newValue);
                if (customer != null) {
                    txtCustomerName.setText(customer.getName());
                    lblEmail.setText(customer.getEmail());
                }
        });

        cmbSelectRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) return;
                Room room = searchRoom(newValue);
                if (room != null) {
                    txtRoomType.setText(room.getRoomType().toString());
                    txtRoomNumber.setText(room.getRoomNumber());
                }
        });

        cmbPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Mobile Payment"));

    }



    public ReservationStatus saveToLocalOnAction(ActionEvent actionEvent) {
        String status = "Pending";
        String availability = "Occupied";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            int customerId = Integer.parseInt(lblCustomerID.getText());
            int roomId = Integer.parseInt(lblRoomID.getText());
            Date checkInDate = dateFormat.parse(lblCheck_in_date.getText());
            Date checkOutDate = dateFormat.parse(lblCheck_out_date.getText());
            double totalAmount = Double.parseDouble(lblTotal.getText());


            ReservationStatus reservationStatus = ReservationStatus.valueOf(status);


            String query = "INSERT INTO Reservations (customer_id, room_id, check_in_date, check_out_date, total_amount, reservation_status) VALUES (?, ?, ?, ?, ?, ?)";

            boolean isInserted = CrudUtil.execute(query,
                    customerId,
                    roomId,
                    new java.sql.Date(checkInDate.getTime()),
                    new java.sql.Date(checkOutDate.getTime()),
                    totalAmount,
                    reservationStatus.toString()
            );


            String qry = "UPDATE rooms SET availability = ? WHERE room_number = ?";

            boolean isUpdatedRoomStatus = CrudUtil.execute(qry,
                    availability,
                    lblRoomNumber.getText()
            );

            if (isInserted && isUpdatedRoomStatus) {
                new Alert(Alert.AlertType.INFORMATION, "Reservation Added Successfully!").show();
                clearLabelInvoce();
                return ReservationStatus.Pending;
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add reservation.").show();
                return null;
            }

        } catch (ParseException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid date format! Use YYYY-MM-DD.").show();
            return null;
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format in fields!").show();
            return null;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            return null;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            return null;
        }

    }



    public void calculateTotalOnAction(ActionEvent actionEvent) {

     String type= txtRoomType.getText();
     int day= calculateDaysBetween();

     if(validateInputs()){
         new Alert(Alert.AlertType.ERROR, "Please input all details !").show();

         }else {


             switch (type) {

                 case "Single":
                     lblTotalAmount.setText(String.valueOf(Double.valueOf(day * 1200)));
                     break;

                 case "Double":
                     lblTotalAmount.setText(String.valueOf(Double.valueOf(day * 2800)));
                     break;

                 case "Suite":
                     lblTotalAmount.setText(String.valueOf(Double.valueOf(day * 3200)));
                     break;


             }

         }
    }


    private boolean validateInputs() {
        return     txtCheck_in_date.getValue() == null
                || txtCheck_out_date.getValue() == null
                || cmbPaymentMethod.getValue() == null
                || cmbSelectRoom.getValue() == null
                || cmbCustomerID.getValue() == null;
    }


    public void checkOutOnAction(ActionEvent actionEvent) {

        if ( validateInputs())
        {
            new Alert(Alert.AlertType.ERROR, "Please input all details !").show();
        }


       else {

            lblCustomerName.setText(txtCustomerName.getText());
            lblRoomNumber.setText(txtRoomNumber.getText());
            lblCheck_in_date.setText(txtCheck_in_date.getValue().toString());
            lblCheck_out_date.setText(txtCheck_out_date.getValue().toString());
            lblPaymentMethod.setText(cmbPaymentMethod.getValue());
            lblTotal.setText(lblTotalAmount.getText());
            lblEmailInvoice.setText(lblEmail.getText());
            lblCustomerID.setText(cmbCustomerID.getValue());
            lblRoomID.setText(cmbSelectRoom.getValue());

            loadDate();

            txtCustomerName.clear();
            txtRoomType.clear();
            txtRoomNumber.clear();
            txtCheck_in_date.setValue(null);
            txtCheck_out_date.setValue(null);
            cmbCustomerID.setValue(null);
            cmbSelectRoom.setValue(null);
            cmbPaymentMethod.setValue(null);
            lblTotalAmount.setText("");
            lblEmail.setText("");

        }
    }




    private void clearLabelInvoce() {
        lblCustomerName.setText("");
        lblRoomNumber.setText("");
        lblCheck_in_date.setText("");
        lblCheck_out_date.setText("");
        lblPaymentMethod.setText("");
        lblTotal.setText("");
        lblEmailInvoice.setText("");
        lblCustomerID.setText("");
        lblRoomID.setText("");
        lblDate.setText("");

    }




    public int calculateDaysBetween() {
        if (txtCheck_in_date.getValue() != null && txtCheck_out_date.getValue() != null) {
            LocalDate checkIn = txtCheck_in_date.getValue();
            LocalDate checkOut = txtCheck_out_date.getValue();
            return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        }
        return 0;
    }


    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }



    private Customer searchCustomer(String newValue) {
        BillingService service = serviceFactory.getInstance().getServiceType(ServiceType.BILLING);
        return service.searchCustomer(newValue);
    }

    private Room searchRoom(String newValue) {
        BillingService service = serviceFactory.getInstance().getServiceType(ServiceType.BILLING);
        return service.searchRoom(newValue);
    }



    public ObservableList<String> getCustomerID() {


        BillingService service = serviceFactory.getInstance().getServiceType(ServiceType.BILLING);
        List<Customer> all = service.getAll();
        ObservableList<String> customerID = FXCollections.observableArrayList();
        for (Customer customer : all) {
            customerID.add(String.valueOf(customer.getCustomerID()));
        }
        return customerID;

    }



    private ObservableList<String> getRoomID() {

        BillingService service = serviceFactory.getInstance().getServiceType(ServiceType.BILLING);
        List<Room> all = service.getAllRooms();
        ObservableList<String> roomID = FXCollections.observableArrayList();
        for (Room room : all) {
            roomID.add(String.valueOf(room.getRoomId()));
        }
        return roomID;

    }



    public void printOnAction(ActionEvent actionEvent) {
        try {
            generateInvoice();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendEmailOnAction(ActionEvent actionEvent) throws IOException {
        if (lblEmailInvoice.getText().isEmpty()) {
            System.out.println("Error: Email field is empty.");
            return;
        }

        String filePath = generateInvoice();
        sendEmailWithAttachment(lblEmailInvoice.getText(), filePath);
        System.out.println("Invoice sent to: " + lblEmailInvoice.getText());
    }

    private String generateInvoiceNumber() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + UUID.randomUUID().toString().substring(0, 5);
    }

    private String generateInvoice() throws IOException {
        String invoiceNumber = generateInvoiceNumber();
        String filePath = System.getProperty("user.home") + "/Downloads/invoice_" + invoiceNumber + ".pdf";  // PDF file path

        File directory = new File(System.getProperty("user.home") + "/Downloads");

        if (!directory.exists()) directory.mkdirs();

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);



        PdfFont font = PdfFontFactory.createFont("Helvetica-Bold");

        String logoPath = "img/bedicon.png";
        File logoFile = new File(logoPath);
        if (logoFile.exists()) {
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.scaleToFit(50, 50);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        }

        document.add(new Paragraph("  Hotel Linara ")
                .setFont(font)
                .setFontSize(25)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontColor(ColorConstants.BLUE)
                .setMarginBottom(-12)
        );

        document.add(new Paragraph(" Hotel Reservation Invoice ")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14)
                .setBold()
                .setMarginBottom(20)
        );

        document.add(new Paragraph("Welcome to Hotel Linara!\n\n We are delighted to welcome you to Hotel Linara and appreciate your choice to stay with us. Our team is dedicated to ensuring your comfort and providing you with a relaxing and enjoyable experience.\n\n Your reservation details are as follows:")
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(12)
                .setMarginTop(20))
                .setBottomMargin(20);

        Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        table.addCell(("Invoice Number:"));
        table.addCell((invoiceNumber));

        table.addCell(("Customer Name:"));
        table.addCell((lblCustomerName.getText()));

        table.addCell(("Room Number:"));
        table.addCell((lblRoomNumber.getText()));

        table.addCell(("Check-in Date:"));
        table.addCell((lblCheck_in_date.getText()));

        table.addCell(("Check-out Date:"));
        table.addCell((lblCheck_out_date.getText()));

        table.addCell(("Payment Method:"));
        table.addCell((lblPaymentMethod.getText()));

        table.addCell(getStyledCell("Total Amount:"));
        table.addCell(getStyledCell(lblTotal.getText()));


        document.add(table);

        document.add(new Paragraph("Thank you for choosing our hotel!\n We truly appreciate your stay with us and hope you had a comfortable and enjoyable experience.\n Your satisfaction is our top priority, and we look forward to welcoming you again in the near future. Safe travels, and we hope to see you again soon!")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12)
                .setMarginTop(20));

        document.close();
        System.out.println("Invoice generated: " + filePath);

        return filePath;
    }

    private static Cell getStyledCell(String text) {
        return getStyledCell(text, true);
    }

    private static Cell getStyledCell(String text, boolean highlight) {
        Cell cell = new Cell().add(new Paragraph(text).setFontSize(12));
        if (highlight) {
            cell.setBold();
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        }
        return cell;
    }

    private void sendEmailWithAttachment(String recipientEmail, String filePath) {

        final String senderEmail = "samithani17@gmail.com";
        final String senderPassword = "kxjv hzgk imkg rhkc";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your Hotel Invoice");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Dear Customer,\n\nPlease find your invoice attached.\n\nThank you!");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully with invoice attachment.");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }



}
