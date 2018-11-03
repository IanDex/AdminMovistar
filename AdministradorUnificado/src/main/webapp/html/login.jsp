<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@page import="java.util.ArrayList"%>

<%@page session="true" %>
<%@page import="com.grupodot.telefonica.Models.Usuario"%>
<%@page import="com.grupodot.telefonica.Models.Rol"%>

<%
//String form = "formulariotic";
String forms = (String) request.getAttribute("form");
String path =  "http://localhost:8080/";

if(session.getAttribute("rol")!=null){
	response.sendRedirect("/" + session.getAttribute("rol"));
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Vendor styles -->
        <link rel="stylesheet" href="<%=path %>assets/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="<%=path %>assets/vendors/bower_components/animate.css/animate.min.css">

        <!-- App styles -->
        <link rel="stylesheet" href="<%=path %>assets/css/app.min.css">
        <link rel="stylesheet" href="<%=path %>assets/css/login.min.css">
        
    	<style> .dis:disabled{ cursor: not-allowed; border: 1px solid #d2d3d4; padding: 6px; border-radius: 50%;} .dis{ padding: 6px; border-radius: 50%;} .dis:hover{   padding: 6px; border-radius: 50%; text-decoration: none;} a{text-decoration: none;}.btn:disabled {background-color: #d2d2d2;} </style>
            <script>
    	var form = '<%=forms%>'; 
    console.log(form);
</script>
    </head>

    <body data-ma-theme="movistarblue">
        <div class="login">

            <!-- Login -->
            <div class="login__block active" id="l-login">
                <img src="https://www.movistar.co/ofertas/productos-hogar-y-movil/images/Logo.png" alt="logo">

                <div class="login__block__body">
                    <div class="form-group form-group--float form-group--centered">
                        <input type="text" class="form-control" value="" name="user" id="user" autocomplete="off">
                        <label>Usuario</label>
                        <i class="form-group__bar"></i>
                    </div>

                    <div class="form-group form-group--float form-group--centered">
                        <input type="password" name="password" id="password" value="" class="form-control" autocomplete="off">
                        
                        <label>Contraseña</label>
                        <i class="form-group__bar"></i>
                    </div>
                     <div class="form-group otc2">
                    	<span><strong>Datos incorrectos</strong></span>
                    </div>
                    
                    	<button class="btn btn-outline-primary btn--icon dis" id="btnenviar" ><i class="zmdi zmdi-long-arrow-right"></i></button>
                    	
                    <div class="form-group otc">
                    	<span><a href="#" class="recPass">¿Olvidaste tu contraseña?</a></span>
                    </div>
                    

                </div>
            </div>

            <!-- Forgot Password -->
            <div class="login__block" id="l-forget-password">
               <img src="https://www.movistar.co/ofertas/productos-hogar-y-movil/images/Logo.png" alt="logo">
                <div class="login__block__body">
                    <p class="mt-4">Lorem ipsum dolor fringilla enim feugiat commodo sed ac lacus.</p>

                    <div class="form-group form-group--float form-group--centered">
                        <input type="text" class="form-control" id="email" autocomplete="off">
                        <label>Correo electronico</label>
                        <i class="form-group__bar"></i>
                    </div>
 					<div class="form-group emailno">
                    	<span><strong>Correo invalido</strong></span>
                    </div>
                     <div class="form-group nofound">
                    	<span><strong>No existe tu correo</strong></span>
                    </div>
                    <button class="btn btn--icon login__block__btn emailbtn"><i class="zmdi zmdi-check"></i></button>
                     <div class="form-group otc">
                    	<span><a href="#" class="iniSes">Iniciar Sesión</a></span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Vendors -->
        <script src="<%=path %>assets/vendors/bower_components/jquery/dist/jquery.min.js"></script>
        <script src="<%=path %>assets/vendors/bower_components/popper.js/dist/umd/popper.min.js"></script>
        <script src="<%=path %>assets/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

        <!-- App functions and actions -->
        <script src="<%=path %>assets/js/app.min.js"></script>
        <script src="<%=path %>assets/js/scripts.min.js"></script>
    	<script src="<%=path %>assets/js/js.js"></script>
    </body>
</html>