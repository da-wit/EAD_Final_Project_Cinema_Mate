package com.cinemamate.cinema_mate.core.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component
public class BrowserLauncher implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String url = "http://localhost:8080/pages/loginPage/login.html";

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            System.out.println("Desktop API not supported. Please open the following URL manually:");
            System.out.println(url);
        }
    }
}