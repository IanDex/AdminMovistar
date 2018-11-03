var s_a = 0, numRows = -1, jsonConsulta="";

//esta funcion se ejecuta una vez carga compleamente la pagina
$(document).ready(function() {
	$('select').material_select();
	var nowDate = new Date();
	var today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), 0, 0, 0, 0);
	$('.datepicker').pickadate({
		selectMonths: true, // Creates a dropdown to control month
		selectYears: 15, // Creates a dropdown of 15 years to control year,
		format: 'yyyy-mm-dd',
		today: 'Today',
		clear: 'Clear',
		close: 'Ok',
		onSelect: function() {}
	});
	$("#intervaloDatatable").hide();
	$("#paginator").hide();
	Materialize.updateTextFields();
	console.log(tableName);
	$(".PopUp").hide();
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

function obtener_fecha_hoy() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    var today = dd + '/' + mm + '/' + yyyy;
    return today;
}

function restaFechasysacardiastransc(fechaInicio, fechaFin) {
    var aFecha1 = fechaInicio.split('-');
    var aFecha2 = fechaFin.split('-');
    var fFecha1 = Date.UTC(aFecha1[0], aFecha1[1] - 1, aFecha1[2]);
    var fFecha2 = Date.UTC(aFecha2[0], aFecha2[1] - 1, aFecha2[2]);
    var dif = fFecha2 - fFecha1;
    var dias = Math.floor(dif / (1000 * 60 * 60 * 24));
    return dias;
}

function SelectPaginator(numRows){
	var txt = "";
	var showNum = $('#intervaloDatatable').val();
	console.log(showNum);
	var numPag = parseFloat(numRows / showNum);
	console.log(numPag);
	var ceilNumPag = Math.ceil(numPag);
	console.log(ceilNumPag);
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

function generarTabla(jsonStr){
	console.log("GERANDO TABLA");
	var txt = "", success, origin, headers, response;
	var json = JSON.parse(jsonStr);
	jsonConsulta = jsonStr;
	success = json[0].success;
	origin = json[0].origin;
	headers = json[0].headers;
	response = json[0].response;
	numRows = json[0].numRows;
	
	//formulario de registro
	//generarFormulario(headers);
	
	//agregamos las cabeceeras provenientes del json
	txt += "<thead>";
	txt += "<tr>";		
	for(var h = 0; h < headers.length; h++){
		txt += "<th style='width:auto;'>"+headers[h]+"</th>";
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
		txt += "</tr>";
	}
	txt += "</tbody>";
	document.getElementById("datatable").innerHTML = txt;
	numRowsInfo(numRows);
	SelectPaginator(numRows);
	console.log("Termino de generar TABLA");
}

function exportXLSXFile(JsonArr, fileTitle){
	alasql('SELECT * INTO XLSX("'+fileTitle+'.xlsx",{headers:false}) FROM ?',[JsonArr]);
}


function formatText(objArray, separador) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';
    var line = "";
    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') line += separador

            line += array[i][index];
        }
        
        str += line+"\r\n";
    }
    return str;
}


function exportFile(items, fileTitle, tipoDeArchivo, separador) {
    // Convert Object to JSON
    var jsonObject = JSON.stringify(items);
    var text = formatText(jsonObject, separador);
    var exportedFilename = fileTitle + '.' +tipoDeArchivo || 'export.'+tipoDeArchivo;
    	if(tipoDeArchivo==="csv"){
    		var blob = new Blob([text], {type: 'application/csv;charset=utf-8;'});
    	}
    	if(tipoDeArchivo==="txt"){
    		var blob = new Blob([text], {type: 'text/plain'});
    	}
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, exportedFilename);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { 
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", exportedFilename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
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
			url : "../../Admin",
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
					}catch(Exc){					
						Materialize.toast("Error Al Mostrar Registros", 4000, 'rounded');
						$("#modalActualizandoRegistros").hide();
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
	//setTimeout(listar, 1000, $('#intervaloDatatable').val(), '', $('#searchDatatable').val());
}

