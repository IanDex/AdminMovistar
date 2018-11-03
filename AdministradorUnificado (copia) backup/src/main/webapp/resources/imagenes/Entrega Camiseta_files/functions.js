var dias = ["sun", "mon", "thu", "wes", "tue", "fri", "sat"];
var festivos;
var dateDivider = "-";

$(document).ready(function(){
	console.log(form);
	$("#fechaE").attr("disabled", true);
	chargeSelectDepartamento("departamento");
	chargeSelectNomenclatura();
	validaFestivos();
	Materialize.updateTextFields();
	$('select').material_select();
	$('#tratamientoDatos').click(function() {
		if($("#tratamientoDatos").val() == "Yes"){
			$("#tratamientoDatos").val('No');
		}
		else if($("#tratamientoDatos").val() == "No"){
			$("#tratamientoDatos").val('Yes');
		}
		else{
			$("#tratamientoDatos").val('No');
		}
	});
	$("#tratamientoDatos").val('No');
});

function construirDireccion(){
	var caja1 = $("#direccioncaja1").val();
	var caja2 = $("#direccioncaja2").val();
	var caja3 = $("#direccioncaja3").val();
	var caja4 = $("#direccioncaja4").val();
	var direccionunida = caja1+" "+caja2+" # "+caja3+" - "+caja4;
	$("#direccionUnida").val(direccionunida);
}

function validaFestivos(){
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/festivos.json",
		success : function(data) {
			for(i in data){
				festivos = data;
			}
		}
	});
}

//se carga el tiempo de entrega de un municipio
function chargeSelectTiempoEntrega(municipio){
	var txt = "";
	$.ajax({
		type : "POST",
		dataType: "JSON",
		url : "/json/tiemposEntrega.json",
		success : function(data) {
			for(i in data){
				for(j in data[i].municipios){
					if(municipio == data[i].municipios[j].nombre){
						txt += data[i].municipios[j].tiempo_entrega;
					}
				}
			}
			var today = sumarDias(1, obtener_fecha_hoy());
			
			var valida = validaDia(today);
			//var valida = validaDia(castStrToDate("2018-03-10"));
			//console.log("validando dia inicial...");
			//console.log(castDatetoStr(valida));
			
			var sumar = validaTest(txt, valida);
			//console.log("sumando dias...");
			//console.log(castDatetoStr(valida));
			
			valida = validaDia(sumar);
			//console.log("validando dia final...");
			//console.log(castDatetoStr(valida));
			
			$("#fechaCalculada").val(castDatetoStr(valida));
			initDatapicker(valida);
		}
	});
}

function validaTest(dias, fecha){
	var dias = parseInt(dias);
	for(var i=0;i<dias;i++){
		if(validaFinesOFestivos(fecha) == true){
			fecha = validaDia(fecha);
		} else{
			fecha = sumarDias(1, fecha);
		}
	}
	return fecha;
}

//obtener la fecha de hoy en formato Date
function obtener_fecha_hoy() {
    return new Date();
}

//sumar n dias a una fecha tipo Date
function sumarDias(dias, fecha){
	var dias = parseInt(dias);
	fecha.setDate(fecha.getDate() + dias);
    return fecha;
}

//no uses new Date("2018-03-10") le resta un dia a esa fecha
function castStrToDate(dateStr){
	var splitSrtDate = dateStr.toString().split(dateDivider);
	var date = new Date(parseInt(splitSrtDate[0]), parseInt(splitSrtDate[1])-1, parseInt(splitSrtDate[2]));
	return date;
}

//funcion que convierte un date en un string pd: el divisor de la fecha esta definido globalmente
function castDatetoStr(date){
	var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!
    var yyyy = date.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    return (yyyy + dateDivider + mm + dateDivider + dd);
}

//validar que la fecha no sea sabado o domingo
function validaDia(date){
	var diaactual = dias[date.getUTCDay()];
	while(diaactual === "sat" || diaactual === "sun" || festivos.indexOf(castDatetoStr(date)) !== -1){
		date = sumarDias(1, date);
		diaactual = dias[date.getUTCDay()];
	}
	return date;
}

function validaFinesOFestivos(date){
	var diaactual = dias[date.getUTCDay()];
	if(diaactual === "sat" || diaactual === "sun" || festivos.indexOf(castDatetoStr(date)) !== -1){
		return true
	}
	return false;
}

//luego que pase todas las validaciones iniciamos el datapicker
function initDatapicker(date){
	var nowDate = date;
	var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), 0, 0, 0, 0);
	$('.datepicker').pickadate({
		selectMonths: true, // Creates a dropdown to control month
		dismissible: false, // Modal can be dismissed by clicking outside of the modal
		selectYears: 15, // Creates a dropdown of 15 years to control year,
		format: 'yyyy-mm-dd',
		today: 'Today',
		clear: 'Clear',
		close: 'Ok',
		min: today, //restringe los dias anterioresw
		onSelect: function() {}
	});
	$("#fechaE").attr("disabled", false);
	Materialize.updateTextFields();
}