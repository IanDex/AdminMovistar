$(document).ready(function(){
	 $( "#form-inscribete" ).submit(function( event ) {
	     event.preventDefault();
	     $.ajax({
	         type : "POST",
	         url : form,//"/formulariotic",
	         data : JSON.parse(serializeToArray('form-inscribete')),
	         success : function(data) {
	             if (data === "true") {
	                 $("#form-inscribete")[0].reset();
	                 $("#tratamientoDatos").val('No');
	                 console.log("registro creado correctamente");
	                 if(form === "formularioclientepreferencial" || form === "formularioclientepreferencialtestdrive"){
	                     Materialize.toast("Tendrás el beneficio en 3 días hábiles", 6000);
	                 } else if(form === "formularioS9"){
	                     $("#form-inscribete").fadeOut();
	                     $(".thank-msm").fadeIn();
	                 }
	                 else {
	                     Materialize.toast("Registro creado correctamente", 6000);
	                 }
	                 
	             } else if(data === "duplicated"){
	                 console.log("error al crear el registro");
	                 Materialize.toast("El número ingresado ya se encuentra registrado", 6000);
	             }
	             else {
	                 console.log("error al crear el registro");
	                 Materialize.toast("Error al crear el registro", 6000);
	             }
	         }
	     });
	 });
});

//esta funcion carga los departamentos del json colombia que posee departamentos y ciudades por departamentos
function chargeSelectDepartamentos(){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/colombia.json",
		success : function(data) {
			txt += "<option value='' disabled selected>Elija un departamento</option>";
			for(i in data){
				txt += "<option value='"+data[i].departamento+"'>"+data[i].departamento+"</option>";
			}
			$("#departamento").html(txt);
			$('#departamento').material_select();
		}
	});
}

//esta funcion carga los departamentos del json tiemposEntrega para envios a nivel nacional que posee municipios y tiempos de entrega a estos
function chargeSelectDepartamento(idSelect){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/tiemposEntrega.json",
		success : function(data) {
			txt += "<option value='' disabled selected>Elija un departamento</option>";
			for(i in data){
				txt += "<option value='"+data[i].Departamento+"'>"+data[i].Departamento+"</option>";
			}
			$("#"+idSelect).html(txt);
			$("#"+idSelect).material_select();
		}
	});
}

//se cargan las ciudades de un departamento del json ciudades
function chargeSelectCiudades(departamento){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/colombia.json",
		success : function(data) {
			txt += "<option value='' disabled selected>Elija una ciudad</option>";
			for(i in data){
				if(departamento == data[i].departamento){
					for(j in data[i].ciudades){
						txt += "<option value='"+data[i].ciudades[j]+"'>"+data[i].ciudades[j]+"</option>";
					}
					break;
				}
			}
			$("#ciudad").html(txt);
			$('#ciudad').material_select();
		}
	});
}

//se cargan los municipios del json tiemposEntrega por departamento
function chargeSelectMunicipios(departamento){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/tiemposEntrega.json",
		success : function(data) {
			txt += "<option value='' disabled selected>Elija un municipio</option>";
			for(i in data){
				if(departamento == data[i].Departamento){
					for(j in data[i].municipios){
						txt += "<option value='"+data[i].municipios[j].nombre+"'>"+data[i].municipios[j].nombre+"</option>";
					}
					break;
				}
			}
			$("#municipio").html(txt);
			$('#municipio').material_select();
		}
	});
}

function chargeSelectNomenclatura(){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/nomenclaruta.json",
		success : function(data) {
			txt += "<option value='' disabled selected>Direccion</option>";
			for(i in data){
				txt += "<option value='"+data[i]+"'>"+data[i]+"</option>";
			}
			$("#direccioncaja1").html(txt);
			$('#direccioncaja1').material_select();
		}
	});
}

function validaNumero(numero){
	$.ajax({
		type : "POST",
		url : "/adminformulario",//"/formulariotic",
		data : {opcion: "validarnumero", form:form, numero:$("#telefono").val()},
		success : function(data) {
			if (data === "true") {
				Materialize.toast("Error al crear el registro", 6000);
			}
		}
	});
}

//convierte un formulario html en un array listo para enviar por ajax
function serializeToArray(form) {
	var text = "{";
	var arrSerialize = $('#' + form).serializeArray();
	text += "\"numero\":\"" + $("#telefono").val() + "\",";
	for (var i in arrSerialize) {
		if(arrSerialize[i].name == "opcion"){
			text += "\"" + arrSerialize[i].name + "\":\"" + arrSerialize[i].value + "\",";
		}
		else{
			text += "\"" + i + "\":\"" + arrSerialize[i].value + "\",";
		}
	}
	text = text.slice(0, text.length - 1)
	text += "}";
	return text;
}


//estas funciones validan tipos de inputs tipo texto, para que funciones deben ser agregadas las siguientes lineas en el input de tipo text onkeypress="return alfaNumerico(event);" onkeyup="return alfaNumerico(event);" 
function alfaNumerico(e){
	key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz0123456789";
    especiales = "8-37-39-46-110-190-17-18-91-32";

    tecla_especial = false
    for(var i in especiales){
         if(key == especiales[i]){
             tecla_especial = true;
             break;
         }
     }

     if(letras.indexOf(tecla)==-1 && !tecla_especial){
         return false;
     }
}

function soloLetras(e){
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = "8-37-39-46";

    tecla_especial = false
    for(var i in especiales){
         if(key == especiales[i]){
             tecla_especial = true;
             break;
         }
     }

     if(letras.indexOf(tecla)==-1 && !tecla_especial){
         return false;
     }
 }

function soloNumeros(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0123456789";
    especiales = "8-37-39-46-110-190-17-18-91-32";
    tecla_especial = false
    for(var i in especiales) {
        if(key == especiales[i]) {
            tecla_especial = true;
            break;
        }
    }

    if(letras.indexOf(tecla) == -1 && !tecla_especial)
        return false;
}

function validaNit(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0123456789-";
    especiales = "8-37-39-46-110-190-17-18-91-32";
    tecla_especial = false
    for(var i in especiales) {
        if(key == especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if(letras.indexOf(tecla) == -1 && !tecla_especial)
        return false;
}