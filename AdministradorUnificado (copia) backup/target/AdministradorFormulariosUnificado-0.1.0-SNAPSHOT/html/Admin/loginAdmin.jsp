<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
	<head>

		<title>Autenticación</title>
		<!--Import Google Icon Font-->
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		
		<!-- Compiled and CSS -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/materialize/css/materialize.css">
		<!-- import my own styles -->
		<link href="<%=request.getContextPath()%>/resources/css/Admin/login.css" rel="stylesheet">
		
		<!-- Compiled and JavaScript -->
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<!-- Compiled and JavaScript -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.js"></script>
		
		<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/Admin/login.js"></script>
	</head>
	
	<body>
		<div class="container">
			<div class="section">
			  	<div class="row" >
			    	<div class="col s12 m6 offset-m3">
			      		<div class="card">
					        <form method="POST" action="<%=request.getContextPath()%>/LoginServlet">
				        		<div class="card-content">
								  <span class="card-title black-text center-align"><b>INICIAR SESIÓN</b></span>
						            <div class="row" style="margin: auto;">
						            	<input id="opcion" name="<portlet:namespace/>opcion" type="hidden" value="login">
							            <div class="input-field col s12">
							                <input id="username" name="username" autocomplete="off" type="text" class="validate" minlength=5 maxlength=25 required>
											<label for="username">Username</label>
							            </div>
						            </div>
						            <div class="row" style="margin: auto;">
						            	<div class="input-field col s12">
						                	<input id="password" name="password" type="password" class="validate" autocomplete="current-password" minlength=5 maxlength=25 required>
											<label for="password">Password</label>
						              	</div>
						            </div>
						        </div>
				        		<div class="card-action">
					        		<center>
										<button class="waves-effect waves-light btn-large" id="btnLogin" onsubmit="openModal()" type="submit">Ingresar</button>
									</center>
		        				</div>
					        </form>
	    				</div>
	    			</div>
	    		</div>
    		</div>
    	</div>
    	
    	<%	
		String rtaLogin = (String)request.getSession().getAttribute("message");
		request.getSession().invalidate();
		%>
		
		<%if(rtaLogin == "Incorrecto"){%>
			<script>Materialize.toast('Datos Incorrectos!\nIntente Nuevamente.', 4000, 'orange lighten-1 rounded')</script>
		<%} else if((String)request.getAttribute("message") == "Error"){%>
			<script>Materialize.toast('Error al intentar realizar el inicio de sesión.}', 4000, 'rounded')</script>
		<%} %>
	</body>
</html>
