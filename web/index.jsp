<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tropical Hotel</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>
    <!-- Header -->
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
    
    <!-- Hero Section -->
    <section id="hero">
        <div class="container">
            <h2>Welcome to Tropical Hotel – Your Paradise Awaits</h2>
            <p>Experience luxury and comfort in the heart of nature.</p>
            <a href="booking.jsp" class="btn">Book Now</a>
        </div>
    </section>
    
    <!-- Search Section -->
    <section id="search">
        <div class="container">
            <form action="search.jsp" method="get">
                <input type="text" name="destination" placeholder="Destination" required>
                <input type="date" name="checkin" required>
                <input type="date" name="checkout" required>
                <input type="number" name="guests" placeholder="Guests" required>
                <button type="submit">Search</button>
            </form>
        </div>
    </section>
    
    <!-- Featured Rooms -->
    <section id="featured-rooms">
        <div class="container">
            <h2>Featured Rooms</h2>
            <!-- Add room details here -->
        </div>
    </section>
    
    <!-- Amenities Section -->
    <section id="amenities">
        <div class="container">
            <h2>Amenities</h2>
            <!-- List amenities here -->
        </div>
    </section>
    
    <!-- Special Offers -->
    <section id="offers">
        <div class="container">
            <h2>Special Offers</h2>
            <!-- Add offers here -->
        </div>
    </section>
    
    <!-- Customer Reviews -->
    <section id="reviews">
        <div class="container">
            <h2>Customer Reviews</h2>
            <!-- Add reviews here -->
        </div>
    </section>
    
    <!-- Footer -->
    <footer>
        <div class="container">
            <p>&copy; 2024 Tropical Hotel. All rights reserved.</p>
            <ul>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Terms & Conditions</a></li>
            </ul>
        </div>
    </footer>
</body>
</html>
