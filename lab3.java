import java.util.*;
class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String msg) { super(msg); }
}
abstract class Person {
    protected String name;
    protected String email;
    public Person() {}
    public Person(String name, String email) { this.name = name; this.email = email; }
    public abstract void displayInfo();
}
class Student extends Person {
    private Integer rollNo;
    private String course;
    private Double marks;
    private char grade;
    public Student() {}
    public Student(Integer rollNo, String name, String email, String course, Double marks) {
        super(name, email);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }
    public Integer getRollNo() { return rollNo; }
    public void setRollNo(Integer rollNo) { this.rollNo = rollNo; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; calculateGrade(); }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    private void calculateGrade() {
        if (marks >= 75) grade = 'A';
        else if (marks >= 60) grade = 'B';
        else if (marks >= 50) grade = 'C';
        else if (marks >= 35) grade = 'D';
        else grade = 'F';
    }
    public void displayInfo() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println();
    }
}
interface RecordActions {
    boolean addStudent(Student s);
    boolean deleteStudent(Integer rollNo) throws StudentNotFoundException;
    boolean updateStudent(Integer rollNo, Student s) throws StudentNotFoundException;
    Student searchStudent(Integer rollNo) throws StudentNotFoundException;
    List<Student> viewAllStudents();
}
class StudentManager implements RecordActions {
    private final Map<Integer, Student> map = new HashMap<>();
    public synchronized boolean addStudent(Student s) {
        if (s == null || s.getRollNo() == null) return false;
        if (map.containsKey(s.getRollNo())) return false;
        map.put(s.getRollNo(), s);
        return true;
    }
    public synchronized boolean deleteStudent(Integer rollNo) throws StudentNotFoundException {
        if (!map.containsKey(rollNo)) throw new StudentNotFoundException("Student not found: " + rollNo);
        map.remove(rollNo);
        return true;
    }
    public synchronized boolean updateStudent(Integer rollNo, Student s) throws StudentNotFoundException {
        if (!map.containsKey(rollNo)) throw new StudentNotFoundException("Student not found: " + rollNo);
        s.setRollNo(rollNo);
        map.put(rollNo, s);
        return true;
    }
    public synchronized Student searchStudent(Integer rollNo) throws StudentNotFoundException {
        Student s = map.get(rollNo);
        if (s == null) throw new StudentNotFoundException("Student not found: " + rollNo);
        return s;
    }
    public synchronized List<Student> viewAllStudents() {
        List<Student> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparingInt(st -> st.getRollNo()));
        return list;
    }
}
class Loader implements Runnable {
    private final int steps;
    public Loader(int steps) { this.steps = steps; }
    public void run() {
        try {
            System.out.print("Loading");
            for (int i = 0; i < steps; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println("Loading interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("1.Add Student  2.Delete Student  3.Update Student  4.Search Student  5.View All  6.Exit");
            System.out.print("Choice: ");
            int choice = safeReadInt();
            switch (choice) {
                case 1:
                    addStudentFlow();
                    break;
                case 2:
                    deleteStudentFlow();
                    break;
                case 3:
                    updateStudentFlow();
                    break;
                case 4:
                    searchStudentFlow();
                    break;
                case 5:
                    viewAllFlow();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
        System.out.println("Program execution completed.");
    }
    private static void addStudentFlow() {
        try {
            Integer roll = readInteger("Enter Roll No (Integer): ");
            String name = readNonEmpty("Enter Name: ");
            String email = readNonEmpty("Enter Email: ");
            String course = readNonEmpty("Enter Course: ");
            Double marks = readDoubleInRange("Enter Marks: ", 0.0, 100.0);
            Student s = new Student(roll, name, email, course, marks);
            Thread t = new Thread(new Loader(5));
            t.start();
            t.join();
            boolean added = manager.addStudent(s);
            if (!added) System.out.println("Add failed: duplicate roll no.");
            else {
                s.displayInfo();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Operation interrupted");
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
    private static void deleteStudentFlow() {
        try {
            Integer roll = readInteger("Enter Roll No to delete: ");
            Thread t = new Thread(new Loader(3));
            t.start();
            t.join();
            boolean ok = manager.deleteStudent(roll);
            if (ok) System.out.println("Deleted: " + roll);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            Thread.currentThread().interrupt();
        }
    }
    private static void updateStudentFlow() {
        try {
            Integer roll = readInteger("Enter Roll No to update: ");
            Student existing = manager.searchStudent(roll);
            System.out.println("Existing record:");
            existing.displayInfo();
            String name = readNonEmpty("Enter New Name: ");
            String email = readNonEmpty("Enter New Email: ");
            String course = readNonEmpty("Enter New Course: ");
            Double marks = readDoubleInRange("Enter New Marks: ", 0.0, 100.0);
            Student updated = new Student(roll, name, email, course, marks);
            Thread t = new Thread(new Loader(4));
            t.start();
            t.join();
            boolean ok = manager.updateStudent(roll, updated);
            if (ok) updated.displayInfo();
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
    private static void searchStudentFlow() {
        try {
            Integer roll = readInteger("Enter Roll No to search: ");
            Thread t = new Thread(new Loader(2));
            t.start();
            t.join();
            Student s = manager.searchStudent(roll);
            s.displayInfo();
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            Thread.currentThread().interrupt();
        }
    }
    private static void viewAllFlow() {
        Thread t = new Thread(new Loader(3));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            Thread.currentThread().interrupt();
        }
        List<Student> all = manager.viewAllStudents();
        if (all.isEmpty()) System.out.println("No records");
        else all.forEach(Student::displayInfo);
    }
    private static Integer readInteger(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) throw new IllegalArgumentException("Field cannot be empty");
        Integer val = Integer.valueOf(line);
        return val;
    }
    private static Double readDoubleInRange(String prompt, double min, double max) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) throw new IllegalArgumentException("Field cannot be empty");
        Double val = Double.valueOf(line);
        if (val < min || val > max) throw new IllegalArgumentException("Marks must be between " + min + " and " + max);
        return val;
    }
    private static String readNonEmpty(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) throw new IllegalArgumentException("Field cannot be empty");
        return line;
    }
    private static int safeReadInt() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                if (line.isEmpty()) return -1;
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }
}
