<%@page import="java.util.ArrayList"%>
<%@page session="true" %>
<%@page import="com.grupodot.telefonica.models.Usuario"%>
<%@page import="com.grupodot.telefonica.models.Rol"%>

<%	

	HttpSession sesion = request.getSession(false);

	Usuario user = new Usuario();
	ArrayList<Rol> roles = new ArrayList<Rol>();
	String resEmpt = "0";
	String form = "";
	if(session!=null){
		if(sesion.getAttribute("userData") != null){
			try {
				user = (Usuario) sesion.getAttribute("userData");
				resEmpt = (String) request.getAttribute("invalid");
				if(user != null){
					if(user.getTipousuario().equalsIgnoreCase("superUser")){
						form = user.getRoles().get(0).getNombre();
						roles = user.getRoles();
					}else{
						
					}
				}else{
// 					sesion.invalidate();
				}
			} catch (Exception e) {
				System.out.print("JSP Exception");
				e.printStackTrace();
			}
		}
	}else{
		
	}	
%>

<%-- <portlet:actionURL var="leerCSV" name="leerCSV" /> --%>
<%-- <portlet:resourceURL var="ajaxBuscarCiudadPorDepartamento" id="ajaxBuscarCiudadPorDepartamento" /> --%>

<!-- <portlet:renderURL var="logoutR"> -->
<!-- 	<portlet:param name="request" value="LogOut"/> -->
<!-- </portlet:renderURL> -->

<!-- <portlet:resourceURL var="postUrl"></portlet:resourceURL> -->

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<meta http-equiv="expires" content="0">
 
		<meta http-equiv="Cache-Control" content="no-cache">
 
		<meta http-equiv="Pragma" CONTENT="no-cache">
		
		<title>Administrador de formulario</title>
				
		<!--Import Google Icon Font-->
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		
		<!-- Compiled and CSS -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/materialize/css/materialize.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Material-edit.css">
		
		<!-- Compiled and JavaScript -->
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<!-- Compiled and JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>
		
		<!-- import my own styles -->
		<link href="<%=request.getContextPath()%>/resources/css/Admin/superUser.css" rel="stylesheet">
		
		<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/Admin/superUser.js"></script>
		<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/datatable.js"></script>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/alasql/0.4.5/alasql.min.js"></script> 
 		<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.12.5/xlsx.core.min.js"></script>
 		<script>
		 var tableToExcel = (function() {
		      var uri = 'data:application/vnd.ms-excel;base64,'
		        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
		        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
		      return function(table, name) {
		        if (!table.nodeType) table = document.getElementById(table)
		        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
		        window.location.href = uri + base64(format(template, ctx))
		      }
		    })()
		</script>
 		
	</head>
	
	<body>
	  	<div class="banner-admin">
			<img class="banner" width="100%" height="300px" src="<%=request.getContextPath()%>/resources/imagenes/movistar_banner_principal.jpg">
		</div>
	  	<div class="container-fluid">
			<div class="row">
			  <div class="col s12">
			    <ul class="tabs">
			      <li class="tab col s3"><a class="active" href="#test1">Gestionar Usuarios</a></li>
			      <li class="tab col s3"><a href="#test2">Gestionar Permisos</a></li>
			      <li class="tab col s3"><a href="#test3">Gestionar Formularios</a></li>
			    </ul>
			  </div>
			  <h2 class="welcomeMsg">Bienvenido <%= user.getTipousuario() %>: <span class="userMgs"><%= user.getNombre() %></span></h2>
			  	<div id="test1" name="usuario" class="col s12">
			  		<a href="#addUsuario" class="waves-effect waves-light btn modal-trigger" id="btnAddUsuario">Agregar</a>
			  		<table class="centered highlight striped" id="usuario">
					</table>
				</div>
			  	<div id="test2" name="rol" class="col s12">
			  		<a href="#addRol" class="waves-effect waves-light btn modal-trigger" id="btnAddRol">Agregar</a>
			  		<table class="centered striped" id="rol">
					</table>
				</div>
				<div id="test3" class="col s12">
					<div class="row datatableDomSup">
						<div class="input-field col s2">
							<input type="date" id="fechaInicio" name="fechaInicio" class="datepicker" placeholder="   Fecha Inicio: ">
						</div>
						
						<div class="input-field col s2">
							<input type="date" id="fechaFin" name="fechaFin" class="datepicker" placeholder="   Fecha Fin ">
						</div>
						
						<div class="input-field col s2">
			 				<a class="waves-effect waves-teal btn" id="btnConsultar" onclick="listarPorFechas($('#fechaInicio').val(), $('#fechaFin').val())">Consultar</a>
						</div>
						
						<div class="btnsForms input-field col s3 offset-s1">
							<select class="validate" id="formulario" name="<portlet:namespace/>formulario" onchange="ajaxCall($('#formulario').val())" required>
								<option value="" disabled selected>Elija el formulario</option>
								<%for(Rol list: roles){%>
									<option value="<%=list.getNombre()%>"><%=list.getDescripcion()%></option>
								<%}%>
							</select>
						</div>
						<div class="btnsForms input-field col s2">
