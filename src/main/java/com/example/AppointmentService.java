package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    // Method to save the appointment (existing code)
    public void saveAppointment(Appointment appointment) {
        // Save the appointment to the database
        appointmentRepo.save(appointment);
    }

    // New method to fetch all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();  // Fetch all appointments from the repository
    }
}