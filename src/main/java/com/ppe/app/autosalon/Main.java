package com.ppe.app.autosalon;

import com.ppe.app.autosalon.repository.Database;
import com.ppe.app.autosalon.service.AutoSalon;
import com.ppe.app.autosalon.service.AutoSalonDb;
import com.ppe.app.autosalon.view.ApplicationVew;
import com.ppe.app.autosalon.view.ConsoleApp;
import com.ppe.app.autosalon.view.FrameApp;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Database db;
        try {
            db = new Database();
            db.initDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Scanner in = new Scanner(System.in);

        ApplicationVew app = null;
        AutoSalon autoSalon = new AutoSalonDb(db);

        while (true) {
            System.out.println("Do you want to start console app or GUI app? " +
                    "Select by typing 1 or 2:\n\n1) Console app\n2) GUI app:");
            String ans = in.nextLine().trim();
            if (ans.startsWith("1")) {
                app = new ConsoleApp(autoSalon);
                break;
            } else if (ans.startsWith("2")) {
                app = new FrameApp(autoSalon);
                break;
            } else if (ans.equals("exit")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Your input is incorrect!. Type 1 or 2!");
            }

        }

        if (app != null)
            app.start();

    }
}