//FIXME Validar Si se elimina (función anterior para exportar XLSX)
function exportar(fechaInicio, fechaFin){
	console.log(tableName + " - " + fechaInicio + " - " + fechaFin);
	$.ajax({
		type : "POST",
		url : PostURL,
		data : {
			opcion : "exportar",
			tabla: tableName,
			fechaInicio: fechaInicio,
			fechaFin: fechaFin,
		},
		beforeSend : function() {
			console.log("send");
			$("#modalExportarDatos").show();
		},
		success : function(data) {
			console.log(data);
			try{
				if(data !== "false"){
					//exportFile(JSON.parse(data), "Reporte generado "+tableName);
					exportXLSXFile(JSON.parse(data), "Reporte generado "+tableName);
					Materialize.toast("Reporte generado", 4000, 'rounded');
					$("#modalExportarDatos").hide();
				}
				else{
					Materialize.toast("Error Al Crear El Reporte", 4000, 'rounded');
					console.log("error");
					$("#modalExportarDatos").hide();
				}
			} catch(Exc){
				Materialize.toast("Error Al Crear El Reporte", 4000, 'rounded');
				console.log(Exc);
				$("#modalExportarDatos").hide();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("error");
		}
	});
}

function exportarExcelXLSX(fechaInicio, fechaFin){
	console.log(tableName + " - " + fechaInicio + " - " + fechaFin);
	if(validaFecha(fechaInicio, fechaFin) === -1){
		alert("la fecha de inicio debe ser menor a la fecha de fin");
	} else{
		mostrarLoaderExport();
		var request = new XMLHttpRequest();
		var fileName = tableName+"_report.xlsx";
		request.open('POST', "../../Admin");
		request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
		request.responseType = 'blob';
		
		request.onload = function(e) {
			console.log("loaded");
		    if (this.status === 200) {
		        var blob = this.response;
		        if(window.navigator.msSaveOrOpenBlob) {
		            window.navigator.msSaveBlob(blob, fileName);
		        }
		        else{
		            var downloadLink = window.document.createElement('a');
		            var contentTypeHeader = request.getResponseHeader("Content-Type");
		            downloadLink.href = window.URL.createObjectURL(new Blob([blob], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }));
		            downloadLink.download = fileName;
		            document.body.appendChild(downloadLink);
		            downloadLink.click();
		            document.body.removeChild(downloadLink);
		            ocultarLoaderExpor();
		            Materialize.toast("Archivo Generado", 4000, 'blue rounded');
		        }
		      }
		   };
		   request.onerror = function(e) {
			   Materialize.toast("Error Al Crear El Reporte", 4000, 'red rounded');
			   alert("Error " + e.target.status + " ocurrio mientras se recibía el documento.");
			   ocultarLoaderExpor();
		   };
		   console.log(tableName);
		   request.send("opcion=exportar&tabla="+tableName+"&fechaInicio="+fechaInicio+"&fechaFin="+fechaFin+"");
		   
		  
	}
}

function mostrarLoaderExport(){
	$(document).ready(function(){
	    $("#modalExportarDatos").show();
	});
}

function ocultarLoaderExpor(){
	$(document).ready(function(){
	    $("#modalExportarDatos").hide();
	});
}