<!-- 							<a href="javascript:void(0)" class="btn waves-effect" id="btnExport" onclick="exportarPorFechas($('#fechaInicio').val(), $('#fechaFin').val());" style="font-size: 8.5pt; padding-left: 5px; padding-right:5px; text-align:center;">Generar Reporte</a> -->
							<a href="javascript:void(0)" class="btn waves-effect" id="btnExport" onclick="tableToExcel('datatable','W3C Example Table')" style="font-size: 8.5pt; padding-left: 5px; padding-right:5px; text-align:center;">Generar ReporteP</a>
						</div>
						
						<div class="btnsMovPlay input-field col s2 offset-s4">
							<select class="validate" id="formularioMovPlay" name="<portlet:namespace/>formulario" onchange="ajaxCall($('#formularioMovPlay').val())" required>
								<option value="" disabled selected>Elija el formulario</option>
								<%for(Rol list: roles){%>
									<option value="<%=list.getNombre()%>"><%=list.getDescripcion()%></option>
								<%}%>
							</select>
						</div>
					</div>
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
					<table class="centered" id="datatable">
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
					<li><a class="btn-floating tooltipped red" href="" id="btnLogout" data-position="top" data-delay="50" data-tooltip="Log Out"><i class="material-icons">close</i></a></li>
				</ul>
			</form>
		</div>
		
		<!-- Modal Structure -->
		<div id="modalPermisosDeUsuario" class="modal modal-fixed-footer">
		  <div class="modal-content">
		    <h2 id="modalUsuarioPermisoHead">Modal Header</h2>
		    <div class="row">
		    	<h4>Accesos a tablas en Bd</h4>
		    	<div class="input-field" id="modalUsuarioPermisoBody">
		    	</div>
		    </div>
		  </div>
		  <div class="modal-footer">
		    <a href="JavaScript:void(0)" class="modal-action modal-close waves-effect waves-red btn-flat red">Close</a>
		  </div>
		</div>
		
		<!-- Modal Structure -->
		<div id="addRol" class="modal">
		  <div class="modal-content">
		    <h2>Agregar Rol</h2>
		    <form id="formAddRol" method="POST">
			    <div class="row">
					<div class="input-field">
						<i class="material-icons prefix">person_outline</i>
						<input id="nombre" name="nombre" type="text" placeholder="  Nombre Completo" class="validate" minlength=5 maxlength=50 required>
					</div>
					<div class="input-field">
						<i class="material-icons prefix">info_outline</i>
						<input id="descripcion" name="descripcion" type="text" placeholder="  Descripcion" class="validate" onpaste="return false;" onkeypress="return alfaNumerico(event);" onkeyup="return alfaNumerico(event);" minlength=5 required>
					</div>
			    </div>
			    <div class="center">
				    <a href="JavaScript:void(0)" class="modal-action modal-close waves-effect waves-red btn-flat red">Close</a>
				    <input class="btn blue" type="submit" name="btnSubmit" value="Registrar">       
				</div>
		    </form>
		  </div>
		</div>
		
		<!-- Modal Structure -->
		<div id="addRolUsuario" class="modal">
		  <div class="modal-content">
		    <h2>Agregar Permiso</h2>
		    <form id="formAddRolUsuario" method="POST">
			    <div class="row">
					<input id="codUsuario" name="codUsuario" type="hidden">
					<div class="row">
						<i class="material-icons prefix">people_outline</i>
						<select class="browser-default validate" id="rol" name="rol" required>
						  <option value="" disabled selected>Elejir Rol</option>
						</select>
					</div>
					<div class="input-field">
						<i class="material-icons prefix">info_outline</i>
						<input id="descripcion" name="descripcion" type="text" placeholder="  Descripcion" class="validate" onpaste="return false;" onkeypress="return alfaNumerico(event);" onkeyup="return alfaNumerico(event);" minlength=5 required>
					</div>
			    </div>
			    <div class="center">
				    <a href="JavaScript:void(0)" class="modal-action modal-close waves-effect waves-red btn-flat red">Close</a>
				    <input class="btn blue" type="submit" name="btnSubmit" value="Registrar">       
				</div>
		    </form>
		  </div>
		</div>
		
		<!-- Modal Structure -->
		<div id="addUsuario" class="modal">
		  <div class="modal-content">
		    <h2>Agregar Usuario</h2>
		    <form id="formAddUsuario" method="POST">
			    <div class="row">
					<div class="input-field">
						<i class="material-icons prefix">account_circle</i>
						<input id="username" name="username" type="text" placeholder="  Username" class="validate" onpaste="return false;" onkeypress="return alfaNumerico(event);" onkeyup="return alfaNumerico(event);" minlength=5 maxlength=25 required>
					</div>
					<div class="input-field">
						<i class="material-icons prefix">enhanced_encryption</i>
						<input id="password" name="password" type="password" placeholder="  Password" class="validate" onpaste="return false;" onkeypress="return alfaNumerico(event);" onkeyup="return alfaNumerico(event);" minlength=5 maxlength=15 required>
					</div>
					<div class="input-field">
						<i class="material-icons prefix">person_outline</i>
						<input id="nombre" name="nombre" type="text" placeholder="  Nombre Completo" class="validate" onpaste="return false;" onkeypress="return soloLetras(event);" onkeyup="return soloLetras(event);" minlength=5 maxlength=50 required>
					</div>
					<div class="row">
						<i class="material-icons prefix">people_outline</i>
						<select class="browser-default" id="tipoUsuario" name="tipoUsuario" required>
						  <option value="" disabled selected>Tipo de usuario</option>
					      <option value="1">Administrador</option>
					      <option value="2">Visor</option>
					      <option value="4">superUser</option>
						</select>
					</div>
