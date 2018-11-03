$(document).ready(function() {
	$("#formulario").hide();
	Materialize.updateTextFields();
	$('select').material_select();
	$(".PopUp").hide();
});

function cargarSelectformularios(){
	for(i in dataformularios){
		txt += "<option value='"+dataformularios[i].form+"'>"+dataformularios[i].user+"</option>";
	}
	$("#formularios").html(txt);
	$('#formularios').material_select();
}

$('#btnLogin').click(function() {
	login();
});

function login(){
	console.log("Iniciar Sesión");
	var username = $('#username').val();
	var password = $('#password').val();
	$.ajax({
		type : "POST",
		url : "LoginServlet",
		data : {
			username : username,
			password: password,
		},
		beforeSend : function() {
			console.log("send");
			$("#modalEspera").show();
		},
		success : function(data) {
			console.log(data);
			try{
				if(data == "INCORRECTO"){
					Materialize.toast('Datos Incorrectos!\nIntente Nuevamente.', 4000, 'orange lighten-1 rounded')
					$("#modalEspera").hide();
				}
			} catch(Exc){
				Materialize.toast("Error al intentar iniciar sesión", 4000, 'red rounded');
				console.log(Exc);
				$("#modalEspera").hide();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("error");
			$("#modalEspera").hide();
		}
	});
}