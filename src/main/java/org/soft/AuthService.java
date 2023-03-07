package org.soft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {
    String[] login(String username, String password) {
        try {
            String query = "SELECT userid, role, dept FROM users WHERE userid = ? and password = ?";
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String[] ans = {
                    rs.getString(1), rs.getString(2), rs.getString(3)
            };
            con.close();
            return ans;

        } catch (Exception ignored) {
        }
        return new String[]{};
    }
}
