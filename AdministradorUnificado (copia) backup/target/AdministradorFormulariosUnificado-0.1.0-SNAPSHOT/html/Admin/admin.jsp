<%@page import="java.util.ArrayList"%>

<%@page session="true" %>
<%@page import="com.grupodot.telefonica.models.Usuario"%>
<%@page import="com.grupodot.telefonica.models.Rol"%>

<% 
	String resEmpt = "0";
	Usuario user = null;
	String form = "";
	ArrayList<Rol> roles = new ArrayList<Rol>();
	HttpSession sesion = request.getSession(false);
	if(session!=null){
		if(sesion.getAttribute("userData") != null){
			try{
// 				resEmpt = (String)request.getAttribute("invalid");
				user = (Usuario) sesion.getAttribute("userData");
				if(user != null){
					System.out.print("JSP User "+user.getNombre());
					form = user.getRoles().get(0).getNombre();
					roles = user.getRoles();
				}else{
					response.sendRedirect("/html/Admin/loginAdmin.jsp");
				}
			} catch(Exception e){
				System.out.print("JSP Exception");
				e.printStackTrace();
			}
		}else{
			response.sendRedirect("/html/Admin/loginAdmin.jsp");
		}
	}else{
		response.sendRedirect("/html/Admin/loginAdmin.jsp");
	}
