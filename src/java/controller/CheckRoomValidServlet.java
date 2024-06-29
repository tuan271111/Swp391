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
import model.Booking;
import model.CheckRoomValid;
import model.RoomType;

/**
 *
 * @author admin
 */
public class CheckRoomValidServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckRoomValidServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckRoomValidServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        String checkInDateStr = request.getParameter("check_in");
        String checkOutDateStr = request.getParameter("check_out");

        request.getSession().setAttribute("check_in", checkInDateStr);
        request.getSession().setAttribute("check_out", checkOutDateStr);
        UserDao udao = new UserDao();
        ManagerDao mDao = new ManagerDao();
        
        List<CheckRoomValid> l = udao.checkRoomValid(checkInDateStr, checkOutDateStr);
        List<RoomType> listSearchRoomType = mDao.getRoomType();
        

        for (CheckRoomValid check : l) {
            if (check.getRoomValid() <= 0) {
                for (RoomType roomType : listSearchRoomType) {
                    if(roomType.getIDRoomType() == check.getIDRoom()){
                        listSearchRoomType.remove(roomType);
                        break;
                    }
                }
            }
        }
       
        request.setAttribute("listRoom", listSearchRoomType);
        request.getRequestDispatcher("customer_room.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
