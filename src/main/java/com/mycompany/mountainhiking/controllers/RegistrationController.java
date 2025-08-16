package com.mycompany.mountainhiking.controllers;

import com.mycompany.mountainhiking.models.Mountain;
import com.mycompany.mountainhiking.models.Registration;
import com.mycompany.mountainhiking.models.Student;
import com.mycompany.mountainhiking.repo.MountainRepository;
import com.mycompany.mountainhiking.repo.RegistrationRepository;
import com.mycompany.mountainhiking.services.TuitionCalculator;
import com.mycompany.mountainhiking.services.Validator;
import com.mycompany.mountainhiking.views.ConsoleView;

import java.util.*;

public class RegistrationController {
    private final ConsoleView view;
    private final RegistrationRepository registrationRepository;
    private final MountainRepository mountainRepository;
    private final Validator validator;
    private final TuitionCalculator calc;

    public RegistrationController(ConsoleView view, RegistrationRepository registrationRepository, MountainRepository mountainRepository, Validator validator, TuitionCalculator calc) {
        this.view = view;
        this.registrationRepository = registrationRepository;
        this.mountainRepository = mountainRepository;
        this.validator = validator;
        this.calc = calc;
    }

    public boolean add() {
        String id = askValid("Student ID (SE/HE/DE/QE/CE + 6 digits): ", validator::id).toUpperCase();
        if (registrationRepository.exists(id)) { view.msg("This student has already registered."); return false; }
        String name  = askValid("Full name (2-20 chars): ", validator::name);
        String phone = askValid("Phone (10 digits): ", validator::phone);
        String email = askValid("Email: ", validator::email);
        String code  = askMountainCode();

        Registration r = new Registration(new Student(id, name, phone, email), code, calc.calc(phone));
        
        registrationRepository.put(r);
        view.msg("Registered successfully. Fee = " + calc.calc(phone));
        return true;
    }

    public boolean update() {
        String id = view.ask("Student ID to update: ").toUpperCase();
        Registration r = registrationRepository.get(id);
        if (r == null) { view.msg("This student has not registered yet."); return false; }

        String name = view.ask("Name [" + r.student.getName() + "]: ");
        if (!name.isBlank() && validator.name(name)) r.student.setName(name);

        String phone = view.ask("Phone [" + r.student.getPhone() + "]: ");
        if (!phone.isBlank() && validator.phone(phone)) { r.student.setPhone(phone); r.tuitionFee = calc.calc(phone); }

        String email = view.ask("Email [" + r.student.getEmail() + "]: ");
        if (!email.isBlank() && validator.email(email)) r.student.setEmail(email);

        String code = view.ask("Mountain code [" + r.mountainCode + "]: ");
        if (!code.isBlank()) {
            String u = code.toUpperCase();
            if (mountainRepository.get(u) != null) r.mountainCode = u; else view.msg("Invalid mountain code. Keeping old value.");
        }

        view.msg("Updated.");
        return true;
    }

    public void display() { view.printAll(registrationRepository.all(), mountainRepository.all()); }

    public boolean delete() {
        String id = view.ask("Student ID to delete: ").toUpperCase();
        if (registrationRepository.get(id) == null) { view.msg("This student has not registered yet."); return false; }
        if (view.confirm("Are you sure")) { registrationRepository.remove(id); view.msg("Deleted."); return true; }
        return false;
    }

    public void search() {
        String key = view.ask("Name contains: ").toLowerCase();
        List<Registration> list = new ArrayList<>();
        for (Registration r : registrationRepository.all()) if (r.student.getName().toLowerCase().contains(key)) list.add(r);
        view.printAll(list, mountainRepository.all());
    }

    public void filter() {
        String campus = view.ask("Campus code (SE/HE/DE/QE/CE): ").toUpperCase();
        List<Registration> list = new ArrayList<>();
        for (Registration r : registrationRepository.all()) if (r.student.campus().equals(campus)) list.add(r);
        view.printAll(list, mountainRepository.all());
    }

    public void stats() {
        Map<String, Integer> cnt = new LinkedHashMap<>();
        Map<String, Long> sum = new LinkedHashMap<>();
        for (Registration r : registrationRepository.all()) {
            cnt.merge(r.mountainCode, 1, Integer::sum);
            sum.merge(r.mountainCode, r.tuitionFee, Long::sum);
        }
        view.printStats(cnt, sum, mountainRepository.all());
    }

    // ---------- Helpers ----------
    private String askValid(String label, java.util.function.Predicate<String> rule) {
        while (true) {
            String s = view.ask(label).trim();
            if (rule.test(s)) return s;
            view.msg("Invalid. Try again.");
        }
    }

    private String askMountainCode() {
        view.listMountains(mountainRepository.all());
        while (true) {
            String c = view.ask("Mountain code: ").toUpperCase();
            Mountain m = mountainRepository.get(c);
            if (m != null) return c;
            view.msg("Invalid code.");
        }
    }
}