<!-- 					<div class="input-field"> -->
<!-- 						<i class="material-icons prefix">people_outline</i> -->
<!-- 					    <select class="validate" id="tipoUsuario" name="tipoUsuario" required> -->
<!-- 					      <option value="" disabled selected>Tipo de usuario</option> -->
<!-- 					      <option value="1">Administrador</option> -->
<!-- 					      <option value="2">Visor</option> -->
<!-- 					      <option value="4">superUser</option> -->
<!-- 					    </select> -->
<!-- 					</div> -->
			    </div>
			    <div class="center">
				    <a href="JavaScript:void(0)" class="modal-action modal-close waves-effect waves-red btn-flat red">Close</a>
				    <input class="btn blue" type="submit" name="btnSubmit" value="Registrar">       
				</div>
		    </form>
		  </div>
		</div>
		
		<!-- Modal Structure -->
		<div id="editUsuario" class="modal">
		  <div class="modal-content">
		    <h2>Editar Usuario</h2>
		    <form id="formEditUsuario" method="POST">
			    <div class="row">
			    	<input id="codUsuario" name="codUsuario" type="hidden">
					<div class="input-field">
						<i class="material-icons prefix">person_outline</i>
						<input id="nombre" name="nombre" type="text" placeholder="  Nombre Completo" class="validate" onpaste="return false;" onkeypress="return soloLetras(event);" onkeyup="return soloLetras(event);" minlength=5 maxlength=50 required>
					</div>
					<i class="material-icons prefix">people_outline</i>
					<select class="browser-default" id="tipoUsuario" name="tipoUsuario" required>
					  <option value="" disabled selected>Tipo de usuario</option>
				      <option value="1">Administrador</option>
				      <option value="2">Visor</option>
				      <option value="4">superUser</option>
					</select>
			    </div>
			    <div class="center">
				    <a href="JavaScript:void(0)" class="modal-action modal-close waves-effect waves-red btn-flat red">Close</a>
				    <input class="btn blue" type="submit" name="btnSubmit" value="Editar">       
				</div>
		    </form>
		  </div>
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
		
		<div class="PopUp" id="modalProcesando">
			<!-- Modal content -->
			<div class="PopUpContent">
				<p>Procesando...</p>
			</div>
		</div>
		
		<script type="text/javascript">
<%-- 			var URL = '<%= postUrl %>'; --%>
			var tableName = "usuario"
				
		</script>
<%-- 		<%if(resEmpt.equals("1")){%> --%>
<!-- 			<script> -->
// 				Materialize.toast('Bienvenido', 4000, 'blue rounded')
<!-- 			</script> -->
<%-- 		<%}else if(sesion == null){%> --%>
<!-- 			<script>Materialize.toast('Sesión no valida!\nDebe iniciar una sesión primero.}.', 4000, 'orange lighten-1 rounded')</script> -->
<%-- 		<%} %> --%>
	</body>
</html>