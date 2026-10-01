package com.example.hourgenerator2;

import javafx.beans.DefaultProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class HourController {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label totalHours;
    @FXML
    private Label myLabel;

    private LocalDate myStartDate;
    private LocalDate myEndDate;
    private boolean daysChecked = false;
    private boolean sanity = false;
    private double totalHoursNum;
    private Calculate calc = new Calculate();

    @FXML private TextField monCount, tueCount, wedCount, thuCount, friCount, satCount, sunCount;
    @FXML private TextField monHours, tueHours, wedHours, thuHours, friHours, satHours, sunHours;

    private TextField[] counts;
    private TextField[] hours;

    @FXML
    private void initialize() {
        counts = new TextField[] { monCount, tueCount, wedCount, thuCount, friCount, satCount, sunCount };
        hours = new TextField[] { monHours, tueHours, wedHours, thuHours, friHours, satHours, sunHours };
    }

    @FXML
    public void getStartDate(ActionEvent event) {
        myStartDate = startDate.getValue();
        System.out.println("Start Date: " + myStartDate);
    }

    @FXML
    public void getEndDate(ActionEvent event) {
        myEndDate = endDate.getValue();
        System.out.println("End Date: " + myEndDate);
    }

    @FXML
    public void calculateDays(ActionEvent event) {
        if (startDate != null && myEndDate != null) {
            calc.calculateTotalDays(myStartDate, myEndDate, counts);
            showSuccess("Days calculated. Edit the counts if needed");
            daysChecked = true;
        } else if (startDate == null) {
            showError("Start date not inputted");
        } else {
            showError("End date not inputted");
        }
    }
    @FXML
    public void calculateHours(ActionEvent event) {
        if (sanityCheck(hours)) {
            totalHours.setText(calc.calculateTotalHours(counts, hours, totalHoursNum, myLabel));
            showSuccess("Hours calculated. You may still edit");
        }
    }

    private boolean sanityCheck(TextField[] hours) {
        int countEmpty = 0;
        String text;

        if (!daysChecked) {
            showError("You must calculate number of days first");
            return false;
        }
        for (int i = 0; i < 7; i++) {
            text = hours[i].getText().trim();
            if (!text.isEmpty()) {
                if (text.charAt(0) == '0' && text.length() > 1 && text.charAt(1) != '.') {
                    showError("You cannot have 0 followed by some integer");
                    return false;
                }
            } else {
                countEmpty++;
                continue;
            }
            if (!isNumeric(text)) {
                return false;
            }
        }
        if (countEmpty == 7) {
            showError("At least one hour field must be filled");
            return false;
        }
        return true;
    }

    private boolean isNumeric(String str) {
        int decimalCount = 0;
        boolean hasDigits = false;

        if (str.isEmpty()) {
            return true;
        }

        for (char c : str.toCharArray()) {
            if (c == '-'){
                showError("The number cannot be negative");
                return false;
            }
            if (c == ','){
                showError("The number cannot use ' , ' as a comma");
                return false;
            }
            if (c == '.') {
                decimalCount++;
                if (decimalCount > 1) {
                    return false;
                }
            } else if (Character.isDigit(c)) {
                hasDigits = true;
            } else {
                showError("Input is not valid");
                return false;
            }
        }
        return hasDigits && decimalCount <= 1;
    }

    private void showError(String message) {
        myLabel.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 13px; -fx-font-weight: bold;");
        myLabel.setText(message);
    }
    private void showSuccess(String message) {
        myLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
        myLabel.setText(message);
    }
}
