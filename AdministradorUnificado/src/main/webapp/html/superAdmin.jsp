<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page session="true" %>
<%@page import="com.grupodot.telefonica.Models.Usuario"%>
<%@page import="com.grupodot.telefonica.Models.Rol"%>
<% 
	String resEmpt = "0";
	Usuario user = null;
	String form = "";
	String nom = "";
	String resRoles = "";
	ArrayList<Rol> roles = new ArrayList<Rol>();
	HttpSession sesion = request.getSession(false);
	if(session.getAttribute("rol")=="superAdmin"){
	if(session!=null){
		if(sesion.getAttribute("userData") != null){
			try{
// 				resEmpt = (String)request.getAttribute("invalid");
				user = (Usuario) sesion.getAttribute("userData");
				if(user != null){
					System.out.print("JSP User "+user.getNombre());
					form = user.getRoles().get(0).getNombre();
					nom = user.getRoles().get(0).getDescripcion();
					roles = user.getRoles();
					resRoles = user.getnewRoles();
				}else{
					response.sendRedirect("/");
				}
			} catch(Exception e){
				System.out.print("JSP Exception");
				e.printStackTrace();
			}
		}else{
			response.sendRedirect("/");
		}
	}else{
		response.sendRedirect("/");
	}}else{response.sendRedirect("/");}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
//String form = "formulariotic";
String forms = (String) request.getAttribute("form");
String path =  "http://localhost:8080/assets/";
%>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Administrador</title>
    <!-- Vendor styles -->
    <link rel="stylesheet" href="<%=path %>vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" href="<%=path %>vendors/bower_components/animate.css/animate.min.css">
    <link rel="stylesheet" href="<%=path %>vendors/bower_components/jquery.scrollbar/jquery.scrollbar.css">
    <link rel="stylesheet" href="<%=path %>vendors/bower_components/select2/dist/css/select2.min.css">
    <link rel="stylesheet" href="<%=path %>vendors/flatpickr/flatpickr.min.css" />
    <!-- App styles -->
    <link rel="stylesheet" href="<%=path %>css/app.min.css">
    <link rel="stylesheet" href="<%=path %>css/admin.css">
    <!-- Compiled and CSS -->
    <!-- 		<link href="http://localhost:8086/resources/plugins/materialize/css/materialize.css" rel="stylesheet"> -->

    <script>var tableName = '<%= form %>';
	
	</script>

    <!-- Compiled and JavaScript -->


</head>

<body id="body">
    <main class="main">
        <div class="page-loader">
            <div class="page-loader__spinner">
                <svg viewBox="25 25 50 50">
                    <circle cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" />
                </svg>
            </div>
        </div>

        <header class="header">
    

    <div class="header__logo hidden-sm-down">
        <img src="<%=path %>img/logo-movistar-white.png" style="width: 60%;">

    </div>

    <form class="search">
        <div class="search__inner">
            <input autocomplete="off" type="text" class="search__text" id="searchGlobal" placeholder="Search for people, files, documents...">
            <i class="zmdi zmdi-search search__helper" data-ma-action="search-close"></i>
        </div>
    </form>

    <ul class="top-nav">
        <li class="hidden-xl-up"><a href="#" data-ma-action="search-open"><i class="zmdi zmdi-search"></i></a></li>

        <li class="dropdown hidden-xs-down">
            <a href="#" data-toggle="dropdown"><i class="zmdi zmdi-more-vert"></i></a>

            <div class="dropdown-menu dropdown-menu-right">
                <div class="dropdown-item theme-switch">
                    Theme Switch

                    <div class="btn-group btn-group-toggle btn-group--colors" data-toggle="buttons">
                        <label class="btn bg-green active"><input type="radio" value="green" autocomplete="off" checked></label>
                        <label class="btn bg-blue"><input type="radio" value="blue" autocomplete="off"></label>
                        <label class="btn bg-red"><input type="radio" value="red" autocomplete="off"></label>
                        <label class="btn bg-orange"><input type="radio" value="orange" autocomplete="off"></label>
                        <label class="btn bg-teal"><input type="radio" value="teal" autocomplete="off"></label>

                        <div class="clearfix mt-2"></div>

                        <label class="btn bg-cyan"><input type="radio" value="cyan" autocomplete="off"></label>
                        <label class="btn bg-blue-grey"><input type="radio" value="blue-grey" autocomplete="off"></label>
                        <label class="btn bg-purple"><input type="radio" value="purple" autocomplete="off"></label>
                        <label class="btn bg-indigo"><input type="radio" value="indigo" autocomplete="off"></label>
                        <label class="btn bg-brown"><input type="radio" value="brown" autocomplete="off"></label>
                    </div>
                </div>
            </div>
        </li>

        <li class="hidden-xs-down">
            <a href="#" title="Actualizar" data-toggle="tooltip" data-placement="bottom" onclick="$('#fechaInicio').val(null);$('#fechaFin').val(null);listar($('#intervaloDatatable').val(), '', '', '', '');">
                <i class="zmdi zmdi-refresh-alt"></i>
            </a>
        </li>

        <li class="dropdown hidden-xs-down">
            <div class="user__info" data-toggle="dropdown">
                <i class="avatar-char bg-purpura">C</i>
            </div>

            <div class="dropdown-menu">
                <a href="#" data-toggle="modal" data-target="#modal-small" class="dropdown-item">Cambiar Contraseña</a>
                <a href="/logout" class="dropdown-item">Salir</a>
            </div>
        </li>

    </ul>