%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Administrador de formulario</title>
		
		<script>var tableName = '<%= form %>';</script>
		
		<!--Import Google Icon Font-->
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		
		<!-- Compiled and CSS -->
		<link href="<%=request.getContextPath()%>/resources/plugins/materialize/css/materialize.css" rel="stylesheet">
		
		<!-- import my own styles -->
		<link href="<%=request.getContextPath()%>/resources/css/Admin/admin.css" rel="stylesheet">

		<!-- Compiled and JavaScript -->
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<!-- Compiled and JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>
		
		<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/Admin/admin.js"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/alasql/0.4.6/alasql.min.js"></script> 
 		<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.12.5/xlsx.core.min.js"></script> 
 		
	</head>
	
	<body >
	
	<div class="container" style="width:80%; background: #fff; margin-bottom: 15px;">
		<div style="margin-top:15px; padding: 10px; border: 1px solid #d6d6d6;">
			<div class="banner-admin">
				<img class="banner responsive-img" src="<%=request.getContextPath()%>/resources/imagenes/movistar_banner_principal.jpg">
			</div>
			
			<div class="row datatableDomSup" style="padding-left: 12px;">
				<div class="input-field col s2">
					<input type="date" id="fechaInicio" name="fechaInicio" class="datepicker" placeholder="Fecha Inicio: ">
				</div>
				
				<div class="input-field col s2">
					<input type="date" id="fechaFin" name="fechaFin" class="datepicker" placeholder="Fecha Fin: ">
				</div>
				
				<div class="input-field col s2" style="padding-top: 7px;">
	 				<a class="waves-effect waves-teal btn" id="btnConsultar" onclick="listarPorFechas($('#fechaInicio').val(), $('#fechaFin').val())">Consultar</a>
				</div>
				
				<div class="btnsForms input-field col s3 offset-s1" style="padding-top: 2px;">
					<select class="validate" id="formulario" name="formulario" onchange="ajaxCall($('#formulario').val())" required>
						<option value="" disabled selected>Elija el formulario</option>
						<%for(Rol list: roles){%>
							<option value="<%=list.getNombre()%>"><%=list.getDescripcion()%></option>
						<%}%>
					</select>
				</div>
	<%-- 			<%if(!user.getTipousuario().equalsIgnoreCase("visor")){%> --%>
					<div class="btnsForms input-field col s2" style="padding-top: 2px;">
	<!-- 					<a href="javascript:void(0)" class="btn" id="btnExport" onclick="exportarPorFechas($('#fechaInicio').val(), $('#fechaFin').val());" style="font-size: 8.5pt; padding-left: 5px; padding-right:5px; text-align:center;">Generar Reporte</a> -->
						<a href="javascript:void(0)" class="btn" id="btnExport" onclick="exportarExcelXLSX($('#fechaInicio').val(), $('#fechaFin').val());" style="font-size: 9pt; padding-left: 5px; padding-right:5px; text-align:center;">Generar Reporte</a>
					</div>
	<%-- 			<%}%> --%>
				<div class="btnsMovPlay input-field col s2 offset-s4">
					<select class="validate" id="formularioMovPlay" name="formulario" onchange="ajaxCall($('#formularioMovPlay').val())" required>
						<option value="" disabled selected>Elija el formulario</option>
						<%for(Rol list: roles){%>
							<option value="<%=list.getNombre()%>"><%=list.getDescripcion()%></option>
						<%}%>
					</select>
				</div>
			</div>
			
	<%-- 		<%if(!user.getTipousuario().equalsIgnoreCase("visor")){%> --%>
			<div class="row btnsMovPlay">
				<div class="input-field col s2 offset-s3">
					<a href="javascript:void(0)" class="btn green" id="btnExportMovPlay" onclick="exportarMovistarPlay($('#fechaInicio').val(), $('#fechaFin').val(), 1);">Generar Reporte Mantencion</a>
				</div>
				<div class="input-field col s2">
					<a href="javascript:void(0)" class="btn green" id="btnExportMovPlay" onclick="exportarMovistarPlay($('#fechaInicio').val(), $('#fechaFin').val(), 2);">Generar Reporte NSIP</a>
				</div>
				<div class="input-field col s2">
					<a href="javascript:void(0)" class="btn green" id="btnExportMovPlay" onclick="exportarMovistarPlay($('#fechaInicio').val(), $('#fechaFin').val(), 3);">Generar Reporte Total</a>
				</div>
			</div>
	<%-- 		<%}%> --%>
			<div>
				<table class="centered bordered highlight striped responsive-table" id="datatable" style="table-layout:fixed;">
				</table>
			</div>
			<div class="row">
				<div class="input-field col s1" id="selectShowDatatable">
					<select id="intervaloDatatable" onchange="listar($('#intervaloDatatable').val(), '', '', $('#fechaInicio').val(), $('#fechaFin').val());">
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
						<a href="javascript:void(0)" class="arrowicon paginatorElement" onclick="listar($('#intervaloDatatable').val(), 'ant', '', $('#fechaInicio').val(), $('#fechaFin').val())"><i class="material-icons">arrow_back</i></a> 
						<div class="paginatorElement">
							<select id="paginator" onchange="changePaginator($('#paginator').val());">
							</select>
						</div>
						<a href="javascript:void(0)" class="arrowicon paginatorElement" onclick="listar($('#intervaloDatatable').val(), 'sig', '', $('#fechaInicio').val(), $('#fechaFin').val())"><i class="material-icons">arrow_forward</i></a>
					</div>
				</div>
			</div>
		</div>
	</div>
		<div class="fixed-action-btn horizontal">
			<form action="../../LogoutServlet" method="POST">
				<a class="btn-floating btn-large green"><i class="large material-icons">mode_edit</i></a>
				<ul>
					<li><button class="btn-floating tooltipped red" type="submit" id="btnLogout" data-position="top" data-delay="50" data-tooltip="Log Out"><i class="material-icons">close</i></button></li>
					<li><a class="btn-floating tooltipped blue" data-position="top" data-delay="50" data-tooltip="Recargar Tabla" onclick="$('#fechaInicio').val(null);$('#fechaFin').val(null);listar($('#intervaloDatatable').val(), '', '', '', '');"><i class="material-icons">cached</i></a></li>
				</ul>
			</form>
		</div>
		<div class="PopUp" id="modalActualizandoRegistros">
			<!-- Modal content -->
			<div class="PopUpContent">
				<p>Actualizando registros...</p>
			</div>
		</div>
		<div class="PopUp" id="modalExportarDatos">
			<!-- Modal content -->
			<div class="PopUpContent">
				<p>Exportando registros...</p>
			</div>
		</div>
<%-- 		<%if(resEmpt.equals("1")){%> --%>
<!-- 			<script> -->
<!--  				Materialize.toast('Bienvenido', 4000, 'blue rounded') -->
<!-- 			</script> -->
<%-- 		<%}%> --%>
	
	</body>
</html>