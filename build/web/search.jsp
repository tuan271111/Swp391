<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search Results - Tropical Hotel</title>
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
            <div class="auth-buttons">
                <a href="login.jsp">Login</a>
                <a href="signup.jsp">Sign Up</a>
            </div>
        </div>
    </header>
    
    <section id="search-results">
        <div class="container">
            <h2>Search Results</h2>
            <%
                String destination = request.getParameter("destination");
                String checkin = request.getParameter("checkin");
                String checkout = request.getParameter("checkout");
                int guests = Integer.parseInt(request.getParameter("guests"));

                // Connect to the database and fetch search results
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "password");
                    PreparedStatement ps = conn.prepareStatement("SELECT * FROM rooms WHERE destination = ? AND available_from <= ? AND available_to >= ? AND capacity >= ?");
                    ps.setString(1, destination);
                    ps.setString(2, checkin);
                    ps.setString(3, checkout);
                    ps.setInt(4, guests);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        String roomType = rs.getString("room_type");
                        double price = rs.getDouble("price");
                        String description = rs.getString("description");

                        %>
                        <div class="room">
                            <h3><%= roomType %></h3>
                            <p><%= description %></p>
                            <p>Price: $<%= price %> per night</p>
                            <a href="booking.jsp" class="btn">Book Now</a>
                        </div>
                        <%
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
