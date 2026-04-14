

This project is a simple command-line expense tracking application built in Java. It allows users to record daily expenses, store them in a file, and generate basic financial summaries.

The goal of the project was to practice working with file handling, structured data, and basic analytics in Java, while building something practical and easy to use.

 Features:

- Add expenses with a description, category, and amount
- Automatically stores expenses with the current date
- View all recorded expenses
- Calculate total spending
- View spending grouped by category
- View spending grouped by month

Technologies Used:

- Java
- File handling using FileWriter and Scanner
- HashMap for data aggregation

   How It Works :

Each expense is saved to a text file in the following format: 
date,description,category,amount

The application reads this file to generate summaries such as total spending, category totals, and monthly spending.

## How to Run

1. Open the project in NetBeans or any Java IDE
2. Run the Main.java file
3. Follow the menu options in the console

 Example :
 --- CATEGORY SUMMARY ---
Food: R500
Transport: R200
Entertainment: R300
Other: R100


Notes

- Data is stored locally in a file named expenses.txt
- The application handles basic input validation and skips invalid data entries

Author : 
Somila Mancoba

