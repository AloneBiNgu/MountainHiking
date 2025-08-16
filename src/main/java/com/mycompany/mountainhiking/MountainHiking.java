package com.mycompany.mountainhiking;

import com.mycompany.mountainhiking.controllers.MenuController;
import com.mycompany.mountainhiking.controllers.RegistrationController;
import com.mycompany.mountainhiking.repo.ClasspathMountainRepository;
import com.mycompany.mountainhiking.repo.FileRegistrationRepository;
import com.mycompany.mountainhiking.repo.MountainRepository;
import com.mycompany.mountainhiking.repo.RegistrationRepository;
import com.mycompany.mountainhiking.services.TelcoPolicy;
import com.mycompany.mountainhiking.services.TuitionCalculator;
import com.mycompany.mountainhiking.services.Validator;
import com.mycompany.mountainhiking.views.ConsoleView;

public class MountainHiking {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        MountainRepository mountainRepo = new ClasspathMountainRepository();
        RegistrationRepository regRepo = new FileRegistrationRepository();
        Validator validator = new Validator();
        TuitionCalculator calc = new TuitionCalculator(new TelcoPolicy());
        RegistrationController regController = new RegistrationController(view, regRepo, mountainRepo, validator, calc);
        MenuController menu = new MenuController(view, regRepo, regController);
        menu.run();
    }
}