function exportarMovPlay(fechaInicio, fechaFin, reporte){
	$.ajax({
		type : "POST",
		url : PostURL,
		data : {
			opcion : "exportJSON",
			tabla: tableName,
			fechaInicio: fechaInicio,
			fechaFin: fechaFin,
		},
		beforeSend : function() {
			console.log("send");
			$("#modalExportarDatos").show();
		},
		success : function(data) {
			console.log(data);
			try{
				if(data !== "false"){
					switch(reporte){
					
					case 1: 
						generarMplayMantencion(JSON.parse(data));
						console.log("generar TXT");
						break;
						
					case 2:
						generarMplayNsip(JSON.parse(data));
						console.log("generar Mplay Nsip");
						break;
						
					case 3:
						generarXls1(JSON.parse(data));
						console.log("generar Mplay Total");
						break;
					}
					console.log(JSON.parse(data));
					Materialize.toast("Reporte generado", 4000, 'rounded');
					$("#modalExportarDatos").hide();
				}
				else{
					Materialize.toast("Error Al Crear El Reporte", 4000, 'rounded');
					console.log("error");
					$("#modalExportarDatos").hide();
				}
			} catch(Exc){
				Materialize.toast("Error Al Crear El Reporte", 4000, 'rounded');
				console.log(Exc);
				$("#modalExportarDatos").hide();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("error");
		}
	});
}

//<<<<<<< HEAD
function generarTxt(data){}
//=======


function generarMplayMantencion(data){
//>>>>>>> fffa98e219b01a1db3c8faaae76a45ef2ebd912d
	data = data.splice(1, data.length);
	//var header = "[[\"LINEA MOVIL DEL CLIENTE\",\"CONSULTA PAGINA WEB\",\"WEB\",\"Activar\",\"servicio\",\"   \",\"   \",\"   \",\"Directo\",\"POR CONFIRMAR CON CANAL ON LINE\",\"observacion1\",\"observacion2\"],";
	var header= "[";
	var burn = ",\"CONSULTA PAGINA WEB\",\"WEB\",\"Activar\",\"PAMPM\",\"   \",\"   \",\"   \",\"Directo\",\"24358\",\"SE TRAMITA ACTIVACIÓN MOVISTAR PLAY LITE POR SOLICITUD DEL CLIENTE EN www.movistar.co\",\"SE TRAMITA ACTIVACIÓN MOVISTAR PLAY LITE POR SOLICITUD DEL CLIENTE EN www.movistar.co \"],";
	var arrData = header;
	
	for(var i in data){
		arrData += "[\""+data[i][2]+"\""+burn;
	}
	arrData = arrData.slice(0, arrData.length-1);
	arrData += "]";
	console.log(arrData);
	exportFile(JSON.parse(arrData), "ACTIVACION MPLAY MANTENCION", "csv",",");
}

function generarXls1(data){
	data = data.splice(1, data.length);
	var arrData = "[[\"Línea Movil Del Cliente\",\"Correo Electrónico\",\"Fecha De Solicitud\"],"; // ,\"CODABONADO\"
	for(var i in data){
		arrData += "[\""+data[i][2]+"\",\""+data[i][3]+"\",\""+data[i][4]+"\"],";
	}
	arrData = arrData.slice(0, arrData.length -1);
	arrData += "]";
	console.log(arrData);
	exportXLSXFile(JSON.parse(arrData), "ACTIVACION MPLAY TOTAL");
	
}

function generarMplayNsip(data){
	data = data.splice(1, data.length);
	var arrData = "[";
	for(var i in data){
		arrData += "[\""+data[i][2]+"\",\""+data[i][3]+"\"],";
	}
	arrData = arrData.slice(0, arrData.length-1)
	arrData += "]";
	console.log(arrData);
	exportFile(JSON.parse(arrData), "ACTIVACION MPLAY NSIP", "csv", ",");
	
}

function validaFecha(fechaInicio, fechaFin){
	if(fechaInicio == "" || fechaFin == ""){
		return 1;
	} else if(restaFechasysacardiastransc(fechaInicio, fechaFin) < 0){
		return -1;
	}else{
		return 0;
	}
}

function listarPorFechas(fechaInicio, fechaFin){
	if(validaFecha(fechaInicio, fechaFin) === -1){
		alert("la fecha de inicio debe ser menor a la fecha de fin");
	}
	else{
		listar($('#intervaloDatatable').val(), '', '', fechaInicio, fechaFin);
	}
}

function exportarPorFechas(fechaInicio, fechaFin){
	if(validaFecha(fechaInicio, fechaFin) === -1){
		alert("la fecha de inicio debe ser menor a la fecha de fin");
	} else{
		exportar(fechaInicio, fechaFin);
	}	
}

function exportarMovistarPlay(fechaInicio, fechaFin, reporte){
	if(validaFecha(fechaInicio, fechaFin) === -1){
		alert("la fecha de inicio debe ser menor a la fecha de fin");
	} else{
			exportarMovPlay(fechaInicio, fechaFin, reporte);
			
		}	
}
