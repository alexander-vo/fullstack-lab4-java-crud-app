package lab_4;

import java.util.ArrayList;
import java.util.Scanner;

enum MENU_ACTIONS {
    GET_ALL_CITY_OBJECTS,
    SAVE_CITY_OBJECT,
    UPDATE_CITY_OBJECT,
    DELETE_CITY_OBJECT,
    EXIT
}

public class Menu {

    private final Database db;
    private final Scanner input;

    public Menu(Database db) {
        this.db = db;
        input = new Scanner(System.in);
    }

    public void start() {
        String id;
        DBUserCityObject entity;
        boolean toExit = false;
        while (!toExit) {
            MENU_ACTIONS action = menu();
            ArrayList<DBUserCityObject> list = db.getAllEntities();
            switch (action) {
                case GET_ALL_CITY_OBJECTS:
                    System.out.println("\nAll entities:");
                    printAllEntities(list);
                    break;
                case SAVE_CITY_OBJECT:
                    System.out.println("\nSave entity process:");
                    entity = saveEntity();
                    System.out.println("Saved!\nEntity:");
                    entity.print();
                    break;
                case UPDATE_CITY_OBJECT:
                    System.out.println("\nUpdate entity process:");
                    System.out.print("Entity id: ");
                    id = input.nextLine();
                    entity = db.getEntityById(Integer.parseInt(id));
                    entity = updateEntity(entity);
                    System.out.println("Updated!\nEntity:");
                    entity.print();
                    break;
                case DELETE_CITY_OBJECT:
                    System.out.println("\nDelete entity activity:");
                    System.out.print("Entity id: ");
                    id = input.nextLine();
                    entity = db.getEntityById(Integer.parseInt(id));
                    deleteEntity(entity);
                    System.out.println("Deleted!");
                    break;
                case EXIT:
                    toExit = true;
                    break;
                default:
                    System.out.println("\nWrong choice.");
            }
        }
        input.close();
    }

    private MENU_ACTIONS menu() {
        System.out.println("\nChoose menu item:");
        System.out.println("1. Get all entities");
        System.out.println("2. Save entity");
        System.out.println("3. Update entity");
        System.out.println("4. Delete entity");
        System.out.println("5. Exit");
        System.out.print(">>>  ");
        String item = input.nextLine();
        return MENU_ACTIONS.values()[Integer.parseInt(item) - 1];
    }

    private void printAllEntities(ArrayList<DBUserCityObject> list) {
        for (DBUserCityObject item: list) {
            item.print();
        }
    }

    private DBUserCityObject saveEntity() {
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("City: ");
        String city = input.nextLine();
        DBUserCityObject entity = new DBUserCityObject(username, email, city);
        return db.saveEntity(entity);
    }

    private DBUserCityObject updateEntity(DBUserCityObject entity) {
        System.out.print("Username ("+entity.getUsername()+"): ");
        String username = input.nextLine();
        System.out.print("Email ("+entity.getEmail()+"): ");
        String email = input.nextLine();
        System.out.print("City ("+entity.getCity()+"): ");
        String city = input.nextLine();
        username = username.trim().isEmpty() ? entity.getUsername() : username.trim();
        email = email.trim().isEmpty() ? entity.getEmail() : email.trim();
        city = city.trim().isEmpty() ? entity.getCity() : city.trim();
        entity.setUsername(username);
        entity.setEmail(email);
        entity.setCity(city);
        return db.updateEntity(entity);
    }

    private void deleteEntity(DBUserCityObject entity) {
        db.deleteEntity(entity);
    }

}
