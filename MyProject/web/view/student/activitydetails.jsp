<%-- 
    Document   : activitydetails
    Created on : Nov 8, 2022, 8:30:48 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .header{
                background-color: rgb(60, 105, 173);
                color: white;
            }
            a{
                text-decoration: none;
            }
            html{
                margin: 20px 150px;
                font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            }
            .top{
                display: flex;
                justify-content: space-between;
                background-color: #f5f5f5;
                height: 30px;
                padding-top: 5px;
                margin-bottom: 30px;
            }
            .top-left{
                text-align: left;
            }
            .top-left a{
                margin: 2px 5px;
                border-radius: 5px;
                padding: 2px 4px;
                font-weight: bold;
                background-color: rgb(60, 105, 173);
            }
            .top-right{
                text-align: right;
            }
            .top-right a{
                margin: 2px 5px;
                border-radius: 5px;
                padding: 2px 4px;
                font-weight: bold;
                background-color: rgb(60, 105, 173);
            }
            
            .title{
                font-size: 40px;
                margin-bottom: 15px;
            }
            td{
                padding-right: 100px;
            }
        </style>

    </head>
    <body>
        <div class='title'>Activity Detail</div>
       
            
        <div class='top'>

            <div class="top-left">
                <a href="timetable" class="header">Timetable</a> |
                <a class="header">Activity Detail</a>
            </div>

            <div class='top-right'>
                <a class="header">${requestScope.account.username}</a> |
                <a class="header" href="../logout">Log out</a>
            </div>
        </div>
                <table>
                    <tr>
                        <td>Date: </td>
                        <td>${requestScope.ses.date}</td>
                    </tr>
                    <tr>
                        <td>Slot: </td>
                        <td>${requestScope.ses.timeslot.id}</td>
                    </tr>
                    <tr>
                        <td>Student Group: </td>
                        <td>${requestScope.ses.group.name}</td>
                    </tr>
                    <tr>
                        <td>Instructor: </td>
                        <td>${requestScope.ses.lecturer.account.username}</td>
                    </tr>
                    <tr>
                        <td>Subject: </td>
                        <td>${requestScope.ses.group.subject.detailname}(${requestScope.ses.group.subject.name})</td>
                    </tr>
                    <tr>
                        <td>Subject session number: </td>
                        <td>${requestScope.ses.group.subject.numberOfSession}</td>
                    </tr>
                    <tr>
                        <td>Session description: </td>
                        <td>${requestScope.ses.attandances[0].description}</td>
                    </tr>
                    <tr>
                        <td>Attendance: </td>
                        <c:if test="${requestScope.ses.attandated}">
                            <td>Attended</td>
                    </c:if>
                            <c:if test="${!requestScope.ses.attandated}">
                            <td>Not yet</td>
                    </c:if>
                        
                    </tr>
                    <tr>
                        <td>Record time: </td>
                        <c:if test="${requestScope.ses.attandated}">
                            <td>${requestScope.ses.attandances[0].record_time}</td>
                    </c:if>
                            <c:if test="${!requestScope.ses.attandated}">
                            <td></td>
                    </c:if>
                    </tr>
                </table>
    </body>
</html>
