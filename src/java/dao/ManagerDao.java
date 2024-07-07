/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbcontext.DBContext;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.BookingDetails;
import model.Contact;
import model.Discount;
import model.RoomType;
import model.User;

/**
 *
 * @author admin
 */
public class ManagerDao {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    //Manager account
    // day thong tin all account ra bang quan ly account (View)
    public List<User> getAccounts() {
        List<User> list = new ArrayList<>();
        String query = "select * from Account";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9)));
            }
        } catch (Exception e) {
        }
        return list;
    }


   //MANAGER DISCOUNT
    public List<Discount> getDiscount() {
        List<Discount> list = new ArrayList<>();
        String query = "select * from Discount";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Discount(rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void addDiscount(String DiscountName, String DiscountValue, String StartDay, String EndDay, String Note) {
        String query = "insert into Discount(DiscountName,DiscountValue,StartDay,EndDay,Note) values (?,?,?,?,?)";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, DiscountName);
            ps.setString(2, DiscountValue);
            ps.setString(3, StartDay);
            ps.setString(4, EndDay);
            ps.setString(5, Note);

            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    // xoa Discount theo ID (Delete)
    public void deleteDiscount(String IDDiscount) {
        String query = "delete from Discount where IDDiscount = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, IDDiscount);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    //MANAGER BOOKING
    // day thong tin all Booking ra bang quan ly booking (View)
    public List<Booking> getBooking() {
        List<Booking> list = new ArrayList<>();
        String query = "select * from Booking";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Booking(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getDouble(10),
                        rs.getString(11),
                        rs.getString(12)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    // get list booking
    public List<BookingDetails> getBookingDetails() {
        List<BookingDetails> list = new ArrayList<>();
        String query = "select * from BookingDetails";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                BookingDetails bd = new BookingDetails(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getDouble(14),
                        rs.getString(15),
                        rs.getString(16));
                bd.setIsCancel(rs.getBoolean(17));
                bd.setOver(isDateBeforeToday(rs.getString(12)));
                list.add(bd);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    private static boolean isDateBeforeToday(String dateString) {
        // Định dạng ngày tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Chuyển đổi chuỗi ngày tháng thành đối tượng LocalDate
        LocalDate dateToCheck = LocalDate.parse(dateString, formatter);
        
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();
        
        // So sánh ngày cần kiểm tra với ngày hiện tại
        return dateToCheck.isBefore(today);
    }

    public List<BookingDetails> getBookingDetailsByReceptionist() {
        List<BookingDetails> list = new ArrayList<>();
        String query = "SELECT bd.*\n"
                + "FROM BookingDetails bd\n"
                + "JOIN Account a ON bd.IDAccount = a.IDAccount\n"
                + "WHERE a.IDRole = 2 or a.IDRole =3;";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BookingDetails(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getDouble(14),
                        rs.getString(15),
                        rs.getString(16)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public List<BookingDetails> getBookingDetailsByCustomer() {
        List<BookingDetails> list = new ArrayList<>();
        String query = "SELECT bd.*\n"
                + "FROM BookingDetails bd\n"
                + "JOIN Account a ON bd.IDAccount = a.IDAccount\n"
                + "WHERE a.IDRole = 1;";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BookingDetails(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getDouble(14),
                        rs.getString(15),
                        rs.getString(16)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<BookingDetails> searchBookingDetails(String Phone) {
        List<BookingDetails> list = new ArrayList<>();
        String query = "select * from BookingDetails where Phone = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);
            ps.setString(1, Phone);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BookingDetails(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getDouble(14),
                        rs.getString(15),
                        rs.getString(16)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void updateBookingStatus(String IDBooking, String BookingStatus) {
        String query = "update BookingDetails set Note = ? where IDBooking = ?";
        try {
            conn = DBContext.getConnection();//mo ket noi
            ps = conn.prepareStatement(query);

            ps.setString(1, BookingStatus);
            ps.setString(2, IDBooking);

            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    
    public static void main(String[] args) {
        ManagerDao dao = new ManagerDao();
        
    }
    
   

}
