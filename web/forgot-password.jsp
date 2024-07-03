<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password - Tropical Hotel</title>
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

    <section id="forgot-password">
        <div class="container">
            <h2>Forgot Password</h2>
            <form action="sendResetLink.jsp" method="post">
                <input type="email" name="email" placeholder="Enter your email" required>
                <button type="submit">Send Reset Link</button>
            </form>
        </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; 2024 Tropical Hotel. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>
