<%-- 
    Document   : att
    Created on : Mar 13, 2023, 10:45:41 AM
    Author     : sonnt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="att" method="POST"> 
            <table border="1px">
                <tr>
                    <td>Seq</td>
                    <td>Student Id</td>
                    <td>Name</td>
                    <td>Present/Absent</td>
                    <td>Description</td>
                </tr>
                <c:forEach items="${requestScope.atts}" var="a" varStatus="loop">
                    <tr>

                        <td>${loop.index +1}</td>
                        <td>${a.student.id}</td>
                        <td>${a.student.name}</td>
                        <td>
                            <input type="radio" 
                                   <c:if test="${!a.status}">
                                   checked="checked" 
                                   </c:if>
                                   name="status${a.student.id}" value="absent"/> Absent
                            <input type="radio" 
                                   <c:if test="${a.status}">
                                   checked="checked" 
                                   </c:if>
                                   name="status${a.student.id}" value="present" /> Present
                        </td>
                        <td>
                            <input type="hidden" name="sid" value="${a.student.id}"/>
                            <input type="hidden" name="aid${a.student.id}" value="${a.id}"/>
                            <input type="text" name="description${a.student.id}" value="${a.description}"/></td>
                    </tr>    

                </c:forEach>
            </table>
            <input type="hidden" name="sessionid" value="${param.id}"/>
            <input type="submit" value="Save"/>
        </form>
        
    </body>
</html>
