package lab_4;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection connection;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DATABASE_USER = System.getenv("MYSQL_USER");
            String DATABASE_PASSWORD = System.getenv("MYSQL_PASSWORD");
            String CONNECTION_STRING = System.getenv("DATABASE_URL");
            connection = DriverManager.getConnection(CONNECTION_STRING, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<DBUserCityObject> getAllEntities() {
        ArrayList<DBUserCityObject> entities = new ArrayList<>();
        try {
            Statement st = connection.createStatement();

            String SELECT_ALL = "SELECT * FROM users_cities;";
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                entities.add(new DBUserCityObject(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("city")
                ));
            }
            st.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return entities;
    }

    public DBUserCityObject getEntityById(Integer id) {
        DBUserCityObject entity = null;
        try {
            String GET_BY_ID = "SELECT * FROM users_cities WHERE id = ?;";
            PreparedStatement st = connection.prepareStatement(GET_BY_ID);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                entity = new DBUserCityObject(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("city")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    public DBUserCityObject saveEntity(DBUserCityObject entity) {
        DBUserCityObject newEntity = null;
        try {
            String SAVE_ENTITY = "INSERT INTO users_cities (username, email, city) VALUES (?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(SAVE_ENTITY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, entity.getUsername());
            st.setString(2, entity.getEmail());
            st.setString(3, entity.getCity());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                newEntity = new DBUserCityObject(rs.getInt(1), entity);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return newEntity;
    }

    public DBUserCityObject updateEntity(DBUserCityObject entity) {
        try {
            String UPDATE_ENTITY = "UPDATE users_cities SET username = ?, email = ?, city = ? WHERE id = ?;";
            PreparedStatement st = connection.prepareStatement(UPDATE_ENTITY);

            st.setString(1, entity.getUsername());
            st.setString(2, entity.getEmail());
            st.setString(3, entity.getCity());
            st.setInt(4, entity.getId());

            st.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    public void deleteEntity(DBUserCityObject entity) {
        try {
            String DELETE_ENTITY = "DELETE FROM users_cities WHERE id = ?;";
            PreparedStatement st = connection.prepareStatement(DELETE_ENTITY);
            st.setInt(1, entity.getId());
            st.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
