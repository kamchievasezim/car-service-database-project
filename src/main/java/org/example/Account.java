package org.example;

import org.example.Owner.Owner;
import org.example.Manager.Manager;
import org.example.Mechanic.Mechanic;
import org.example.Specialist.Specialist;
import org.example.Accountant.Accountant;

import java.util.Scanner;

public class Account {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Signin signin = new Signin();

        System.out.print("Enter your position: ");
        String position = scanner.next();

        switch (position) {
            case "owner":
                Owner owner = new Owner();
                System.out.print("Enter your password: ");
                String passwordOwner = scanner.next();
                if (passwordOwner.equals(signin.ownerPassword)) {
                    owner.menu();
                    System.out.println(owner.getOption());
                } else {
                    System.out.println("Incorrect password, try again.");
                }
                break;

            case "manager":
                Manager manager = new Manager();
                System.out.print("Enter your password: ");
                String passwordManager = scanner.next();
                if (passwordManager.equals(signin.managerPassword)) {
                    manager.menu();
                    System.out.println(manager.getOption());
                } else {
                    System.out.println("Incorrect password, try again.");
                }
                break;

            case "mechanic":
                Mechanic mechanic = new Mechanic();
                System.out.print("Enter your password: ");
                String passwordMechanic = scanner.next();
                if (passwordMechanic.equals(signin.mechanicPassword)) {
                    mechanic.menu();
                    System.out.println(mechanic.getOption());
                } else {
                    System.out.println("Incorrect password, try again.");
                }
                break;

            case "specialist":
                Specialist specialist = new Specialist();
                System.out.print("Enter your password: ");
                String passwordSpecialist = scanner.next();
                if (passwordSpecialist.equals(signin.specialistPassword)) {
                    specialist.menu();
                    System.out.println(specialist.getOption());
                } else {
                    System.out.println("Incorrect password, try again.");
                }
                break;

            case "accountant":
                Accountant accountant = new Accountant();
                System.out.print("Enter your password: ");
                String passwordAccountant = scanner.next();
                if (passwordAccountant.equals(signin.accountantPassword)) {
                    accountant.menu();
                    System.out.println(accountant.getOption());
                } else {
                    System.out.println("Incorrect password, try again.");
                }
                break;

            default:
                System.out.println("Did not find your account, try again.");
                break;
        }
    }
}
