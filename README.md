Team Members:
(1) Elijah Constatine Dela Cruz
(2) Edgardo De Guzman
(3) Edgar Serona Jr.

Upon opening the program,

It asks for (1) details if you are an employee or the payroll staff, (2) your password, and (3) a display if you entered an incorrect character or value

A. Username:
Employee or PayrollStaff

B. Password:
0000 for employees and 1234 for the payroll staff

If the user enters a wrong username or password, the program will display the message, "Incorrect username and/or password"





The next section of the code is computing the semi-montly salary based on hours worked, which handles the computing how much an employee earns for a given month.

The program only counts hours within the official work window of 8:00 AM to 5:00 PM. 

Overtime is not included.

To make time comparisons easier in the code, all times are converted from HH:MM format into total minutes from midnight — so 8:00 AM becomes 480, and 5:00 PM becomes 1020.

There is also a 5-minute grace period. If an employee clocks in at 8:05 or earlier, the program treats their login as exactly 8:00 AM so they are not penalized. After the start and end times are set, one hour is always subtracted for the lunch break, giving a maximum of 8 hours per day.


Each month is split into two payroll periods. Days 1 to 15 fall under the first cutoff, and days 16 onwards fall under the second cutoff. The program separates the hours and gross pay for each period as it reads through the attendance records. 


For computing the deductions, both cutoff amounts are added together first to get the monthly gross before any deductions are computed. From there, SSS, PhilHealth, and Pag-IBIG are deducted. The remaining amount becomes the taxable income, which is then used to calculate the withholding tax based on TRAIN Law brackets. Finally, all four deductions are combined and subtracted from the monthly gross to get the net pay.


The payroll report is printed from June to December 2024. For each month, the output shows the hours worked and gross pay per cutoff, the combined monthly gross, each deduction broken down individually, and the final net salary.

If the username is "PayrollStaff", display options: 1. Process Payroll 2. Exit the program

https://docs.google.com/spreadsheets/d/1vzl9uz4tco-TThyqBWqYsEPJDYYldY_D7T7vX7IS2wA/edit?usp=sharing
