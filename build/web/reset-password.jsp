<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password - Tropical Hotel</title>
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

    <section id="reset-password">
        <div class="container">
            <h2>Reset Password</h2>
            <%
                String token = request.getParameter("token");
                if (token != null) {
            %>
            <form action="updatePassword.jsp" method="post">
                <input type="hidden" name="token" value="<%= token %>">
                <input type="password" name="newPassword" placeholder="Enter new password" required>
                <button type="submit">Reset Password</button>
            </form>
            <%
                } else {
                    out.println("Invalid or expired reset token.");
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
