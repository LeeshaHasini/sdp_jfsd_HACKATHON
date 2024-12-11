package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Endpoint for booking an appointment.
     */
    @PostMapping("/book")
    public String bookAppointment(@RequestParam Long doctorId,
                                  @RequestParam String name,
                                  @RequestParam int age,
                                  @RequestParam String problem,
                                  Model model) {
        try {
            // Create and save a new appointment
            Appointment appointment = new Appointment();
            appointment.setDoctorId(doctorId);
            appointment.setName(name);
            appointment.setAge(age);
            appointment.setProblem(problem);
            appointmentService.saveAppointment(appointment);

            // Redirect to the success page
            return "redirect:/appointments/success";
        } catch (Exception e) {
            // Log the error and return to the error page with a message
            model.addAttribute("errorMessage", "An error occurred while booking your appointment. Please try again.");
            return "error"; // Return to a generic error page
        }
    }

    /**
     * Endpoint to display all appointments.
     */
    @GetMapping("/list")
    public String listAppointments(Model model) {
        // Fetch the list of all appointments
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointments/list"; // Thymeleaf page to display appointments
    }

    /**
     * Endpoint for appointment success page.
     */
    @GetMapping("/success")
    public String showSuccessPage() {
        return "appointments/success"; // Thymeleaf success page
    }
}
