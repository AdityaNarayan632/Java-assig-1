import java.util.*;

abstract class Person {
    protected String name;
    protected String email;

    public Person() {}

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void displayInfo(); 
}

class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student() {}

    public Student(int rollNo, String name, String email, String course, double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) {
        this.marks = marks;
        calculateGrade();
    }

    private void calculateGrade() {
        if (marks >= 75) grade = 'A';
        else if (marks >= 60) grade = 'B';
        else if (marks >= 50) grade = 'C';
        else if (marks >= 35) grade = 'D';
        else grade = 'F';
    }

    @Override
    public void displayInfo() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Email   : " + email);
        System.out.println("Course  : " + course);
        System.out.println("Marks   : " + marks);
        System.out.println("Grade   : " + grade);
        System.out.println("--------------------------------------");
    }

    public void displayInfo(boolean brief) {
        if (brief) {
            System.out.println(rollNo + " | " + name + " | " + email);
        } else {
            displayInfo();
        }
    }
}

interface RecordActions {
    boolean addStudent(Student s);
    boolean deleteStudent(int rollNo);
    boolean updateStudent(int rollNo, Student s);
    Student searchStudent(int rollNo);
    List<Student> viewAllStudents();
}

class StudentManager implements RecordActions {

    private Map<Integer, Student> studentMap = new HashMap<>();

    @Override
    public boolean addStudent(Student s) {
        if (studentMap.containsKey(s.getRollNo())) {
            return false;     
        }
        studentMap.put(s.getRollNo(), s);
        return true;
    }

    @Override
    public boolean deleteStudent(int rollNo) {
        return studentMap.remove(rollNo) != null;
    }

    @Override
    public boolean updateStudent(int rollNo, Student s) {
        if (!studentMap.containsKey(rollNo)) return false;
        s.setRollNo(rollNo);
        studentMap.put(rollNo, s);
        return true;
    }

    @Override
    public Student searchStudent(int rollNo) {
        return studentMap.get(rollNo);
    }

    @Override
    public List<Student> viewAllStudents() {
        List<Student> list = new ArrayList<>(studentMap.values());
        list.sort(Comparator.comparingInt(Student::getRollNo));
        return list;
    }
}

public class Main {

    public static void main(String[] args) {

        StudentManager manager = new StudentManager();
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\n========== STUDENT MANAGEMENT MENU ==========");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Search Student");
            System.out.println("5. View All Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    Student add = readStudent(sc, -1);
                    boolean added = manager.addStudent(add);
                    System.out.println(added ? "Student Added." : "Duplicate Roll No! Not Added.");
                    break;

                case 2:
                    System.out.print("Enter Roll No to delete: ");
                    int del = readInt(sc);
                    System.out.println(manager.deleteStudent(del) ? "Deleted." : "Not Found.");
                    break;

                case 3:
                    System.out.print("Enter Roll No to update: ");
                    int r = readInt(sc);
                    if (manager.searchStudent(r) == null) {
                        System.out.println("No student found.");
                    } else {
                        Student up = readStudent(sc, r);
                        System.out.println(manager.updateStudent(r, up) ? "Updated." : "Failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Roll No: ");
                    int sr = readInt(sc);
                    Student found = manager.searchStudent(sr);
                    if (found != null) found.displayInfo();
                    else System.out.println("Not Found.");
                    break;

                case 5:
                    List<Student> list = manager.viewAllStudents();
                    if (list.isEmpty()) System.out.println("No records.");
                    else list.forEach(Student::displayInfo);
                    break;

                case 6:
                    run = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
        System.out.println("Program Ended.");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            try {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
                sc.nextLine();
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            } catch (Exception e) {
                System.out.print("Enter valid marks: ");
                sc.nextLine();
            }
        }
    }

    private static Student readStudent(Scanner sc, int fixedRoll) {
        int roll;
        if (fixedRoll == -1) {
            System.out.print("Roll No: ");
            roll = readInt(sc);
        } else {
            roll = fixedRoll;
            System.out.println("Updating Roll No: " + roll);
        }

        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Course: ");
        String course = sc.nextLine();
        System.out.print("Marks (0-100): ");
        double marks = readDouble(sc);

        return new Student(roll, name, email, course, marks);
    }
}
