package com.example.financesecureapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@RestController
public class CustomerController {

    // SCHWACHSTELLE 1: Hartcodiertes Geheimnis (Soll von Semgrep & SpotBugs gefunden)
    private static final String API_SECRET = "SuperSecretFinanceKey2026!";

    @GetMapping("/api/customer")
    public String getCustomerData(@RequestParam String customerId) {

        // SCHWACHSTELLE 2: SQL Injection (Soll von SpotBugs und DAST/ZAP gefunden)
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            Statement stmt = conn.createStatement();
            // Gefährlich: Ungefilterter Input direkt in der Query
            String query = "SELECT * FROM customers WHERE id = '" + customerId + "'";
            stmt.executeQuery(query);

            return "Daten für Kunde " + customerId + " erfolgreich geladen.";
        } catch (Exception e) {
            return "Fehler beim Laden der Daten.";
        }
    }
}