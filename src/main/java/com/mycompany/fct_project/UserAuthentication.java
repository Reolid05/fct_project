package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {
    public String authenticateUser(String username, String password) {
        String role = null;
        String query = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("rol");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }
}
