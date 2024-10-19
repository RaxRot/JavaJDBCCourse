import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/testjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "19649072Sever";

    public static void main(String[] args) {
        try {
            //Create connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established successfully");

            //make crud operations
            createStudent(connection, "Vlad", 85);
            readStudents(connection);
            updateStudent(connection, 1, "Vladislav", 90);
            deleteStudent(connection, 1);

            //close connection
            connection.close();
            System.out.println("Connection closed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void createStudent(Connection connection, String name, int marks) throws SQLException {
        String sql = "INSERT INTO students (sname, marks) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, marks);
            preparedStatement.executeUpdate();
            System.out.println("Student created successfully");
        }
    }


    private static void readStudents(Connection connection) throws SQLException {
        String sqlQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("sid");
                String name = resultSet.getString("sname");
                int marks = resultSet.getInt("marks");
                System.out.println("ID: " + id + ", Name: " + name + ", Marks: " + marks);
            }
        }
    }


    private static void updateStudent(Connection connection, int id, String name, int marks) throws SQLException {
        String updateQuery = "UPDATE students SET sname = ?, marks = ? WHERE sid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, marks);
            preparedStatement.setInt(3, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }


    private static void deleteStudent(Connection connection, int id) throws SQLException {
        String deleteQuery = "DELETE FROM students WHERE sid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }
}
