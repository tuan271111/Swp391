/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ManagerDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingDetails;
import model.CheckRoomValid;
import model.Discount;

/**
 *
 * @author admin
 */
public class ConfirmInformationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        UserDao udao = new UserDao();
        ManagerDao mDao = new ManagerDao();

        //bookingtime
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTime = currentTime.format(formatter);

        String IDAccount = request.getParameter("IDAccount");
        int idac = Integer.parseInt(IDAccount);

        String IDRoomType = request.getParameter("IDRoomType");
        int idrt = Integer.parseInt(IDRoomType);

        String FullName = request.getParameter("FullName");
        String Gender = request.getParameter("Gender");
        String Email = request.getParameter("Email");
        String Phone = request.getParameter("Phone");
        String NameRoomType = request.getParameter("NameRoomType");

        String adult = request.getParameter("Adult");
        int na = Integer.parseInt(adult);
        String child = request.getParameter("Child");
        int nc = Integer.parseInt(child);
        // get total day
        String checkInDateStr = request.getParameter("checkInDate");
        String checkOutDateStr = request.getParameter("checkOutDate");

        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);
        int numOfDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        // get ngay hien tai de check Day
        LocalDate currentDate = LocalDate.now();

        String nRooms = request.getParameter("numRooms");
        int numRooms = Integer.parseInt(nRooms);

        String price = request.getParameter("Price");
        int tpi = Integer.parseInt(price);
        double tp = numOfDays * numRooms * tpi;
        int idDiscount = 4;

        String DiscountCode = request.getParameter("DiscountCode");
        String mess = "Fail";
        int fail = 0;

        if (DiscountCode == null) {
            tp = numOfDays * numRooms * tpi;
        } else {
            boolean discountMatch = false;
            List<Discount> listDiscount = mDao.getDiscount();
            for (int i = 0; i < listDiscount.size(); i++) {
                String codeDis = listDiscount.get(i).getNote();

                if (DiscountCode.equalsIgnoreCase(codeDis)) {
                    idDiscount = listDiscount.get(i).getIDDiscount();
                    double value = listDiscount.get(i).getDiscountValue();
                    tp = (numOfDays * numRooms * tpi) * (1 - value);
                    discountMatch = true;
                    break;
                }
            }

            if (!discountMatch && DiscountCode.trim().length() > 0) {
                fail = 1;
                mess = "Discount code invalid";
            }
        }

        String Note = "Not Yet";

        // check Room
        String ttRoom = request.getParameter("ttRoom");
        int ttR = Integer.parseInt(ttRoom);
        int checkRoom = ttR - numRooms;
        String cRoom = String.valueOf(checkRoom);

        List<CheckRoomValid> l = udao.checkRoomValid(checkInDateStr, checkOutDateStr);
        int cr = checkRoom;
        for (int i = 0; i < l.size(); i++) {
            if (idrt == l.get(i).getIDRoom()) {
                cr = l.get(i).getRoomValid();
            } else {
                cr = ttR;
            }
        }

        if (cr <= 0 || cr < numRooms) {
            request.setAttribute("messFail", "Not enough room! " + cr + " rooms available");
            request.getRequestDispatcher("form_test.jsp").forward(request, response);
        } else if (checkInDate.isBefore(currentDate) || checkOutDate.isBefore(checkInDate)) {
            request.setAttribute("messFail", "Day Invaild!");
            request.getRequestDispatcher("form_test.jsp").forward(request, response);
        } else if (fail == 1) {
            request.setAttribute("messFail", mess);
            request.getRequestDispatcher("form_test.jsp").forward(request, response);
        } else {
            BookingDetails bookingDt = new BookingDetails(idac, idDiscount, idrt, FullName, Gender, Email, Phone, na, nc, checkInDateStr, checkOutDateStr, numRooms, tp, formattedTime, Note);
            request.setAttribute("roomName", NameRoomType);
            request.setAttribute("price", price);
            request.setAttribute("booking", bookingDt);
            request.getRequestDispatcher("form_payment.jsp").forward(request, response);
        }
    }

    private boolean areDateRangesOverlapping(String startA, String endA, int idRoom) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ManagerDao managerDao = new ManagerDao();
        List<BookingDetails> list = managerDao.getBookingDetails();
        for (BookingDetails bookingDetail : list) {
            if (bookingDetail.getIDRoomType() == idRoom) {
                LocalDate startDateA = LocalDate.parse(startA, formatter);
                LocalDate endDateA = LocalDate.parse(endA, formatter);
                LocalDate startDateC = LocalDate.parse(bookingDetail.getCheckIn(), formatter);
                LocalDate endDateC = LocalDate.parse(bookingDetail.getCheckOut(), formatter);

                boolean check = !(endDateA.isBefore(startDateC) || endDateC.isBefore(startDateA));
                if (check) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
