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
    <meta charset="UTF-8">
    <title>.: Login :.</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>assets/css/fonts.css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
    <link rel="stylesheet" href="<%=path %>assets/css/styles.css">
    <link rel="stylesheet" href="<%=path %>assets/css/login.css">
    <style> .dis:disabled{ cursor: not-allowed; border: 1px solid #003145; padding: 6px; border-radius: 4px;} .dis{ border: 1px solid #003145; padding: 6px; border-radius: 4px;} .dis:hover{  border: 1px solid #003145; padding: 6px; border-radius: 4px; text-decoration: none;} a{text-decoration: none;}.btn:disabled {background-color: #000;} </style>
    <script src='https://unpkg.com/sweetalert2'></script>
    <script src='https://unpkg.com/promise-polyfill'></script>
    <script>
    	var form = '<%=forms%>'; 
    console.log(form);
</script>
</head>

<body>
<div id="resultado" name="resultado" class="ui-widget"></div>
<div class="modal" id="loginModal" tabindex="-1" role="dialog" data-controls-modal="loginModal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-login" role="document">
        <div class="modal-content">
            <div class="card card-signup card-plain">
                <div class="modal-header">
                    <div class="card-header card-header-primary text-center">

                        <h4 class="card-title"><img src="<%=path %>assets/img/fundacion_telefonica.png" alt=""></h4>

                    </div>
                </div>
                <form class="form" id="frmLogin" name="frmLogin">
                <div class="modal-body">
                        <div class="card-body">
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <i class="material-icons">fingerprint</i>
                                        </span>
                                    </div>
                                    <input type="text" name="user" id="user" class="form-control" placeholder="Usuario..." required value="adminpruebas" autocomplete="off" autofocus>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <i class="material-icons">lock_outline</i>
                                        </span>
                                    </div>
                                    <input type="password" name="password" id="password" placeholder="Contraseña..." class="form-control" value="movistar2018%" required autocomplete="off"/>
                                </div>
                            </div>
                        </div>
                </div>
                <div class="modal-footer justify-content-center">
                    <button type="button" class="btn btn-primary btn-link btn-wd btn-lg dis" id="btnenviar" >Entrar</button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <!--   Core JS Files   -->
    <script src="<%=path %>assets/js/jquery.min.js"></script>
    <script src="<%=path %>assets/js/scripts.min.js"></script>
    <script src="<%=path %>assets/js/js.js"></script>


</body>
</html>
<!-- by-ian 2k18 -->
