/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbcontext.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class BookingDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public void cancelBooking(int bookingId){
        String query = "update BookingDetails set isCancel = ? where IDBooking = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setBoolean(1, true);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
