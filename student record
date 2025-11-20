import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Person {
    protected String name;

    public Person() {
        this.name = "";
    }

    public Person(String name) {
        this.name = name;
    }
}

class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student() {
        super();
        this.rollNo = 0;
        this.course = "";
        this.marks = 0.0;
        this.grade = 'N'; 
    }

    public Student(int rollNo, String name, String course, double marks) {
        super(name);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public void inputDetails(Scanner sc) {
        System.out.print("Enter Roll No: ");
        while (true) {
            try {
                this.rollNo = sc.nextInt();
                sc.nextLine(); 
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter an integer for Roll No: ");
                sc.nextLine();
            }
        }

        System.out.print("Enter Name: ");
        this.name = sc.nextLine().trim();

        System.out.print("Enter Course: ");
        this.course = sc.nextLine().trim();

        System.out.print("Enter Marks (0 - 100): ");
        while (true) {
            try {
                this.marks = sc.nextDouble();
                sc.nextLine(); /
                if (this.marks < 0 || this.marks > 100) {
                    System.out.print("Marks must be between 0 and 100. Re-enter: ");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter numeric marks between 0 and 100: ");
                sc.nextLine();
            }
        }
        calculateGrade();
    }

    public void calculateGrade() {
        if (marks >= 75.0) {
            grade = 'A';
        } else if (marks >= 60.0) {
            grade = 'B';
        } else if (marks >= 50.0) {
            grade = 'C';
        } else if (marks >= 35.0) {
            grade = 'D';
        } else {
            grade = 'F';
        }
    }

    public void displayDetails() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name   : " + name);
        System.out.println("Course : " + course);
        System.out.println("Marks  : " + marks);
        System.out.println("Grade  : " + grade);
        System.out.println("-------------------------");
    }

    public int getRollNo() { return rollNo; }
    public double getMarks() { return marks; }
    public char getGrade() { return grade; }
}

public class StudentRecordApp {
    private ArrayList<Student> students;
    private Scanner sc;

    public StudentRecordApp() {
        students = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("===== Student Record Menu =====");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    public void run() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readIntSafe();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAll();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2 or 3.");
            }
        }
        sc.close();
    }

    private int readIntSafe() {
        while (true) {
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter an integer: ");
                sc.nextLine();
            }
        }
    }

    private void addStudent() {
        Student s = new Student();
        s.inputDetails(sc);
        students.add(s);
        System.out.println("Student added successfully.\n");
    }

    private void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No student records available.\n");
            return;
        }
        System.out.println("\n---- All Student Records ----");
        for (Student s : students) {
            s.displayDetails();
        }
    }

    public static void main(String[] args) {
        StudentRecordApp app = new StudentRecordApp();
        app.run();
    }
}
