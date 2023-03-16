<%-- 
    Document   : checkattendance
    Created on : Oct 28, 2022, 8:09:07 AM
    Author     : admin
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="helper" class="util.DateTimeHelper"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../css/checkattendance.css"/>
        <style>
            a{
                text-decoration: none;
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
            .present{
                color: Green;
            }

            .absent{
                color: red;
            }

            td{
                padding: 0px 5px;
            }
        </style>
    </head>
    <body>
        <div class='title'>Attandance Report</div>
        <div class='top'>
            <div class="top-left">
                <a class="header" href="home">Home</a> |
                <a class="header" href="timetable">Timetable</a> |
                <a class="header">Attandance Report</a>
            </div>
            <div class='top-right'>
                <a class="header">${requestScope.username}</a> |
                <a class="header" href="../logout">Log out</a>
            </div>

        </div>
        Take attendance for Group: ${requestScope.ses.group.name} <br/>
        Subject: ${requestScope.ses.group.subject.name} <br/>
        Room: ${requestScope.ses.room.name} <br/>
        Date: ${requestScope.ses.date} - ${requestScope.ses.timeslot.description}<br/>
        Attended: <span style="color: red;"> ${requestScope.ses.attandated?"Yes":"No"} </span>
        <form action="takeatt" method="POST">
            <input type="hidden" name="sesid" value="${param.id}"/>
            <table border="1px">
                <tr class="header">
                    <td>No.</td>
                    <td>StudentID</td>
                    <td>Full Name</td>
                    <td>Image</td>
                    <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) eq 0}">
                        <td>Present</td>
                        <td>Absent</td>
                    </c:if>
                    <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) ne 0}">
                        <td>Status</td>
                    </c:if>
                    <td>Description</td>
                    <td>Taker</td>
                    <td>Record time</td>
                </tr>
                <c:forEach items="${requestScope.ses.attandances}" var="a" varStatus="loop">
                    <tr>
                        <td>${loop.index+1}</td>
                        <td>${a.student.id}
                            <input type="hidden" name="stdid" value="${a.student.id}"/>
                        </td>
                        <td>${a.student.name}</td>
                        <td><img src="../${a.student.imageURL}" alt="" style="width: 150px;"></td>
                        <!--<td><img src="../image/avatar/000001.png" alt=""></td>-->
                        <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) eq 0}">
                            <td><input type="radio"
                                       <c:if test="${a.present}">
                                           checked="checked"
                                       </c:if>
                                       name="present${a.student.id}" value="present" /></td>
                            <td><input type="radio"
                                       <c:if test="${!a.present}">
                                           checked="checked"
                                       </c:if>
                                       name="present${a.student.id}" value="absent" /></td>
                            </c:if>
                            <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) lt 0}">
                                <c:if test="${!requestScope.ses.attandated}">
                                <td class="absent">Not yet</td>
                            </c:if>

                            <c:if test="${requestScope.ses.attandated}">


                                <c:if test="${a.present}">
                                    <td class="present"> Present </td>
                                </c:if>
                                <c:if test="${!a.present}">
                                    <td class="absent"> Absent </td>
                                </c:if>

                            </c:if>

                        </c:if>
                        <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) gt 0}">

                            <td class="absent">Not yet</td>

                        </c:if>
                        <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) eq 0}">
                            <td class="description"><input type="text" name="description${a.student.id}" value="${a.description}" /></td>
                            </c:if>
                            <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) ne 0}">
                            <td class="description">${a.description}</td>
                        </c:if>
                            <td class="taker">${a.taker}</td>
                            <c:if test="${requestScope.ses.attandated}">
                                <td class="record_time">${a.record_time}</td>
                            </c:if>
                            <c:if test="${!requestScope.ses.attandated}">
                                <td></td>
                            </c:if>
                            
                    </tr>   

                </c:forEach>

            </table>
            <input  <c:if test="${helper.compare(requestScope.ses.date,requestScope.today) ne 0}">
                    hidden
                </c:if>
                type="submit" value="Save"/>
        </form>
    </body>
</html>


