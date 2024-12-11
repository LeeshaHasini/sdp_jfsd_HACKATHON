package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private AppointmentService appointmentService;


    // Home Page
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
    
    // Registration Form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }
    
    // Process Registration
    @PostMapping("/process_register")
    public String processRegister(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return "register_success";
    }
    
    // List Users
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }
    
    // Fitness Programs
    @GetMapping("/fitness")
    public String viewFitnessProgramsPage() {
        return "fitnessprograms";
    }
    
    @GetMapping("/fitness/{program}")
    public String viewProgramDetails(@PathVariable("program") String program, Model model) {
        model.addAttribute("program", program);
        return "fitness_details";
    }
    
    // Nutrition Page
    @GetMapping("/nutrition")
    public String viewNutritionPage() {
        return "nutrition";
    }
    
    @GetMapping("/nutrition/{program}")
    public String viewNutritionDetails(@PathVariable("program") String program, Model model) {
        model.addAttribute("program", program);
        return "nutrition_details";
    }
    
    // List Doctors
    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorRepo.findAll());
        return "doctors";
    }

    // Show Add Doctor Form
    @GetMapping("/doctors/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "addDoctor";  // This corresponds to the "Add Doctor" form
    }

    // Handle Add Doctor Form Submission
    @PostMapping("/doctors/add")
    public String addDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
        return "redirect:/doctors";  // Redirect back to the list of doctors
    }
    
    @PostMapping("/doctors/book")
    public String successmsg() {
        return "success";  // Redirect back to the list of doctors
    }
    
    
    
}