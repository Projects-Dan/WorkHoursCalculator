package com.example.hourgenerator2;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Arrays;
import java.util.Objects;

public class Calculate {
    private int countMon;
    private int countTue;
    private int countWed;
    private int countThu;
    private int countFri;
    private int countSat;
    private int countSun;
    private int occurence;
    private int numb;

    private int[] weekdays = {countMon, countTue, countWed, countThu, countFri, countSat, countSun};
    private Weekdays days = new Weekdays();
    Calculate(){

    }
    public void calculateTotalDays(LocalDate startDate, LocalDate endDate, TextField[] counts) {
        int daysBetween = (int)ChronoUnit.DAYS.between(startDate, endDate)+1;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        int startDay = startDate.getDayOfWeek().getValue();
        int temp = startDay;
        int limit = Math.min(daysBetween, 7);
        System.out.println(limit);

        if (daysBetween > 0) {
            for (int i = 0; i < limit; i++) {
                if (startDay > 7) {
                    startDay = 1;
                    calculateOccurence(startDay, daysBetween, temp);
                    startDay++;
                }
                calculateOccurence(startDay, daysBetween, temp);
                startDay++;
            }
        } else {
            System.out.println("End date must come after start date");
        }

        assignWeekdays(weekdays, counts);
        Arrays.fill(weekdays, 0);
        System.out.println(Arrays.toString(weekdays));
    }

    public void calculateOccurence(int startDay, int daysBetween, int weekday) {
        if (startDay-7 < 0) {
            numb = 1+((startDay - weekday + 7) % 7);
            occurence = ((daysBetween-numb)/7)+1;
            weekdays[startDay-1] = occurence;
            System.out.println("Index1: " + (startDay-1) + " Occurences: " + occurence);
        } else {
            numb = 1 + ((startDay - weekday) % 7);
            occurence = ((daysBetween-numb)/7)+1;
            weekdays[startDay-1] = occurence;
            System.out.println("Index2: " + (startDay-1) + " Occurences: " + occurence);
        }
    }
    public String calculateTotalHours(TextField[] counts, TextField[] hours, double totalHours, Label myLabel) {
        for (int i = 0; i < 7; i++) {
            if (!Objects.equals(hours[i].getText(), "")) {
                totalHours += Integer.parseInt(counts[i].getText()) * Double.parseDouble(hours[i].getText());
            }
        }
        return Double.toString(totalHours);
    }

    protected void assignWeekdays(int[] weekday, TextField[] counts){
        for (int i = 0 ; i < 7; i++){
            counts[i].setText(Integer.toString(weekday[i]));
        }
    }
}
