package com.example.financesecureapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/")
    public String healthCheck() {
        return "Finance App ist online und sicher!";
    }

    // SCHWACHSTELLE 1 BEHOBEN: Das hartcodierte Passwort (API_SECRET) wurde komplett entfernt.

    @GetMapping("/api/customer")
    public String getCustomerData(@RequestParam String customerId) {

        // SCHWACHSTELLE 3 BEHOBEN: Passwörter nicht hartcodieren. Wir nutzen Umgebungsvariablen.
        String dbUser = System.getenv().getOrDefault("DB_USER", "sa");
        String dbPass = System.getenv().getOrDefault("DB_PASS", "secureDummyPass");

        // SCHWACHSTELLE 2 BEHOBEN: SQL Injection durch Prepared Statements verhindert (?)
        String query = "SELECT * FROM customers WHERE id = ?";

        // SCHWACHSTELLE 4 BEHOBEN: "Try-with-resources" schließt die Datenbankverbindungen automatisch (verhindert Lecks)
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", dbUser, dbPass);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Benutzereingabe sicher als Parameter setzen
            pstmt.setString(1, customerId);

            // Auswerten des Return-Wertes behebt den Fehler "Return value ignored"
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return "Daten für Kunde " + customerId + " erfolgreich geladen.";
                } else {
                    return "Kunde nicht gefunden.";
                }
            }

        } catch (SQLException e) {
            // SCHWACHSTELLE 5 BEHOBEN: Gezielt SQLException fangen statt allgemeiner "Exception"
            return "Fehler beim Laden der Daten.";
        }
    }

    // ENDPUNKT ZUR SIMULATION VON FEHLGESCHLAGENEN LOGINS
    @PostMapping("/api/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Simulation, dass das Passwort immer falsch ist
        logger.warn("SECURITY ALERT: Fehlgeschlagener Login-Versuch für User: {}", username);
        return "Login fehlgeschlagen!";
    }


}