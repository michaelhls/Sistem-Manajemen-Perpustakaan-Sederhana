import java.util.Scanner;

abstract class User {
    public abstract void interact();
}

class Admin extends User {
    private static Book[] books = new Book[100];
    private static int bookCount = 0;
    private Scanner scanner = new Scanner(System.in);

    // menambahkan buku
    private void addBook() {
        System.out.print("Masukkan judul buku: ");
        String title = scanner.nextLine();
        System.out.print("Masukkan nama pengarang: ");
        String author = scanner.nextLine();
        if (bookCount < books.length) {
            books[bookCount] = new Book(title, author);
            bookCount++;
            System.out.println("Buku '" + title + "' berhasil ditambahkan.");
        } else {
            System.out.println("Kapasitas perpustakaan penuh!");
        }
    }

    // menghapus buku
    private void removeBook() {
        System.out.print("Masukkan judul buku yang akan dihapus: ");
        String title = scanner.nextLine();
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

    // mencari buku
    private void searchBook() {
        System.out.print("Masukkan judul buku yang dicari: ");
        String title = scanner.nextLine();
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                System.out.println("Buku ditemukan: " + books[i]);
                return;
            }
        }
        System.out.println("Buku '" + title + "' tidak ditemukan.");
    }

    // menu Admin
    private void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    System.out.println("Keluar dari menu Admin.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }

    @Override
    public void interact() {
        System.out.println("Selamat datang, Admin!");
        showAdminMenu();
    }
}

class Member extends User {
    @Override
    public void interact() {
        System.out.println("Selamat datang, Member!");
        // belum ada menu
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
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User admin = new Admin();
        User member = new Member();

        while (true) {
            System.out.println("\n=== Sistem Manajemen Perpustakaan ===");
            System.out.println("1. Masuk sebagai Admin");
            System.out.println("2. Masuk sebagai Member");
            System.out.println("3. Keluar");
            System.out.print("Masuk sebagai? (1-3): ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); 

            switch (roleChoice) {
                case 1:
                    admin.interact();
                    break;
                case 2:
                    member.interact();
                    break;
                case 3:
                    System.out.println("Terima kasih!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }
}