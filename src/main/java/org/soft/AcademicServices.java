package org.soft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    int getCredits(String courseid) {
        String query = "SELECT credits from courses where id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return 0;
            }
            rs.next();
            int ans = rs.getInt(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    int getCreditLimits(String semester) {
        String query = "SELECT limits from semesters where id = ?";
        try {
            Connection con = DatabaseService.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return 0;
            }
            rs.next();
            int ans = rs.getInt(1);
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    ArrayList<String> getPrerequisites(String courseid) {
        try {
            Connection con = DatabaseService.getConnection();
            String query = "SELECT precourseid FROM prerequisites WHERE courseid = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseid);
            ResultSet rs = ps.executeQuery();
            ArrayList<String> ans = new ArrayList<>();
            while (rs.next()) {
                ans.add(rs.getString(1));
            }
            con.close();
            return ans;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
