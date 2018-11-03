var s_a = 0, numRows = -1, jsonConsulta="";

//esta funcion se ejecuta una vez carga compleamente la pagina
$(document).ready(function() {
	getAllRoles();
	
	$('select').material_select();
	
	$('.button-collapse').sideNav({
	      menuWidth: 300, // Default is 300
	      edge: 'left', // Choose the horizontal origin
	      closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
	      draggable: true, // Choose whether you can drag to open on touch screens,
	      onOpen: function(el) { /* Do Stuff*/ }, // A function to be called when sideNav is opened
	      onClose: function(el) { /* Do Stuff*/ }, // A function to be called when sideNav is closed
	});
	
	$('.modal').modal({
	  dismissible: false, // Modal can be dismissed by clicking outside of the modal
	});
	
	$("ul.tabs").tabs({
	    onShow: function(aux) {
	    	console.log(aux);
	    	var pestaña = aux[0].id;
	    	var tabla = aux[0].name;
	    	if(pestaña === "test3"){
	    		
	    	} else if(pestaña === "test2"){
	    		tableName = "rol";
	    		listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	    	} else if(pestaña === "test1"){
	    		tableName = "rol";
	    		listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	    	} else{
	    		
	    	}
	    },
	});
	
	$( "#formAddRol" ).submit(function( event ) {
	     event.preventDefault();
	     $.ajax({
	         type : "POST",
	         url : URL,
	         data : JSON.parse(serializeToArray('formAddRol')),
	         success : function(data) {
	             if (data === "true") {
	            	 $('#formAddRol')[0].reset();
	            	 $('#addRol').modal('close');	
	                 console.log("registro creado correctamente");
	                 listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	                 Materialize.toast("Registro Creado Correctamente", 4000, 'rounded');
	             } else if(data === "duplicated"){
	                 console.log("el registro se encuentra duplicado");
	                 Materialize.toast("El Registro Ya Se Ha Registrado", 4000, 'rounded');
	             } else {
	                 console.log("error al crear el registro");
	                 Materialize.toast("Error Al Crear Registro", 4000, 'rounded');
	             }
	         }
	     });
	});
	
	$( "#formAddUsuario" ).submit(function( event ) {
	     event.preventDefault();
	     $.ajax({
	         type : "POST",
	         url : URL,
	         data : JSON.parse(serializeToArray('formAddUsuario')),
	         success : function(data) {
	             if (data === "true") {
	                 $('#formAddUsuario')[0].reset();
	                 $('#AddUsuario').modal('close');
	                 console.log("registro creado correctamente");
	                 listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	                 Materialize.toast("Registro Creado Correctamente", 4000, 'rounded');
	             } else if(data === "duplicated"){
	                 console.log("el registro se encuentra duplicado");
	                 Materialize.toast("El Registro Ya Se Ha Registrado", 4000, 'rounded');
	             } else {
	                 console.log("error al crear el registro");
	                 Materialize.toast("Error Al Crear Registro", 4000, 'rounded');
	             }
	         }
	     });
	});
	
	$( "#formAddRolUsuario" ).submit(function( event ) {
	     event.preventDefault();
	     $.ajax({
	         type : "POST",
	         url : URL,
	         data : {opcion: "registrar", tabla: "usuario_rol", usuario: $("#formAddRolUsuario #codUsuario").val(), rol: $("#formAddRolUsuario #rol").val(), descripcion: $("#formAddRolUsuario #descripcion").val()},
	         success : function(data) {
	             if (data === "true") {
	            	 $('#formAddRolUsuario')[0].reset();
	            	 $('#addRolUsuario').modal('close');
	                 console.log("registro creado correctamente");
	                 Materialize.toast("Registro Creado Correctamente", 4000, 'rounded');
	             } else if(data === "duplicated"){
	                 console.log("el registro se encuentra duplicado");
	                 Materialize.toast("El Registro Ya Se Ha Registrado", 4000, 'rounded');
	             } else {
	                 console.log("error al crear el registro");
	                 Materialize.toast("Error Al Crear Registro", 4000, 'rounded');
	             }
	         }
	     });
	});
	
	$( "#formEditUsuario" ).submit(function( event ) {
	    event.preventDefault();
	    $.ajax({
	        type : "POST",
	        url : URL,
	        data : {opcion: "editar", tabla: "usuario", usuario: $("#formEditUsuario #codUsuario").val(), nombre: $("#formEditUsuario #nombre").val(), tipousuario: $("#formEditUsuario #tipoUsuario").val()},
	        success : function(data) {
	            if (data === "true") {
	           	 $('#formEditUsuario')[0].reset();
	           	 $('#editUsuario').modal('close');
	                console.log("registro editado correctamente");
	                listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	                Materialize.toast("Registro Editado Correctamente", 4000, 'rounded');
	            } else {
	                console.log("error al editar el registro");
	                Materialize.toast("Error Al Editar Registro", 4000, 'rounded');
	            }
	        }
	    });
	});
	
	$("#intervaloDatatable").hide();
	
	$("#paginator").hide();
	
	$("#formulario, #formularioMovPlay").hide();
	
	$("#tipoUsuario").hide();
		
	$(".PopUp").hide();
	
	console.log(tableName);
		
	showButtons(tableName);
	
	listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
	
});

