abstract class User {
    public abstract void interact();
}

class Admin extends User {
    @Override
    public void interact() {
        System.out.println("");
    }
}

class Member extends User {
    @Override
    public void interact() {
        System.out.println("");
    }
}

public class Main {
    public static void main(String[] args) {
        User admin = new Admin();
        User member = new Member();

        admin.interact();
        member.interact();
    }
}
