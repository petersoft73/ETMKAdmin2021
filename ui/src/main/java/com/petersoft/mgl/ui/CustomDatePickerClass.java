package com.petersoft.mgl.ui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.Locale;

public class CustomDatePickerClass extends DatePicker {

    public CustomDatePickerClass() {
        setDatePicker();
    }

    private void setDatePicker() {

        //   TimePicker timePicker = new TimePicker();
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setLocale(new Locale("hu", "HU"));
        datePickerSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(datePickerSettings);
        datePicker.setDateToToday();
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        URL dateImageURL = CustomDatePickerClass.class.getResource("/images/datepickerbutton1.png");
        Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
        Icon dateExampleIcon = new ImageIcon(dateExampleImage);

        //   datePicker.setText("");
        //   datePickerButton.setIcon(dateExampleIcon);


    }
}
