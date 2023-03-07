package org.soft;

import java.sql.*;
import java.util.Date;

public class AuthService {
    String[] login(String username, String password) {
        try {
            String query = "SELECT userid, role, dept FROM users WHERE userid = ? and password = ?";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            String ans[] = new String[]{};
            if (rs.next()) {
                ans = new String[]{
                        rs.getString(1), rs.getString(2), rs.getString(3)
                };
                query = "INSERT INTO logs VALUES (?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                ps.execute();
            }
            con.close();
            return ans;

        } catch (Exception ignored) {
        }
        return new String[]{};
    }
}
