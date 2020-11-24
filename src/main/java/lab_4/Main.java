package lab_4;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Menu menu = new Menu(db);
        menu.start();
        db.close();
    }
}
