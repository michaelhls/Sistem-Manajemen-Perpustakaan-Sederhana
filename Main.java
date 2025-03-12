import java.util.Scanner;

abstract class User {
    public abstract void interact();
}

class Admin extends User {
    @Override
    public void interact() {
        System.out.println("Admin sedang mengelola perpustakaan.");
    }
}

class Member extends User {
    @Override
    public void interact() {
        System.out.println("Member sedang meminjam/mengembalikan buku.");
    }
}

class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Judul: " + title + ", Pengarang: " + author + ", Tersedia: " + (isAvailable ? "Ya" : "Tidak");
    }
}

public class Main {
    private static Book[] books = new Book[100];
    private static int bookCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void addBook(String title, String author) {
        if (bookCount < books.length) {
            books[bookCount] = new Book(title, author);
            bookCount++;
            System.out.println("Buku '" + title + "' berhasil ditambahkan.");
        } else {
            System.out.println("Kapasitas perpustakaan penuh!");
        }
    }

    public static void removeBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                for (int j = i; j < bookCount - 1; j++) {
                    books[j] = books[j + 1];
                }
                books[bookCount - 1] = null;
                bookCount--;
                System.out.println("Buku '" + title + "' berhasil dihapus.");
                return;
            }
        }
        System.out.println("Buku '" + title + "' tidak ditemukan.");
    }

    public static void searchBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                System.out.println("Buku ditemukan: " + books[i]);
                return;
            }
        }
        System.out.println("Buku '" + title + "' tidak ditemukan.");
    }

    public static void showMenu() {
        System.out.println("\n=== Sistem Manajemen Perpustakaan ===");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Hapus Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Keluar");
        System.out.print("Pilih opsi (1-4): ");
    }

    public static void main(String[] args) {
        User admin = new Admin();
        User member = new Member();

        admin.interact();
        member.interact();

        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    System.out.print("Masukkan judul buku: ");
                    String title = scanner.nextLine();
                    System.out.print("Masukkan nama pengarang: ");
                    String author = scanner.nextLine();
                    addBook(title, author);
                    break;
                case 2:
                    System.out.print("Masukkan judul buku yang akan dihapus: ");
                    String titleToRemove = scanner.nextLine();
                    removeBook(titleToRemove);
                    break;
                case 3:
                    System.out.print("Masukkan judul buku yang dicari: ");
                    String titleToSearch = scanner.nextLine();
                    searchBook(titleToSearch);
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem ini!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }
}