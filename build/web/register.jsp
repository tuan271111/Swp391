<<<<<<< HEAD
<%-- 
    Document   : SignUpPage
    Created on : Jun 3, 2023, 2:22:20 AM
    Author     : duclu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
</head>
<body>
    <div class="container">
        <div class="registration">
            <div class="logo-container">
                <img src="image/logo1.png" alt="Logo">
            </div>
            <h2>Register</h2>
            <form class="registration-form" action="register" method="POST">
                
                <div class="alert alert-danger" role="alert">
                    <p style="color: red;"class="text-danger">${mess} </p>
                </div>
                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="fullname" required>
                
                <label for="gender">Gender</label>
                <select type="text" id="gender" name="gender" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
                
                <label for="city">City</label>
                <input type="text" id="city" name="city" required>
                
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
                
                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" required>
                
                <label for="username">UserName</label>
                <input type="text" id="username" name="username" required>
                
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                
                <label for="confirm_password">Confirm Password</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
                <input type="hidden" id="idrole" name="idrole" value="1">
                <input type="checkbox"> <a href="#">I agree with the hotel policy</a>
              
                <button type="submit">Register</button>
                
                <div class="mt-4 text-center">
                                <p class="text-sm">Do you already have an account ? <a href="login.jsp"
                                                                               class="text-red-700 hover:underline">Login</a></p>
                </div>
            </form>
        </div>
        <div class="image">
            <img src="image/bglogin.png" alt="Image">
        </div>
    </div>
=======
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
>>>>>>> f73e52c4162f9b78ab34ee8d738dcdf29cafa397
</body>
</html>
