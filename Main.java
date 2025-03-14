abstract class User {
    public abstract void interact();
}

class Admin extends User {
    private static Book[] books = new Book[100]; // array buku dikelola oleh Admin
    private static int bookCount = 0;

    // nambah buku
    private void addBook(String title, String author) {
        if (bookCount < books.length) {
            books[bookCount] = new Book(title, author);
            bookCount++;
            System.out.println("Buku '" + title + "' berhasil ditambahkan.");
        } else {
            System.out.println("Kapasitas perpustakaan penuh!");
        }
    }

    // menghapus buku
    private void removeBook(String title) {
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
    private void searchBook(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                System.out.println("Buku ditemukan: " + books[i]);
                return;
            }
        }
        System.out.println("Buku '" + title + "' tidak ditemukan.");
    }

    @Override
    public void interact() {
        System.out.println("Admin sedang mengelola perpustakaan:");
        // operasi array oleh Admin
        addBook("Pemrograman Java", "John Doe");
        addBook("Struktur Data", "Jane Smith");
        searchBook("Pemrograman Java");
        removeBook("Struktur Data");
        searchBook("Struktur Data");
    }
}

class Member extends User {
    @Override
    public void interact() {
        System.out.println("Member sedang berinteraksi dengan perpustakaan:");
        System.out.println("- Meminjam buku 'Pemrograman Java'");
        System.out.println("- Mengembalikan buku 'Struktur Data'");
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
    public static void main(String[] args) {
        User admin = new Admin();
        User member = new Member();

        System.out.println("=== Sistem Manajemen Perpustakaan ===");
        admin.interact();
        System.out.println();
        member.interact();
    }
}