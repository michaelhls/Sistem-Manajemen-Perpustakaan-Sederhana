import java.util.Scanner;

abstract class User {
    public abstract void interact();
}

class Admin extends User {
    protected static Book[] books = new Book[100];
    protected static int bookCount = 0;
    private final Scanner scanner = new Scanner(System.in);

    private int getBook(final String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }

    private void addBook() {
        System.out.print("Masukkan judul buku: ");
        final String title = scanner.nextLine();

        System.out.print("Masukkan nama pengarang: ");
        final String author = scanner.nextLine();

        if (bookCount < books.length) {
            books[bookCount] = new Book(title, author);
            bookCount++;
            System.out.println("Buku '" + title + "' berhasil ditambahkan.");
        } else {
            System.out.println("Kapasitas perpustakaan penuh!");
        }
    }

    private void removeBook() {
        System.out.print("Masukkan judul buku yang akan dihapus: ");
        final String title = scanner.nextLine();
        final int bookIndex = getBook(title);

        if (bookIndex == -1) {
            System.out.println("Buku '" + title + "' tidak ditemukan.");
        } else {
            for (int j = bookIndex; j < bookCount - 1; j++) {
                books[j] = books[j + 1];
            }
            books[--bookCount] = null;
            System.out.println("Buku '" + title + "' berhasil dihapus.");
        }
    }

    private void searchBook() {
        System.out.print("Masukkan judul buku yang dicari: ");
        final String title = scanner.nextLine();
        final int bookIndex = getBook(title);

        if (bookIndex == -1) {
            System.out.println("Buku '" + title + "' tidak ditemukan.");
        } else {
            System.out.println("Buku ditemukan:\n" + books[bookIndex]);
        }
    }

    private void viewBooks() {
        System.out.println("\n=== Daftar Buku Tersedia ===");
        if (bookCount == 0) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        for (int i = 0; i < bookCount; i++) {
            System.out.println(books[i]);
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.print("\n=== Menu Admin ===\n" +
                    "1. Tambah Buku\n" +
                    "2. Hapus Buku\n" +
                    "3. Cari Buku\n" +
                    "4. Lihat Semua Buku\n" +
                    "5. Keluar\n" +
                    "Pilih opsi (1-5): ");

            final int choice = scanner.nextInt();
            scanner.nextLine();

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
                    viewBooks();
                    break;
                case 5:
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
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void interact() {
        System.out.println("Selamat datang, Member!");
        showMemberMenu();
    }

    private void showMemberMenu() {
        while (true) {
            System.out.print("\n=== Menu Member ===\n" +
                    "1. Lihat Daftar Buku\n" +
                    "2. Cari Buku\n" +
                    "3. Pinjam Buku\n" +
                    "4. Kembalikan Buku\n" +
                    "5. Keluar\n" +
                    "Pilih opsi (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    System.out.println("Keluar dari menu Member.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }

    private int getBook(String title) {
        for (int i = 0; i < Admin.bookCount; i++) {
            if (Admin.books[i].getTitle().equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }

    private void viewBooks() {
        System.out.println("\n=== Daftar Buku Tersedia ===");
        if (Admin.bookCount == 0) {
            System.out.println("Tidak ada buku di perpustakaan.");
            return;
        }
        for (int i = 0; i < Admin.bookCount; i++) {
            if (Admin.books[i].isAvailable()) {
                System.out.println(Admin.books[i]);
            }
        }
    }

    private void searchBook() {
        System.out.print("Masukkan judul buku yang dicari: ");
        String title = scanner.nextLine();
        int bookIndex = getBook(title);

        if (bookIndex == -1) {
            System.out.println("Buku '" + title + "' tidak ditemukan.");
        } else {
            System.out.println("Buku ditemukan:\n" + Admin.books[bookIndex]);
        }
    }

    private void borrowBook() {
        System.out.print("Masukkan judul buku yang ingin dipinjam: ");
        String title = scanner.nextLine();
        int bookIndex = getBook(title);

        if (bookIndex == -1 || !Admin.books[bookIndex].isAvailable()) {
            System.out.println("Buku tidak tersedia untuk dipinjam.");
        } else {
            Admin.books[bookIndex].setAvailable(false);
            System.out.println("Buku '" + title + "' berhasil dipinjam.");
        }
    }

    private void returnBook() {
        System.out.print("Masukkan judul buku yang ingin dikembalikan: ");
        String title = scanner.nextLine();
        int bookIndex = getBook(title);

        if (bookIndex == -1 || Admin.books[bookIndex].isAvailable()) {
            System.out.println("Buku tidak dapat dikembalikan (mungkin sudah tersedia).");
        } else {
            Admin.books[bookIndex].setAvailable(true);
            System.out.println("Buku '" + title + "' berhasil dikembalikan.");
        }
    }
}

class Book {
    private final String title;
    private final String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
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
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        User admin = new Admin();
        User member = new Member();

        while (true) {
            System.out.print("\n=== Sistem Manajemen Perpustakaan ===\n" +
                    "1. Masuk sebagai Admin\n" +
                    "2. Masuk sebagai Member\n" +
                    "3. Keluar\n" +
                    "Masuk sebagai? (1-3): ");
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
                    return;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
    }
}
