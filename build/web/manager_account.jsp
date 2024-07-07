<%-- 
    Document   : manager_account
    Created on : Jul 7, 2024, 1:29:10 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" href="image/favicon.png" type="image/png">
        <title>TROPICAL Hotel</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="vendors/linericon/style.css">
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="vendors/owl-carousel/owl.carousel.min.css">
        <link rel="stylesheet" type="text/css" href="vendors/bootstrap-datepicker/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" type="text/css" href="vendors/nice-select/css/nice-select.css">
        <link rel="stylesheet" type="text/css" href="vendors/owl-carousel/owl.carousel.min.css">
        <!-- main css -->
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/responsive.css">
        <style>
            #booking {
                font-family: Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }

            #booking td,
            #booking th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            #booking tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            #booking tr:hover {
                background-color: #ddd;
            }

            #booking th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #FEA116;
                color: white;
            }
        </style>
    </head>
    <body>
        <c:if test="${sessionScope.userA.IDRole == 2}">
            <%@include file="/includes/manager_header.jsp" %>
        </c:if>
        <c:if test="${sessionScope.userA.IDRole == 3}">
            <%@include file="/includes/receptionist_header.jsp" %>
        </c:if>
        <!--================Breadcrumb Area =================-->
        <section class="breadcrumb_area">
            <div class="overlay bg-parallax" data-stellar-ratio="0.8" data-stellar-vertical-offset="0" data-background=""></div>
            <div class="container">
                <div class="page-cover text-center">
                    <h2 class="page-cover-tittle">Account</h2>
                    <ol class="breadcrumb">
                        <li><a href="index.html">Home</a></li>
                        <li class="active">About</li>
                    </ol>
                </div>
            </div>
        </section>
        <!--================Breadcrumb Area =================-->


        <!--Table Start-->

        <table style="margin-top: 20px;margin-bottom: 20px;" id="booking">
            <thead>
                <tr>
                    <th>ID Account</th>
                    <th>Full Name</th>
                    <th>Gender</th>
                    <th>City</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>UserName</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Manager</th>
                </tr>
            </thead>
            <!--================DATABASE =================-->
            <tbody>
                <c:forEach items="${sessionScope.listU}" var="l">
                    <tr>
                        <td>${l.IDAccount}</td>
                        <td>${l.getFullName()}</td>
                        <td>${l.getGender()}</td>
                        <td>${l.getCity()}</td>
                        <td>${l.getEmail()}</td>
                        <td>${l.getPhone()}</td>
                        <td>${l.getUserName()}</td>
                        <td>${l.getPass()}</td>
                        <td>${l.getIDRole()}</td>
                        <td><a href="delete?IDAccount=${l.getIDAccount()}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
            <!--================DATABASE =================-->
        </table>
        
    <html>
