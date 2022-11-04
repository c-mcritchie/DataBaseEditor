<%-- 
    Document   : users
    Created on : Oct. 31, 2022, 3:31:17 p.m.
    Author     : colem
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
        <title>Users</title>
        <style>
            .flex-container {
                display: flex;
            }

            .flex-side {
                flex: 1;
            }
            .flex-main {
                flex: 2;
            }  
        </style>
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
        <h1>User database</h1>
        <div class="flex-container">
            <!--Add Users-->
            <div class="flex-side">
                
                <h3>Add User</h3>
                <form action="User" method="POST">
                    <input type="hidden" name="action" value="add">
                    Email:<input type="email" name="addEmail"> <br>
                    First Name:<input type="text" name="addFirst"> <br>
                    Last Name:<input type="text" name="addLast"> <br>
                    Password: <input type="password" name="addPassword"> <br>
                    Role: <select name="addRole">
                        <c:forEach items="${roles}" var="role">
                            <option value="${role.name}">${role.name}</option>
                        </c:forEach>
                        </select>
                    <br><button type="submit">Submit</button>
                </form>
            </div>
            

            <!--Table View-->

            <div class="flex-main">
                <form action="User" method="POST" id="delete">
                    <input type="hidden" name="action" value="delete">
                </form>
                <form action="User" method="POST" id="edit">
                    <input type="hidden" name="action" value="edit">
                </form>
                <table class="table">
                <thead>
                    <tr>
                        <th>E-Mail</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th>Active?</th>
                        <th>Edit</th>
                        <th>Delete</th>
                            
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.getEmail()}</td>
                            <td>${user.getFirstName()}</td>
                            <td>${user.getLastName()}</td>
                            <td>${user.getRole().getName()}</td>
                            <td>${user.isActive()}</td>
                            <td><button type="submit" value="${user.getEmail()}" name="edit" form="edit">✏️</button></td>
                            <td><button type="submit" value="${user.getEmail()}" name="delete" form="delete">❌</button></td>
                        </tr>
                    </c:forEach> 
                </tbody>
                    
            </table>
            </div>
        

            <!--Update User-->
            <div class="flex-side">
                
                <h3>Update User</h3>
                <c:if test="${not Hidden}">
                    
                    <form action="User" method="POST">
                        <input type="hidden" name="action" value="update">
                        Email:<input type="email" name="updateEmail" value="${updateUser.getEmail()}" readonly> read only<br>
                        First Name:<input type="text" name="updateFirst" value="${updateUser.getFirstName()}"> <br>
                        Last Name:<input type="text" name="updateLast" value="${updateUser.getLastName()}" > <br>
                        Password: <input type="text" name="updatePassword" value="${updateUser.getPassword()}"> <br>
                        Role: <select name="updateRole">
                            <c:forEach items="${updateRoles}" var="role">
                                <option value="${role.name}" <c:if test="${role.name.equals(updateUser.getRole().getName())}">selected</c:if>>${role.name}</option>
                            </c:forEach>
                            </select>
                        <br><button type="submit" name="updateButton" value="submit">Submit</button><button type="submit" name="updateButton" value="cancel">Cancel</button>
                    </form>
                    </c:if>
            </div>
       </div>
    </body>
</html>
