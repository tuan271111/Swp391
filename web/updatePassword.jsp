<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Password - Tropical Hotel</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>Tropical Hotel</h1>
            <nav>
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="rooms.jsp">Rooms</a></li>
                    <li><a href="amenities.jsp">Amenities</a></li>
                    <li><a href="dining.jsp">Dining</a></li>
                    <li><a href="contact.jsp">Contact Us</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <section id="update-password">
        <div class="container">
            <h2>Update Password</h2>
            <%
                String token = request.getParameter("token");
                String newPassword = request.getParameter("newPassword");
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "password");
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ?, reset_token = NULL WHERE reset_token = ?");
                    ps.setString(1, newPassword);
                    ps.setString(2, token);
                    int result = ps.executeUpdate();

                    if (result > 0) {
                        out.println("Your password has been successfully updated.");
                    } else {
                        out.println("Failed to update password. Invalid or expired token.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
        </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; 2024 Tropical Hotel. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>