</header>


        <section class="content content--full">

            <div class="card">
                <div class="card-body">
                    <div class="tab-container">
                        <ul class="nav nav-tabs nav-tabs--blue nav-fill" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#home-3" role="tab"  >Recursos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link dbase" data-toggle="modal" data-target="#data-base" href="#profile-3" role="tab">Base de datos</a>
                            </li>
                        </ul>

                        <div class="tab-content">
                            <div class="tab-pane active fade show" id="home-3" role="tabpanel">
                                <div class="text-right mar-10">
                                    <button class="btn btn-primary" data-toggle="modal" data-target="#create" id="open_modal">Crear</button>

                                </div>
                                <div class="table-responsive">
                            <table id="data-table" class="table table-bordered">
                                <thead class="thead-default">
                                    <tr>
                                        <th>Name</th>
                                        <th>Usuario</th>
                                        <th>Nombre</th>
                                        <th>Tipo</th>
                                        <th>Opciones</th>
                                    </tr>
                                </thead>
                               
                                <tbody>
                                    <tr>
                                        
                                         <td>
                                              
                                          </td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                        </div>
                                
                            </div>
                            
                        </div>
                    </div>

                </div>
            </div>
        </section>

        <%@include file="snippers/modals.jsp"%>

        
    </main>

    <!-- Javascript -->
    <!-- Vendors -->
    <script src="<%=path %>vendors/bower_components/jquery/dist/jquery.min.js"></script>
    <script>
        var json = jQuery.parseJSON('<%=resRoles %>');
    </script>
    
    <script src="<%=path %>vendors/bower_components/popper.js/dist/umd/popper.min.js"></script>
    <script src="<%=path %>vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="<%=path %>vendors/bower_components/jquery.scrollbar/jquery.scrollbar.min.js"></script>
    <script src="<%=path %>vendors/bower_components/jquery-scrollLock/jquery-scrollLock.min.js"></script>
    <script src="<%=path %>vendors/bower_components/select2/dist/js/select2.full.min.js"></script>
    <!-- Vendors: Data tables -->
    <script src="<%=path %>vendors/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="<%=path %>vendors/bower_components/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="<%=path %>vendors/bower_components/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="<%=path %>vendors/bower_components/jszip/dist/jszip.min.js"></script>
    <script src="<%=path %>vendors/bower_components/datatables.net-buttons/js/buttons.html5.min.js"></script>
	<script src="<%=path %>js/superSu.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>

    <script src="<%=path %>vendors/flatpickr/flatpickr.min.js"></script>
    <!-- App functions and actions -->
    <script src="<%=path %>js/app.min.js"></script>

</body>

</html>