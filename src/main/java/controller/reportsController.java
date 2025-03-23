package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class reportsController {
    @FXML
    private LineChart<String, Number> chatCustomers;
    @FXML
    private BarChart<String, Number> chartReservations;

    public void initialize() {
        loadCustomerGrowthChart();
        loadReservationChart();
    }


    private void loadCustomerGrowthChart() {
        String query = "SELECT DATE_FORMAT(registered_date, '%M') AS month, COUNT(*) AS total FROM customers GROUP BY month ORDER BY MIN(registered_date)";

        try {
            ResultSet rs = CrudUtil.execute(query);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Customer Growth");

            while (rs.next()) {
                String month = rs.getString("month");
                int totalCustomers = rs.getInt("total");
                series.getData().add(new XYChart.Data<>(month, totalCustomers));
            }

            chatCustomers.getData().add(series);


            chatCustomers.applyCss();
            chatCustomers.layout();


            if (!chatCustomers.getData().isEmpty()) {
                chatCustomers.getData().get(0).getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #0078d7;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadReservationChart() {
        String query = "SELECT DATE_FORMAT(check_in_date, '%M') AS month, COUNT(*) AS total FROM reservations GROUP BY month ORDER BY MIN(check_in_date)";

        try {
            ResultSet rs = CrudUtil.execute(query);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Reservations");

            while (rs.next()) {
                String month = rs.getString("month");
                int totalReservations = rs.getInt("total");
                series.getData().add(new XYChart.Data<>(month, totalReservations));
            }

            chartReservations.getData().add(series);
            chartReservations.applyCss();
            chartReservations.layout();

            if (!chartReservations.getData().isEmpty()) {
                chartReservations.lookup(".default-color0.chart-bar").setStyle("-fx-bar-fill: #0078d7;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
