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
	ArrayList<Rol> roles = new ArrayList<Rol>();
	HttpSession sesion = request.getSession(false);
	
	if(session.getAttribute("rol")=="administrador"){
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
			}
	}else{
		response.sendRedirect("/");
	}
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
	<title>
		<%=nom %>
	</title>
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
	var nom = '<%=nom %>';
	console.log(nom + " nombre " + tableName);
	</script>

	<!-- Compiled and JavaScript -->


</head>

<body data-ma-theme="movistarblue">
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
					<input type="text" class="search__text" placeholder="Search for people, files, documents...">
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
		                <i class="avatar-char bg-red">C</i>
		            </div>
		        
		            <div class="dropdown-menu dropdown-menu-right">
		            <div class="user__info" data-toggle="dropdown" aria-expanded="false">
                          
                            <div>
                                <div class="user__name">Malinda Hollaway</div>
                                <div class="user__email">malinda-h@gmail.com</div>
                            </div>
                        </div>
		                <a href="#" data-toggle="modal" data-target="#modal-small" class="dropdown-item">Cambiar Contraseña</a>
		                <a href="/logout" class="dropdown-item">Salir</a>
		            </div>
		        </li>

			</ul>
		</header>




		<section class="content content--full">
			<div id="adminload">
				<h1 id="nombreAdmin" class="display-4 page__center">Administrador
					<%=nom %>
				</h1>
			</div>
			<div class="card">
				<div class="card-body">


					<div class="btnsForms input-field col s3 offset-s1" style="padding-top: 2px;">
						Estas viendo los datos de:
						<select class="select2 validate" id="formulario" name="formulario" onchange="ajaxCall($('#formulario').val())"
						 required>
							<%for(Rol list: roles){%>
							<option value="<%=list.getNombre()%>">
								<%=list.getDescripcion()%>
							</option>
							<%}%>
						</select>
					</div>
					<div class="row" style="padding:20px">
						<div class="col-3">
							<div class="input-group">
								<div class="input-group-prepend red">
									<span class="input-group-text"><i class="zmdi zmdi-calendar"></i></span>
								</div>
								<input type="date" id="fechaInicio" name="fechaInicio" class="form-control date-picker" placeholder="Fecha Inicio: "><br>
							</div>
							<label class="label__error">Campo Requerido</label>
						</div>
						<div class="col-3">
							<div class="input-group">
								<div class="input-group-prepend red_1">
									<span class="input-group-text"><i class="zmdi zmdi-calendar"></i></span>
								</div>
								<input type="date" id="fechaFin" name="fechaFin" class="form-control date-picker" placeholder="Fecha Inicio: "><br>
							</div>
							<label class="label__error">Campo Requerido</label>
						</div>
						<div class="col-2">
							<button id="btn_fecha" class="btn btn-light">Filtrar</button>
<!-- 							<button onclick="clearDate();" class="btn btn-success btn--icon"><i class="zmdi zmdi-arrow-forward"></i></button> -->
						</div>
						<div class="col-4" style="text-align: right;">
						 <% if(form.equalsIgnoreCase("formulario_movistar_play")){  %>
						<div class="dropdown-demo">
                            <div class="dropdown">
                                <button class="btn btn-light" data-toggle="dropdown">Reportes</button>
                                <div class="dropdown-menu dropdown-menu-right">
                                    <a href="#" class="dropdown-item" onclick="exportarMovistarPlay($('#fechaInicio').val(), $('#fechaFin').val(), 1);">Generar Reporte Mantencion</a>
                                    <a href="#" class="dropdown-item" onclick="exportarMovistarPlay($('#fechaInicio').val(), $('#fechaFin').val(), 2);">Generar Reporte NSIP</a>
                                    <a href="#" class="dropdown-item" onclick="exportarExcelXLSX($('#fechaInicio').val(), $('#fechaFin').val());">Generar Reporte Total</a>
                                </div>
                            </div>
                        </div>
						<% } else{ %>
						<button class="btn btn-light" type="button" onclick="exportarExcelXLSX($('#fechaInicio').val(), $('#fechaFin').val());">Generar Reporte</button>
                          <% } %>
						</div>
					</div>
					<div class="table-responsive" style="padding:20px">
						<table id="data-table" class="table table-bordered"></table>
						<div class="row" style="margin-right: 0">
							<div class="input-field col s1" id="selectShowDatatable">
								<select class="select2" id="intervaloDatatable" onchange="listar($('#intervaloDatatable').val(), '', '', $('#fechaInicio').val(), $('#fechaFin').val());">
									<option value=5>5</option>
									<option value=10 selected>10</option>
									<option value=20>20</option>
									<option value=30>30</option>
									<option value=40>40</option>
								</select>
							</div>
							<div class="input-field col s4 offset-s2" id="infoDatatable">
								<p id="info_datatable">
								</p>
							</div>
							<div class="input-field col s2 offset-s3" id="paginationArrows">
								<div id="listArrowDatatabble">

									<div class="paginatorElement">
										<div class="row">
											<div class="col-5" style="text-align: right;">
												<a href="javascript:void(0)" class="arrowicon paginatorElement" onclick="listar($('#intervaloDatatable').val(), 'ant', '', $('#fechaInicio').val(), $('#fechaFin').val())"><i
													 class="zmdi zmdi-arrow-left zmdi-hc-2x"></i></a>
											</div>
											<div class="col-2">
												<select class="select2" id="paginator" onchange="changePaginator($('#paginator').val());">
												</select>
											</div>
											<div class="col-5">
												<a href="javascript:void(0)" class="arrowicon paginatorElement" onclick="listar($('#intervaloDatatable').val(), 'sig', '', $('#fechaInicio').val(), $('#fechaFin').val())"><i
													 class="zmdi zmdi-arrow-right zmdi-hc-2x"></i></a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<div class="PopUp" id="modalExportarDatos">
			<!-- Modal content -->
			<div class="PopUpContent">
				<p>Exportando registros...</p>
			</div>
		</div>
		<div class="PopUp" id="modalActualizandoRegistros">
			<!-- Modal content -->
			<div class="PopUpContent">
				<p>Actualizando registros...</p>
			</div>
		</div>

	<%@include file="snippers/modals.jsp"%>

	</main>

	<!-- Javascript -->
	<!-- Vendors -->
	<script src="<%=path %>vendors/bower_components/jquery/dist/jquery.min.js"></script>
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

	
	<script type='text/javascript' src="<%=path %>js/admin.js"></script>

	<script src="<%=path %>vendors/flatpickr/flatpickr.min.js"></script>
	<script src="https://npmcdn.com/flatpickr@4.5.2/dist/l10n/es.js"></script>
	<!-- App functions and actions -->
	<script src="<%=path %>vendors/bower_components/remarkable-bootstrap-notify/dist/bootstrap-notify.min.js"></script>
	<script src="<%=path %>js/app.min.js"></script>
	<script src="<%=path %>js/scripts.js"></script>
	
	<script>
	function clearDate(){
		$(".date-picker").flatpickr({
			defaultDate: "",
			enableTime: !1,
            maxDate: "today",
            locale: 'es',
            nextArrow: '<i class="zmdi zmdi-long-arrow-right" />',
            prevArrow: '<i class="zmdi zmdi-long-arrow-left" />'	
		})
	}
	
    function NoBack(){
    	history.go(1)
    	}
    
	</script>
	
</body>

</html>