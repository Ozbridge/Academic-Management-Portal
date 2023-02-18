package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Services {
    int getEnrollmentList() {
        return 0;
    }

    Date getAddDropDate(String semester) {
        String query = "SELECT add_drop from semesters where id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            rs.next();
            Date ans = rs.getDate(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
