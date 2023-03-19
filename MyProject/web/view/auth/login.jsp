<%-- 
    Document   : login
    Created on : Mar 15, 2023, 8:12:17 PM
    Author     : ngnqu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 text-center mb-5">
                        <h2 class="heading-section">Login</h2>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-5">
                        <div class="login-wrap p-4 p-md-5">
                            <div class="icon d-flex align-items-center justify-content-center">
                                <span class="fa fa-user-o"></span>
                            </div>
                            <!--<h3 class="text-center mb-4">Must be login to access</h3>-->
                            <form action="login" class="login-form" method="POST">
                                <div class="form-group">
                                    <input type="text" class="form-control rounded-left" placeholder="Username" name="username"
                                           value="${user}">
                                </div>
                                <div class="form-group d-flex">
                                    <input type="password" class="form-control rounded-left" placeholder="Password" name="password"
                                           value="${pass}">
                                </div>
<!--                                <div class="form-group d-md-flex">
                                    <div class="w-50">
                                        <label class="checkbox-wrap checkbox-primary">Remember Me
                                            <input type="checkbox" name="remember">
                                            <span class="checkmark"></span>
                                        </label>
                                    </div>
                                    <br>
                                   

                                </div>-->
                                           <div style='text-align: center; color: red;'>${requestScope.errmsg}</div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary rounded submit p-3 px-5">Login</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>


    </body>
</html>
