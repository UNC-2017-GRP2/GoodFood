package com.netcracker.edu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.netcracker.edu.model.UserInfo;

public class UserDaoImpl implements UserDao {

    private Connection conn;

    public UserDaoImpl(Connection connection) {
        conn = connection;
    }

//    @Override
//    public void add(UserInfo object) {
//        String objectsQuery = "INSERT INTO objects(name,object_id, object_type_id) " + "VALUES(?,?,1)";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(objectsQuery)) {
//
//                pstmt.setString(1, object.getName());
//                pstmt.setLong(2, object.getObject_id());
//
//                pstmt.executeUpdate();
//
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//    }

    @Override
    public UserInfo getUserByUsername(String username) {
        String SQL = "select OBJECT_ID from PARAMETERS where ATTR_ID = 11 and TEXT_VALUE = '"
                + username + "'";
        String parameters = "select * from PARAMETERS where object_id =";
        UserInfo user = new UserInfo(0, "null", "null", "null");
        long objectId = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while(rs.next()) {
                objectId = rs.getLong("OBJECT_ID");
                System.out.println(objectId);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(parameters + objectId)) {
            String passHash = "0";
            String role = "0";

            while (rs.next()) {

                switch (rs.getInt("attr_id")) {
                    case 12:
                        passHash = rs.getString("text_value");
                        break;
                    case 13:
                        role = rs.getString("text_value");
                        break;
                    default:
                        break;
                }
                if (!(passHash.equals(new String("0"))) &&
                        !(role.equals(new String("0")))) {
                    user = new UserInfo(objectId, username, passHash, role);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    @Override
    public List<UserInfo> getAll() {
        String count = "select count(*) from OBJECTS where OBJECT_TYPE_ID = 1";
        String objects = "select * from OBJECTS where OBJECT_TYPE_ID = 1"; // UserInfo type
        String parameters = "select * from PARAMETERS where object_id =";

        long[] objectIds = new long[0];
        List<UserInfo> userList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(count)) {
            while(rs.next()) {
                objectIds = new long[rs.getInt("count")];
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(objects)) {
            int i = 0;
            while (rs.next()) {

                    objectIds[i] = rs.getLong("object_id");
                i++;
            }
        } catch (SQLException ex) {
            System.out.println(11);
            System.out.println(ex.getMessage());
        }
        System.out.println(parameters + objectIds[2]);

        for (int i = 0; i < objectIds.length; i++) {

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(parameters + objectIds[i])) {
                String username = "0";
                String passHash = "0";
                String role = "0";

                while (rs.next()) {

                    switch (rs.getInt("attr_id")) {
                        case 11 : username = rs.getString("text_value");
                        System.out.println(username);
                            break;
                        case 12 : passHash = rs.getString("text_value");
                            break;
                        case 13 : role = rs.getString("text_value");
                            break;
                        default: break;
                    }
                    if (!(username.equals(new String("0"))) &&
                        !(passHash.equals(new String("0"))) &&
                        !(role.equals(new String("0")))) {
                        UserInfo user = new UserInfo(objectIds[i], username, passHash, role);
                        userList.add(user);
                        username = "0";
                        passHash = "0";
                        role = "0";

                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return userList;
    }
}
