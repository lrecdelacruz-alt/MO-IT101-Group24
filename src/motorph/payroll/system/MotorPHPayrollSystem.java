/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorph.payroll.system;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MotorPHPayrollSystem {

    
    static String empFile = "Employee Details.csv";
    static String attFile = "AttendanceRecord.csv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MotorPH Payroll System!");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.println("---------------");

        // Employee Login Process 
        if (username.equals("Employee") && password.equals("0000")) {

            System.out.println("\n[1] View My Details");
            System.out.println("[2] Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {

                System.out.print("\nEnter Employee Number: ");
                String inputId = scanner.nextLine().trim();

                String employeeNumber = "";
                String firstName      = "";
                String lastName       = "";
                String birthday       = "";
                boolean found         = false;

                // look for the employee in the csv
                try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {
                    br.readLine(); // skip the header row
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] col = splitCSV(line);
                        String csvId = col[0].trim().replace(".0", "");
                        if (csvId.equals(inputId)) {
                            employeeNumber = csvId;
                            lastName       = col[1].trim();
                            firstName      = col[2].trim();
                            birthday       = col[3].trim();
                            found          = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file.");
                }

                if (found) {
                    System.out.println("\n===================================");
                    System.out.println("Employee #    : " + employeeNumber);
                    System.out.println("Employee Name : " + firstName + " " + lastName);
                    System.out.println("Birthday      : " + birthday);
                    System.out.println("===================================");
                } else {
                    System.out.println("Employee number does not exist.");
                }

            } else if (choice.equals("2")) {
                System.out.println("Exiting the program. Goodbye!");

            } else {
                System.out.println("Invalid option.");
            }

        // For PayrollStaff Login
        } else if (username.equals("PayrollStaff") && password.equals("1234")) {

            System.out.println("\n[1] Process Payroll");
            System.out.println("[2] Exit");
            System.out.print("Choose an option: ");
            String mainChoice = scanner.nextLine().trim();

            if (mainChoice.equals("1")) {

                System.out.println("\n[1] One Employee");
                System.out.println("[2] All Employees");
                System.out.println("[3] Exit Payroll");
                System.out.print("Choose an option: ");
                String subChoice = scanner.nextLine().trim();

                if (subChoice.equals("1")) {

                    System.out.print("\nEnter Employee Number: ");
                    String inputId = scanner.nextLine().trim();
                    processPayroll(inputId);

                } else if (subChoice.equals("2")) {

                    // reads the file to execute the "ProcessPayroll" option for each employee
                    try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {
                        br.readLine(); 
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] col = splitCSV(line);
                            String empId = col[0].trim().replace(".0", "");
                            processPayroll(empId);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading employee file.");
                    }

                } else if (subChoice.equals("3")) {
                    System.out.println("Exiting payroll.");

                } else {
                    System.out.println("Invalid option.");
                }

            } else if (mainChoice.equals("2")) {
                System.out.println("Exiting the program. Goodbye!");

            } else {
                System.out.println("Invalid option.");
            }

        } else {
            
            System.out.println("Incorrect username and/or password.");
        }

        scanner.close();
    }

    // this method handles everything for one employee
    // we pass the employee ID and it finds them, reads their attendance, then prints the payroll
    static void processPayroll(String inputId) {

        String employeeNumber = "";
        String lastName       = "";
        String firstName      = "";
        String birthday       = "";
        double basicSalary    = 0;
        double hourlyRate     = 0;
        boolean found         = false;

        try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] col = splitCSV(line);
                String csvId = col[0].trim().replace(".0", ""); 
                if (csvId.equals(inputId)) {
                    employeeNumber = csvId;
                    lastName       = col[1].trim();
                    firstName      = col[2].trim();
                    birthday       = col[3].trim();
                    basicSalary    = Double.parseDouble(col[13].trim().replace(",", "")); // remove comma from numbers 
                    hourlyRate     = basicSalary / 21.0 / 8.0; 
                    found          = true;
                    break; 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading employee file.");
        }
        
        if (!found) {
            System.out.println("Employee number does not exist."); // Executes only if the ID that the user enters is not in the files
            return;
        }

        // lists to store the attendance records for this employee
        ArrayList<Integer> dates   = new ArrayList<>();
        ArrayList<Integer> logins  = new ArrayList<>();
        ArrayList<Integer> logouts = new ArrayList<>();

        // open the attendance csv and collect only the rows that belong to this employee
        try (BufferedReader br = new BufferedReader(new FileReader(attFile))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] col = line.split(",");
                if (col.length < 6) continue; // skip incomplete rows
                if (!col[0].trim().equals(inputId)) continue; // skip other employees

                // convert date from MM/DD/YYYY to a number like 20240603 so its easier to work with
                String[] dp = col[3].trim().split("/");
                int dateInt = Integer.parseInt(dp[2]) * 10000
                           + Integer.parseInt(dp[0]) * 100
                           + Integer.parseInt(dp[1]);

                String[] tin  = col[4].trim().split(":");
                String[] tout = col[5].trim().split(":");
                int loginMin  = Integer.parseInt(tin[0])  * 60 + Integer.parseInt(tin[1]);
                int logoutMin = Integer.parseInt(tout[0]) * 60 + Integer.parseInt(tout[1]);

                dates.add(dateInt);
                logins.add(loginMin);
                logouts.add(logoutMin);
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance file.");
        }

        int[]    months     = {6, 7, 8, 9, 10, 11, 12};
        String[] monthNames = {"June", "July", "August", "September",
                               "October", "November", "December"};

        // employee info header
        System.out.println("\n====== PAYROLL REPORT ======");
        System.out.println("Employee #    : " + employeeNumber);
        System.out.println("Employee Name : " + firstName + " " + lastName);
        System.out.println("Birthday      : " + birthday);
        System.out.println("Basic Salary  : PHP " + basicSalary);
        System.out.println("Hourly Rate   : PHP " + hourlyRate);
        System.out.println("===========================");

       
        for (int mi = 0; mi < months.length; mi++) { // creates a loop to compute the payroll through each month
            int month = months[mi];

            // reset the totals 
            double hoursC1 = 0, hoursC2 = 0;
            double grossC1 = 0, grossC2 = 0;

            for (int d = 0; d < dates.size(); d++) {
                int dateInt = dates.get(d);
                int m   = (dateInt / 100) % 100; 
                int day = dateInt % 100;          
                if (m != month) continue;         

                double hrs = computeHoursWorked(logins.get(d), logouts.get(d));

                if (day <= 15) { // first cutoff
                    hoursC1 += hrs;
                    grossC1 += hrs * hourlyRate;
                } else { // second cutoff
                    hoursC2 += hrs;
                    grossC2 += hrs * hourlyRate;
                }
            }

            double monthlyGross    = grossC1 + grossC2;
            double sss             = computeSSS(basicSalary);
            double philHealth      = computePhilHealth(basicSalary);
            double pagIBIG         = computePagIBIG(basicSalary);
            double totalDeductions = sss + philHealth + pagIBIG;
            double taxableIncome   = monthlyGross - totalDeductions; 
            double tax             = computeWithholdingTax(taxableIncome);
            totalDeductions       += tax; 
            double netPay          = monthlyGross - totalDeductions;

            // print the monthly payroll breakdown
            System.out.println("\n-- " + monthNames[mi] + " 2024 --");
            System.out.println("  1st Cutoff (Days 1-15)");
            System.out.println("    Hours Worked : " + hoursC1);
            System.out.println("    Gross Pay    : PHP " + grossC1);
            System.out.println("  2nd Cutoff (Days 16-31)");
            System.out.println("    Hours Worked : " + hoursC2);
            System.out.println("    Gross Pay    : PHP " + grossC2);
            System.out.println("  Total Gross    : PHP " + monthlyGross);
            System.out.println("  -- Deductions --");
            System.out.println("    SSS          : PHP " + sss);
            System.out.println("    PhilHealth   : PHP " + philHealth);
            System.out.println("    Pag-IBIG     : PHP " + pagIBIG);
            System.out.println("    Tax          : PHP " + tax);
            System.out.println("    Total        : PHP " + totalDeductions);
            System.out.println("  Net Salary     : PHP " + netPay);
            System.out.println("---------------------------------------");
        }
    }

    // This method computes how many hours the employee actually worked
    static double computeHoursWorked(int loginMin, int logoutMin) {
        int workStart = 8 * 60;     
        int workEnd   = 17 * 60;    
        int graceEnd  = 8 * 60 + 5; 

        if (loginMin <= graceEnd) loginMin = workStart;

        int effectiveStart = Math.max(loginMin, workStart);
        int effectiveEnd   = Math.min(logoutMin, workEnd);
        int spanMin        = effectiveEnd - effectiveStart;
        if (spanMin <= 0) return 0.0;

        return Math.max(0.0, spanMin / 60.0 - 1.0);
    }

    /*
    This method enforces a smarter way to read the csv file 
    by splitting it into columns and respecting quotation marks
    */
   
    static String[] splitCSV(String line) {
        ArrayList<String> columns = new ArrayList<>();
        boolean insideQuotes = false;
        String current = "";
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                insideQuotes = !insideQuotes; 
            } else if (c == ',' && !insideQuotes) {
                columns.add(current); 
                current = "";
            } else {
                current = current + c; 
            }
        }
        columns.add(current); 
        return columns.toArray(new String[0]);
    }

    static double computeSSS(double basicSalary) {
        double msc;
        if (basicSalary < 3250)      msc = 3000;
        else if (basicSalary < 3750) msc = 3500;
        else                         msc = Math.min(Math.round(basicSalary / 500.0) * 500.0, 30000);
        return msc * 0.045;
    }

    static double computePhilHealth(double basicSalary) {
        double c = basicSalary * 0.025;
        if (c < 250.0)  c = 250.0;
        if (c > 2500.0) c = 2500.0;
        return c;
    }
    
    static double computePagIBIG(double basicSalary) {
        double rate = (basicSalary > 1500) ? 0.02 : 0.01;
        return Math.min(basicSalary * rate, 100.0);
    }


    static double computeWithholdingTax(double taxableIncome) {
        if (taxableIncome <= 20832)       return 0.0;
        else if (taxableIncome <= 33332)  return (taxableIncome - 20833) * 0.20;
        else if (taxableIncome <= 66666)  return 2500.0 + (taxableIncome - 33333) * 0.25;
        else if (taxableIncome <= 166666) return 10833.33 + (taxableIncome - 66667) * 0.30;
        else if (taxableIncome <= 666666) return 40833.33 + (taxableIncome - 166667) * 0.32;
        else                              return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}