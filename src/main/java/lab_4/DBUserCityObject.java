package lab_4;

public class DBUserCityObject {
    private Integer id;
    private String username;
    private String email;
    private String city;

    public DBUserCityObject(Integer id, String username, String email, String city) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.city = city;
    }

    public DBUserCityObject(String username, String email, String city) {
        this.username = username;
        this.email = email;
        this.city = city;
    }

    public DBUserCityObject(Integer id, DBUserCityObject entity) {
        this.id = id;
        this.username = entity.username;
        this.email = entity.email;
        this.city = entity.city;
    }

    public void print() {
        System.out.println(
                id + ". " +
                "Name:  " + username + " ; " +
                "Email:  " + email + " ; " +
                "City:  " + city + " ; "
        );
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
