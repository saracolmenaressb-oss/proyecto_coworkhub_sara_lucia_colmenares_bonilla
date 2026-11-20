package com.campuslands.proyectocoworkhub.presentation.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BookingProperties {

    @Value("${coworkhub.booking.max-advance-days}")
    private int maxAdvanceDays;

    public int getMaxAdvanceDays() {
        return maxAdvanceDays;
    }

}