function showButtons(table){
	if(table === "formulario_movistar_play"){
		$(".btnsMovPlay").show();
		$(".btnsForms").hide();
	} else{
		$(".btnsForms").show();
		$(".btnsMovPlay").hide();
	}
}

function ajaxCall(tabla){
	tableName = tabla;
	showButtons(tableName);
	listar($('#intervaloDatatable').val(), '', '', '', $('#fechaInicio').val(), $('#fechaFin').val());
}

function getRoles(idUsuario, nombreUsuario){
	$.ajax({
        type : "POST",
        url : URL,
        data : {opcion: "getRolesUsuario", usuario: idUsuario},
        beforeSend : function() {
			console.log("send");
			$("#modalProcesando").show();
		},
		success : function(data) {
			try{
				console.log(data);
				$("#modalProcesando").hide();
	        	showRolesUsuario(idUsuario, JSON.parse(data), nombreUsuario);
	        	
			} catch(Exc){
				Materialize.toast("Error Al Mostar Procesar", 4000, 'rounded');
				$("#modalProcesando").hide();
				console.log(Exc);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("error");
			$("#modalProcesando").hide();
		}
    });
}

function getRoles(idUsuario, nombreUsuario){
	$.ajax({
        type : "POST",
        url : URL,
        data : {opcion: "getRolesUsuario", usuario: idUsuario},
        beforeSend : function() {
			console.log("send");
			$("#modalProcesando").show();
		},
		success : function(data) {
			try{
				console.log(data);
				$("#modalProcesando").hide();
	        	showRolesUsuario(idUsuario, JSON.parse(data), nombreUsuario);
	        	
			} catch(Exc){
				Materialize.toast("Error Al Mostar Procesar", 4000, 'rounded');
				$("#modalProcesando").hide();
				console.log(Exc);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("error");
			$("#modalProcesando").hide();
		}
    });
}

function deleteRol(codUsuario, codRol){
	$.ajax({
        type : "POST",
        url : URL,
        data : {opcion: "delete", tabla: "usuario_rol", usuario: codUsuario, rol: codRol},
        success : function(data) {
            if (data === "true") {
           	 $('#modalPermisosDeUsuario').modal('close');
                console.log("registro eliminado correctamente");
                Materialize.toast("Registro Eliminado Correctamente", 4000, 'rounded');
            } else {
                console.log("error al eliminar el registro");
                Materialize.toast("Error Al Elimminar", 4000, 'rounded');
            }
        }
    });
}

function getAllRoles(){
	var txt = "";
	$.ajax({
		type : "POST",
		url : URL,
		data: {opcion: "getAllRoles"},
		success : function(data) {
			data = JSON.parse(data);
			txt += "<option value='' disabled selected>Elejir Rol</option>";
			for(i in data){
				txt += "<option value='"+data[i][0]+"'>"+data[i][2]+"</option>";
			}
			$("#formAddRolUsuario #rol").html(txt);
			$('#formAddRolUsuario #rol').material_select();
		}
	});
}

//esta funcion lista los registros pasando parametros de la datatable y la url 
function listar(limit, sig_ant, palabra, fechaInicio, fechaFin) {
	if (sig_ant == "sig") {
		s_a = parseInt(s_a * 1 + limit * 1);
	} else if (sig_ant == "ant" && s_a != 0) {
		s_a = parseInt(s_a * 1 - limit * 1);
	}
	if(numRows != -1 && s_a > numRows){s_a = parseInt(s_a * 1 - limit * 1);return;}
	try{
		$.ajax({
			type : "POST",
			url : URL,
			data : {
				tabla: tableName,
				opcion : "listar",
				limit : limit,
				interval : s_a,
				palabra : palabra,
				fechaInicio : fechaInicio,
				fechaFin : fechaFin
			},
			beforeSend : function() {
				console.log("send");
				$("#modalActualizandoRegistros").show();
			},
			success : function(data) {
				console.log(data);
				try{
					generarTabla(data)
					$("#modalActualizandoRegistros").hide();
				} catch(Exc){
					Materialize.toast("Error Al Mostar Registros", 4000, 'rounded');
					$("#modalActualizandoRegistros").hide();
					console.log(Exc);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("error");
				$("#modalActualizandoRegistros").hide();
			}
		});
    }
	catch(Exc){
		$("#modalActualizandoRegistros").hide();
	}
}

function generarTabla(jsonStr){
	var txt = "", success, origin, headers, response;
	var json = JSON.parse(jsonStr);
	jsonConsulta = jsonStr;
	success = json[0].success;
	origin = json[0].origin;
	headers = json[0].headers;
	response = json[0].response;
	numRows = json[0].numRows;
	
	//agregamos las cabeceeras provenientes del json
	txt += "<thead>";
	txt += "<tr>";		
	for(var h = 0; h < headers.length; h++){
		txt += "<th>"+headers[h]+"</th>";
	}
	if(tableName === "usuario"){
		txt += "<th class='thButon'>Gestionar Permisos</th>";
	}
	txt += "</tr>";
	txt += "</thead>";
	
	//agregamos el body provenientes del json
	txt += "<tbody>";
	for (var i = 0; i < response.length; i++) {
		txt += "<tr>";
		for(var j = 0; j < response[i].length; j++){
			txt += "<td>"+response[i][j]+"</td>";
		}
		if(tableName === "usuario"){
			txt += "<td class='actionTd'>";
				txt += "<a href='JavaScript:void(0);' class='waves-effect waves-light btn-flat' id='aGetRoles' onclick='getRoles(\""+response[i][0]+"\", \""+response[i][1]+"\");'><i class='material-icons'>remove_red_eye</i></a>";
				txt += "<a href='JavaScript:void(0);' class='waves-effect waves-light btn-flat' id='aAddRole' onclick='showAddRolUser("+response[i][0]+");'><i class='material-icons'>add</i></a>";
				txt += "<a href='JavaScript:void(0);' class='waves-effect waves-light btn-flat' id='aEditUsuario' onclick='showEditRolUser(\""+response[i]+"\");'><i class='material-icons'>edit</i></a>";
			txt += "</td>";
		}
		txt += "</tr>";
	}
	txt += "</tbody>";
	
	if(tableName !== "usuario" && tableName !== "rol"){
		document.getElementById("datatable").innerHTML = txt;
	} else{
		document.getElementById(tableName).innerHTML = txt;
	}
	
	numRowsInfo(numRows);
	SelectPaginator(numRows);
}

function changePaginator(page){
	s_a = parseInt(page)*parseInt($('#intervaloDatatable').val()) - parseInt($('#intervaloDatatable').val());
	//listar(limit, sig_ant, palabra, fechaInicio, fechaFin)
	listar($('#intervaloDatatable').val(), '', '', $('#fechaInicio').val(), $('#fechaFin').val());
}

function numRowsInfo(numRows){
	var limit = parseInt(s_a*1 + $('#intervaloDatatable').val()*1);
	if(limit > numRows){
		limit = numRows;
	}
	$("#info_datatable").html("mostrando de "+ s_a + " a " + limit + " filtrados de " + numRows + " registros");
}

function SelectPaginator(numRows){
	var txt = "";
	var showNum = $('#intervaloDatatable').val();
	//console.log(showNum);
	var numPag = parseFloat(numRows / showNum);
	//console.log(numPag);
	var ceilNumPag = Math.ceil(numPag);
	//console.log(ceilNumPag);
	for(var i=1;i<=ceilNumPag;i++){
		if(i == 1){
			txt += "<option value="+i+" selected>"+i+"</option>";
		}
		else{
			txt += "<option value="+i+">"+i+"</option>";
		}
	}
	$("#paginator").html(txt);
	$('#paginator').material_select();
}

function showRolesUsuario(codUsuario, data, title){
	var txt = "";
	for(var i in data){
		//txt += "<p><b>"+data[i][2]+"</b><span>"+data[i][1]+"</span></p>";
		txt += "<p>"+data[i][2]+"   <a href='JavaScript:void(0);' class='btnDelete red-text' onclick='deleteRol(\""+codUsuario+"\",\""+data[i][0]+"\""+");'>X</a></p>";
	}
	if(data.length > 0){
		txt += "<p><a href='JavaScript:void(0);' class='btnDelete red-text' onclick='deleteRol(\""+codUsuario+"\")'>delete all roles</a></p>";
	}
	$("#modalUsuarioPermisoHead").html(title);
	$("#modalUsuarioPermisoBody").html(txt);
	$('#modalPermisosDeUsuario').modal('open');	
}

//convierte un formulario html en un array listo para enviar por ajax
function serializeToArray(form) {
	var text = "{";
	var arrSerialize = $('#' + form).serializeArray();
	text += "\"opcion\":\"registrar\",";
	text += "\"tabla\":\"" + tableName + "\",";
	for (var i in arrSerialize) {
		text += "\"" + arrSerialize[i].name + "\":\"" + arrSerialize[i].value + "\",";
	}
	text = text.slice(0, text.length - 1)
	text += "}";
	return text;
}

function showAddRolUser(codUsuario){
	$('#addRolUsuario').modal('open');	
	$("#formAddRolUsuario #codUsuario").val(codUsuario);
}

function showEditRolUser(usuario){
	usuario = "[\""+usuario.replace(/,/g, '\",\"')+"\"]";
	usuario = JSON.parse(usuario);
	$("#formEditUsuario #codUsuario").val(usuario[0]);
	$("#formEditUsuario #nombre").val(usuario[1]);
	$("#formEditUsuario #tipoUsuario").val(AssignRole(usuario[2]));
	$('#editUsuario').modal('open');
}

function AssignRole(strRole){
	if(strRole === "administrador"){
		return 1;
	} else if(strRole === "visor"){
		return 2;
	} else if(strRole === "administrador"){
		return 4;
	} else {
		return 0;
	}
}