/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorphg24;

/**
 *
 * @author EJ Serona
 */

// this uses a scanner = login process of the employee and the payroll staff

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 

public class MotorPHEmployeeDetails {

    static void print(String word) {
        System.out.print(word);
    }

    static void println(String word) {
        System.out.println(word);
    }

    static void println(int num1) {
        System.out.print(num1);
    }

    public static void main(String[] args) {
        
        try {
            FileReader reader = new FileReader("Copy of MotorPH_Employee Data - Employee Details.csv");
            int data;
            while ((data = reader.read()) != -1) {
                System.out.print((char) data);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
    }

        String empFile = "Copy of MotorPH_Employee Data - Employee Details.csv";
        
        System.out.println(); // just to enter below the data
        System.out.println(); // extra space
        
        // Greetings: String text = "Welcome to MotorPH!"
        System.out.println("Welcome to MotorPH!");

        Scanner scanner = new Scanner(System.in);
               
        print("Enter Username: ");
            String Username = scanner.nextLine();
       
        print("Enter Password: ");
            String Password = scanner.nextLine();
        println ("---------------");
        
        switch (Username + Password) {
            
     case "Employee0000": {
         
         print("Enter Employee Number: ");
         String Userinput = scanner.nextLine();
         
         String employeeNumber = "";
         String firstName = "";
         String lastName = "";
         String birthday = "";
         boolean found = false;
                 
         try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {
         br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[0].equals(Userinput)) {
                    employeeNumber = values[0];
                    firstName = values[1];
                    lastName = values[2];
                    birthday = values[3];
                    found = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        if (found) {
            System.out.println("\n===================================");
            System.out.println("Employee # : " + employeeNumber);
            System.out.println("Employee Name : " + lastName + ", " + firstName);
            System.out.println("Birthday : " + birthday);
            System.out.println("===================================");
        } else {
            System.out.println("Employee not found.");
        }

        break;
    }

    case "PayrollStaff1234": {
        System.out.println("Welcome, Payroll Staff!");
        break;
    }

    default:
        print("Invalid Username or Password!");
}
    } 
}
