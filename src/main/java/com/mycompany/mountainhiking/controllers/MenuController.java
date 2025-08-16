package com.mycompany.mountainhiking.controllers;

import com.mycompany.mountainhiking.repo.RegistrationRepository;
import com.mycompany.mountainhiking.views.ConsoleView;
import java.io.IOException;

public class MenuController {
    private final ConsoleView view;
    private final RegistrationRepository registrationRepository;
    private final RegistrationController registrationController;
    private boolean dirty = false;

    public MenuController(ConsoleView consoleView, RegistrationRepository registrationRepository, RegistrationController registrationController) {
        this.view = consoleView;
        this.registrationRepository = registrationRepository;
        this.registrationController = registrationController;
    }

    public void run() {
        try { registrationRepository.load(); } catch (IOException | ClassNotFoundException ignored) {}
        while (true) {
            switch (view.menu()) {
                case "1" -> dirty |= registrationController.add();
                case "2" -> dirty |= registrationController.update();
                case "3" -> registrationController.display();
                case "4" -> dirty |= registrationController.delete();
                case "5" -> registrationController.search();
                case "6" -> registrationController.filter();
                case "7" -> registrationController.stats();
                case "8" -> save();
                case "9" -> deleteData();
                case "10" -> {
                    exit(); return;
                }
                default -> view.msg("This function is not available.");
            }
        }
    }

    private void save() {
        try { 
            registrationRepository.save();
            dirty = false;
            view.msg("Saved.");
        }
        catch (IOException e) { view.msg("Save failed: " + e.getMessage()); }
    }

    private void exit() {
        if (dirty && view.confirm("Save before exit?")) {
            save();
            view.msg("Saved!");
        }
    }
    
    private void deleteData() {
        try {
            if(registrationRepository.deleteStorageFile());
            view.msg("Deleted Data File!");
        } catch (IOException e) { view.msg("Delete failed: " + e.getMessage()); }
    }
}
