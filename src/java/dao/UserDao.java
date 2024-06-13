package dao;

import dbcontext.DBContext;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
public class UserDao {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User ActiveAccount(int accountId) {
        String query = "update Account set isActive = ? where IDAccount = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setBoolean(1, true);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return null;
    }
    
    public User ResetPass(int userId, String newPass) {
        String query = "update Account set Pass = ? where IDAccount = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, newPass);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return null;
    }
    
    // login
    public User checkAccountValid(String user, String pass) {
        String query = "select * from Account where UserName = ? and Pass = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getBoolean(10));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public User checkAccountExist(String user) {
        String query = "select * from Account where UserName = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, user);
            rs = ps.executeQuery();

            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9));
            }
        } catch (Exception e) {
        }
        return null;
    }

    // them account moi (register)
    public int addAccount(String UserName, String Pass, String FullName, String Gender, String City, String Email, String Phone, String IDRole) {
    String query = "insert into Account(UserName,Pass,FullName,Gender,City,Email,Phone,IDRole,isActive) values(?,?,?,?,?,?,?,?,?)";
    int generatedId = -1;
    
    try {
        conn = DBContext.getConnection(); // Open connection
        ps = conn.prepareStatement(query, ps.RETURN_GENERATED_KEYS);
        ps.setString(1, UserName);
        ps.setString(2, Pass);
        ps.setString(3, FullName);
        ps.setString(4, Gender);
        ps.setString(5, City);
        ps.setString(6, Email);
        ps.setString(7, Phone);
        ps.setString(8, IDRole);
        ps.setBoolean(9, false);

        ps.executeUpdate();

        // Retrieve the generated key
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    return generatedId;
}


    // chinh sua thong tin account (Update)
    public void updateAccount(String IDAccount, String UserName, String Pass, String FullName, String Gender, String City, String Email, String Phone) {
        String query = "update Account set UserName=?, Pass=?, FullName=?, Gender=?, City=? , Email=?, Phone=?  where IDAccount = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, UserName);
            ps.setString(2, Pass);
            ps.setString(3, FullName);
            ps.setString(4, Gender);
            ps.setString(5, City);
            ps.setString(6, Email);
            ps.setString(7, Phone);
            ps.setString(8, IDAccount);

            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}