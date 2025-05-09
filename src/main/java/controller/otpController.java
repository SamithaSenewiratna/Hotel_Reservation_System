package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;


@RequiredArgsConstructor
public  class otpController {
    private static final String EMAIL = "samithani17@gmail.com";
    private static final String PASSWORD = "kxjv hzgk imkg rhkc";
    public  TextField txtEmails;


    private static String otp;
    private static String email;

    public void sendOtpOnAction(ActionEvent actionEvent) throws IOException {
        String userEmail = txtEmails.getText();

        // Generate
        otp = generateOTP();
        email=userEmail;


        sendOTP(userEmail, otp);

        System.out.println("Generated OTP: " + otp);


        Parent root = FXMLLoader.load(getClass().getResource("../view/fogetPassword.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();


        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public static String sendotp(){
        return  otp;
    }

    public static String sendemail(){

        return email;
    }

    public static String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generates a 6-digit OTP
    }

    public static void sendOTP(String recipientEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Reset OTP");
            // email content
            String emailContent = "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 400px; border: 1px solid #ddd;'>"
                    + "<h2 style='color: #0078D7;'>Your OTP Code</h2>"
                    + "<p>Hello,</p>"
                    + "<p>You requested a password reset. Use the following OTP to proceed:</p>"
                    + "<h3 style='font-size: 24px; color: #333; background: #f4f4f4; padding: 10px; display: inline-block; border-radius: 5px;'>" + otp + "</h3>"
                    + "<p>This OTP is valid for a limited time. Do not share it with anyone.</p>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "<br><p>Best regards,</p>"
                    + "<p><strong>Hotel Linara</strong></p>"
                    + "</div>";

            message.setContent(emailContent, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("OTP sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void btnBackOnActions(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/addCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("");
        stage.show();


        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), stage.getScene().getRoot());
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }


}
