$(".page-loader").fadeIn();
$(document).ready(function() {	

//	themes=["indigo", "blue", "green", "red", "orange", "teal", "cyan", "blue-grey", "brown"];
	themes=["movistargreen"];
	var number_themes=themes.length;
	number=Math.floor(Math.random()*number_themes);
	console.log(themes[number]);
	$("#body").attr('data-ma-theme',themes[number]);
	
	$("#create-admin").hide();
	
	$('input[type=radio][name=options]').change(function() {
		formHide();
	    if (this.value == 'user') {
	    	buttonCreateUser();
//	    	$(".modal-content").css('height', '400px');
	    	$("#create-user").fadeIn(800);
	    	$("#create-admin").hide();
	    	$(".modal-content").fadeIn(800);
	    }
	    else if (this.value == 'admin') {
	    	buttonCreateAdmin();
//	    	$(".modal-content").css('height', '330px');
	    	$("#create-admin").fadeIn(800);
	    	$("#create-user").hide();
	    	$(".modal-content").fadeIn(800);
	    }
	});
	
	$('button').click(function (event) {
		 if (this.hasAttribute("disabled")) {
//             alert('True')
           } else {
//             alert('False')
           }
	});
	
	
//	Validacion Crear Usuario
	$("#error-create").hide();
	
	$("#open_modal").click(function(){	
		
		formHide();
	});
	
	$("#open_modal").click(function(){	
		
		
	});
	var nomForm;
	function formHide(){
		setTimeout(function() {
			if( $('#create-user').is(":visible") ){
				nomForm = "create-user";
				$('input[name="createUser"]').keyup(function(){
			        if($('#name').val() != '' && $('#email').val() != '' && $('#cargo').val() != ''){
					    	$('#btn_create').removeAttr('disabled');
					    	$('#btn_create').removeClass('btn-link');
					    	$('#btn_create').addClass('btn-outline-primary');
			        }else{
			        	$('#btn_create').attr('disabled',true);
				    	$('#btn_create').addClass('btn-link');
				    	$('#btn_create').removeClass('btn-outline-primary');
			        }
				});

			}else if($('#create-admin').is(":visible")){
//				 alert('Admin visible');
				 nomForm = "create-admin";
				 $('input[name="createAdmin"]').keyup(function(){
				        if($('#nombre').val() != '' && $('#correo').val() != '' && $('#usuario').val() != ''){
						    	$('#btn_create').removeAttr('disabled');
						    	$('#btn_create').removeClass('btn-link');
						    	$('#btn_create').addClass('btn-outline-primary');
				        }else{
				        	$('#btn_create').attr('disabled',true);
					    	$('#btn_create').addClass('btn-link');
					    	$('#btn_create').removeClass('btn-outline-primary');
				        }
					});
			}
	    }, 500);
	}
	
	function buttonCreateUser(){
		if($('#name').val() != '' && $('#email').val() != '' && $('#cargo').val() != ''){
			$('#btn_create').removeAttr('disabled');
	    	$('#btn_create').removeClass('btn-link');
	    	$('#btn_create').addClass('btn-outline-primary');
		}else{
			$('#btn_create').attr('disabled',true);
	    	$('#btn_create').addClass('btn-link');
	    	$('#btn_create').removeClass('btn-outline-primary');
		}
	}
	
	function buttonCreateAdmin(){
		if($('#nombre').val() != '' && $('#correo').val() != '' && $('#usuario').val() != ''){
			$('#btn_create').removeAttr('disabled');
	    	$('#btn_create').removeClass('btn-link');
	    	$('#btn_create').addClass('btn-outline-primary');
		}else{
			$('#btn_create').attr('disabled',true);
	    	$('#btn_create').addClass('btn-link');
	    	$('#btn_create').removeClass('btn-outline-primary');
		}
	}
	
	$("#btn_cancel_create").click(function(){
		$('#create-user')[0].reset();
		$('#create-admin')[0].reset();
		$('#btn_create').attr('disabled',true);
    	$('#btn_create').addClass('btn-link');
    	$('#btn_create').removeClass('btn-outline-primary');
	});
	
	$("#btn_create").click(function(){
		alert(nomForm);
	});
	
//	Listar Tablas
	
	function showTables(){
		$.ajax({
			type : "POST",
			url : "/superAdmin",
			data : {
				opcion : "showTables",
			},
			beforeSend : function() {	
				console.log("send");
			},
			success : function(data) {
				showPermisos();
				JSON.parse(data, function (k ,v) {
					if(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," != v && ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," != v){
						$("#show--tables").append('<li class="dTables" name="' + k + '">' + v + '</li>');
					}
				});
					try{
										
					}catch(Exc){					
//						
					}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("error");
				
			},done: function(){
			}
		});
	}
	
//	Listar select Permisos
	
	function showPermisos(){
		$.ajax({
			type : "POST",
			url : "/superAdmin",
			data : {
				opcion : "showPermisos",
			},
			beforeSend : function() {	
				console.log("send");
			},
			success : function(data) {
				JSON.parse(data, function (k ,v) {
					var res = v.toString().split( '|' );
					if(undefined != res[2]){
						$("#permisos").append('<option class="dTables" value="' + res[0] + '">' + res[2] + '</option>');
					}
					
				});
					try{
										
					}catch(Exc){					
//						
					}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("error");
				
			},done: function(){
			}
		});
	}
//	Buscador Tablas

	$(".btn_ok").click(function(){
		$(".onkeyup").css('display', 'none');
		$('#find_table').val('');
		$(".dTables").css("display", "list-item");
	});
	
	$(document).on("keyup", function() {
		if( $('#data-base').is(":visible") ){
//		    alert('Elemento visible');
			$(".onkeyup").css('display', 'block');
			$("#find_table").focus();
			
		}
	});
	
	$("#find_table").on("keyup", function() {
		  var patron = $(this).val();
		  // si el campo está vacío
		  if (patron == "") {
		    // mostrar todos los elementos
		    $(".dTables").css("display", "list-item");
		    // si tiene valores, realizar la búsqueda
		  } else {
		    // atravesar la lista
		    $(".dTables").each(function() {
		      if ($(this).text().indexOf(patron) < 0) {
		        // si el texto NO contiene el patrón de búsqueda, esconde el elemento
		        $(this).css("display", "none");
		      } else {
		        // si el texto SÍ contiene el patrón de búsqueda, muestra el elemento
		        $(this).css("display", "list-item");
		      }
		    });
		  }
		});
	
	//esta funcion lista los registros pasando parametros de la datatable y la url 
	function listar() {
		
		try{
			$.ajax({
				type : "POST",
				url : "/superAdmin",
				data : {
					opcion : "listar"
				},
				beforeSend : function() {
					$(".page-loader").fadeIn();
					console.log("send");
//					$("#modalActualizandoRegistros").show();
				},
				success : function(data) {
					try{
							showTables();
					        $("#data-table").DataTable({
					        
					            autoWidth: !1,
					            responsive: !0,
					            ajax: '/json',
					            lengthMenu: [[15, 30, 45, -1], ["15 Rows", "30 Rows", "45 Rows", "Everything"]],
					            columnDefs: [
					                {
					                    "targets": [ 0 ],
					                    "visible": false,
					                    "searchable": false
					                }
					            ],
					            language: {
					                searchPlaceholder: "Search for records..."
					            },
					            sDom: '<"dataTables__top"lfB>rt<"dataTables__bottom"ip><"clear">',
					            buttons: [{
					                extend: "excelHtml5",
					                title: "Export Data"
					            }, {
					                extend: "csvHtml5",
					                title: "Export Data"
					            }, {
					                extend: "print",
					                title: "Material Admin"
					            }],
					            initComplete: function(a, b) {
					                $(this).closest(".dataTables_wrapper").find(".dataTables__top").prepend('<div class="dataTables_buttons hidden-sm-down actions"><span class="actions__item zmdi zmdi-print" data-table-action="print" /><span class="actions__item zmdi zmdi-fullscreen" data-table-action="fullscreen" /><div class="dropdown actions__item"><i data-toggle="dropdown" class="zmdi zmdi-download" /><ul class="dropdown-menu dropdown-menu-right"><a href="" class="dropdown-item" data-table-action="excel">Excel (.xlsx)</a><a href="" class="dropdown-item" data-table-action="csv">CSV (.csv)</a></ul></div></div>')
					                tableD = $(this);
					            }
					        }),
					        $(".dataTables_filter input[type=search]").focus(function() {
					            $(this).closest(".dataTables_filter").addClass("dataTables_filter--toggled")
					           
					        }),
					        $(".dataTables_filter input[type=search]").blur(function() {
					            $(this).closest(".dataTables_filter").removeClass("dataTables_filter--toggled")
					        }),
					        $("body").on("click", "[data-table-action]", function(a) {
					            a.preventDefault();
					            var b = $(this).data("table-action");
					            if ("excel" === b && $(this).closest(".dataTables_wrapper").find(".buttons-excel").trigger("click"),
					            "csv" === b && $(this).closest(".dataTables_wrapper").find(".buttons-csv").trigger("click"),
					            "print" === b && $(this).closest(".dataTables_wrapper").find(".buttons-print").trigger("click"),
					            "fullscreen" === b) {
					                var c = $(this).closest(".card");
					                c.hasClass("card--fullscreen") ? (c.removeClass("card--fullscreen"),
					                $("body").removeClass("data-table-toggled")) : (c.addClass("card--fullscreen"),
					                $("body").addClass("data-table-toggled"))
					            }
					        }),
					        $('#searchGlobal').on('keyup', function () {
					        	console.log("Aqui estaria buscando" + tableD);
					        	tableD.search( this.value ).draw();
					        });
					        $(".page-loader").fadeOut();
//						$("#modalActualizandoRegistros").hide();
					} catch(Exc){
//						Materialize.toast("Error Al Mostar Registros", 4000, 'rounded');
//						$("#modalActualizandoRegistros").hide();
						console.log(Exc);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("error");
//					$("#modalActualizandoRegistros").hide();
				}
			});
	    }
		catch(Exc){
//			$("#modalActualizandoRegistros").hide();
		}
	}

	listar();
	

	
});





