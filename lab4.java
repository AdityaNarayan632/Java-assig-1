import java.util.*;

class Student {
    private int rollNo;
    private String name;
    private String email;
    private String course;
    private double marks;

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    public void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("----------------------------------");
    }
}

class StudentManagementSystem {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Name");
            System.out.println("4. Delete Student by Name");
            System.out.println("5. Sort by Marks (Descending)");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int ch = readInt();

            switch (ch) {
                case 1: addStudent(); break;
                case 2: viewAll(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 5: sortStudents(); break;
                case 6: run = false; break;
                default: System.out.println("Invalid choice");
            }
        }
        System.out.println("Program ended.");
    }

    private static void addStudent() {
        System.out.print("Enter Roll No: "); 
        int roll = readInt();
        System.out.print("Enter Name: "); 
        String name = sc.nextLine().trim();
        System.out.print("Enter Email: "); 
        String email = sc.nextLine().trim();
        System.out.print("Enter Course: "); 
        String course = sc.nextLine().trim();
        System.out.print("Enter Marks: "); 
        double marks = readDouble();

        students.add(new Student(roll, name, email, course, marks));
        System.out.println("Student added.\n");
    }

    private static void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No records.\n");
            return;
        }
        for (Student s : students) s.display();
    }

    private static void searchStudent() {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(name)) {
                s.display();
                found = true;
            }
        }
        if (!found) System.out.println("No matching student.\n");
    }

    private static void deleteStudent() {
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine().trim().toLowerCase();
        Iterator<Student> it = students.iterator();
        boolean removed = false;

        while (it.hasNext()) {
            if (it.next().getName().toLowerCase().equals(name)) {
                it.remove();
                removed = true;
            }
        }
        System.out.println(removed ? "Deleted.\n" : "Not found.\n");
    }

    private static void sortStudents() {
        students.sort((a, b) -> Double.compare(b.getMarks(), a.getMarks()));
        System.out.println("Sorted by marks (descending).\n");
    }

    private static int readInt() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Enter valid number: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Enter valid marks: ");
            }
        }
    }
}
