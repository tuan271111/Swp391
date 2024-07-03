package controller;

import Service.ConfigVNPAY;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dbcontext.DBContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;

/**
 * Handles booking room requests
 */
public class BookingRoomServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookingRoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingRoomServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the request
        String IDAccount = request.getParameter("IDAccount");
        String IDRoomType = request.getParameter("IDRoomType");
        String IDDiscount  = request.getParameter("IDDiscount");
        String FullName = request.getParameter("FullName");
        String Gender = request.getParameter("Gender");
        String Email = request.getParameter("Email");
        String Phone = request.getParameter("Phone");
        String Adult = request.getParameter("Adult");
        String Child = request.getParameter("Child");
        String checkInDateStr = request.getParameter("checkInDate");
        String checkOutDateStr = request.getParameter("checkOutDate");
        String nRooms = request.getParameter("numRooms");
        String Note = request.getParameter("Note");

        // Parse dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = null;
        Date checkOutDate = null;
        try {
            checkInDate = sdf.parse(checkInDateStr);
            checkOutDate = sdf.parse(checkOutDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("booksuccess", "Booking Fail: Invalid date format.");
            request.getRequestDispatcher("showRoomCustomer").forward(request, response);
            return;
        }

        try {
            Connection conn = (Connection) DBContext.getConnection();
            // Check room availability
            String checkAvailabilitySQL = "{CALL CheckRoomAvailability(?, ?, ?)}";
            try (CallableStatement checkStmt = conn.prepareCall(checkAvailabilitySQL)) {
                checkStmt.setInt(1, Integer.parseInt(IDRoomType));
                checkStmt.setDate(2, new java.sql.Date(checkInDate.getTime()));
                checkStmt.setDate(3, new java.sql.Date(checkOutDate.getTime()));
                ResultSet rs = (ResultSet) checkStmt.executeQuery();
                int availableRooms = 0;
                if (rs.next()) {
                    availableRooms = rs.getInt("AvailableRooms");
                }

                if (availableRooms >= Integer.parseInt(nRooms)) {
                    // Book the room
                    String bookRoomSQL = "{CALL BookRoom(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                    try (CallableStatement bookStmt = conn.prepareCall(bookRoomSQL)) {
                        bookStmt.setInt(1, Integer.parseInt(IDAccount));
                        bookStmt.setInt(2, Integer.parseInt(IDDiscount));
                        bookStmt.setInt(3, Integer.parseInt(IDRoomType));
                        bookStmt.setString(4, FullName);
                        bookStmt.setString(5, Gender);
                        bookStmt.setString(6, Email);
                        bookStmt.setString(7, Phone);
                        bookStmt.setInt(8, Integer.parseInt(Adult));
                        bookStmt.setInt(9, Integer.parseInt(Child));
                        bookStmt.setDate(10, new java.sql.Date(checkInDate.getTime()));
                        bookStmt.setDate(11, new java.sql.Date(checkOutDate.getTime()));
                        bookStmt.setInt(12, Integer.parseInt(nRooms));
                        bookStmt.setString(13, Note);
                        bookStmt.execute();
                        request.setAttribute("booksuccess", "Booking Success");
                    }
                } else {
                    request.setAttribute("booksuccess", "Booking Fail: Not enough rooms available.");

                }
            } catch (Exception ex) {
                Logger.getLogger(BookingRoomServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(Exception ex){
            request.setAttribute("booksuccess", "Booking Fail: " + ex.getMessage());
        }
        Payment(request, response);
    }
    
    public void Payment(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Integer.parseInt(request.getParameter("Price"))*30*1000;
        String bankCode = request.getParameter("bankCode");
        
        String vnp_TxnRef = ConfigVNPAY.getRandomNumber(8);
        String vnp_IpAddr = ConfigVNPAY.getIpAddress(request);

        String vnp_TmnCode = ConfigVNPAY.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", ConfigVNPAY.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNPAY.hmacSHA512(ConfigVNPAY.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNPAY.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        //response.getWriter().write(gson.toJson(job));
        response.sendRedirect(paymentUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
