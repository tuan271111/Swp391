<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Tropical Hotel</title>
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
    
    <section id="register">
        <div class="container">
            <h2>Register</h2>
            <%
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                if (password.equals(confirmPassword)) {
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=hotel_db;user=sa;password=123456");
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)");
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ps.setString(3, email);
                        ps.setString(4, phone);
                        int result = ps.executeUpdate();

                        if (result > 0) {
                            out.println("Registration successful! Redirecting to login page...");
                            response.setHeader("Refresh", "2; URL=login.jsp");  // Redirect after 2 seconds
                        } else {
                            out.println("Registration failed. Please try again.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.println("An error occurred. Please try again.");
                    }
                } else {
                    out.println("Passwords do not match. Please try again.");
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
