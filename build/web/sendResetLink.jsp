<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Send Reset Link - Tropical Hotel</title>
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

    <section id="send-reset-link">
        <div class="container">
            <h2>Send Reset Link</h2>
            <%
                String email = request.getParameter("email");
                // Generate a unique reset token
                String resetToken = UUID.randomUUID().toString();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "password");
                    PreparedStatement ps = conn.prepareStatement("UPDATE users SET reset_token = ? WHERE email = ?");
                    ps.setString(1, resetToken);
                    ps.setString(2, email);
                    int result = ps.executeUpdate();

                    if (result > 0) {
                        // Send email with reset link (pseudo code, replace with actual email sending code)
                        String resetLink = "http://localhost:8080/TropicalHotelBooking/reset-password.jsp?token=" + resetToken;
                        out.println("A reset link has been sent to your email: " + email);
                        // Add your email sending code here
                    } else {
                        out.println("No account found with that email.");
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
