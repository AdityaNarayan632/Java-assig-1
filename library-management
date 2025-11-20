import java.util.ArrayList;
import java.util.Scanner;

public class LibrarySystem {
    static class Book {
        int id;
        String title;
        String author;
        boolean issued;

        Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.issued = false;
        }
    }

    static class Member {
        int id;
        String name;

        Member(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Member> members = new ArrayList<>();
    static int bookId = 100;
    static int memberId = 100;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1 Add Book");
            System.out.println("2 Add Member");
            System.out.println("3 Issue Book");
            System.out.println("4 Return Book");
            System.out.println("5 View Books");
            System.out.println("6 View Members");
            System.out.println("7 Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                System.out.print("Enter title: ");
                String t = sc.nextLine();
                System.out.print("Enter author: ");
                String a = sc.nextLine();
                bookId++;
                books.add(new Book(bookId, t, a));
                System.out.println("Book added");
            }

            else if (ch == 2) {
                System.out.print("Enter member name: ");
                String n = sc.nextLine();
                memberId++;
                members.add(new Member(memberId, n));
                System.out.println("Member added");
            }

            else if (ch == 3) {
                System.out.print("Enter book id: ");
                int bid = sc.nextInt();
                System.out.print("Enter member id: ");
                int mid = sc.nextInt();
                Book b = findBook(bid);
                Member m = findMember(mid);
                if (b == null || m == null) System.out.println("Invalid ID");
                else if (b.issued) System.out.println("Book already issued");
                else {
                    b.issued = true;
                    System.out.println("Book issued");
                }
            }

            else if (ch == 4) {
                System.out.print("Enter book id: ");
                int bid = sc.nextInt();
                Book b = findBook(bid);
                if (b == null) System.out.println("Invalid ID");
                else if (!b.issued) System.out.println("Book not issued");
                else {
                    b.issued = false;
                    System.out.println("Book returned");
                }
            }

            else if (ch == 5) {
                for (Book b : books)
                    System.out.println(b.id + " " + b.title + " " + b.author + " Issued:" + b.issued);
            }

            else if (ch == 6) {
                for (Member m : members)
                    System.out.println(m.id + " " + m.name);
            }

            else if (ch == 7) {
                System.out.println("Exiting");
                break;
            }

            else System.out.println("Invalid");
        }
        sc.close();
    }

    static Book findBook(int id) {
        for (Book b : books)
            if (b.id == id) return b;
        return null;
    }

    static Member findMember(int id) {
        for (Member m : members)
            if (m.id == id) return m;
        return null;
    }
}
