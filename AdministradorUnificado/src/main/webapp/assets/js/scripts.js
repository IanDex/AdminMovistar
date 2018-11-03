$(document).ready(function(){

	
	$('input[name="__pass"]').keyup(function(){
        if($('#passold').val() != '' && $('#passnew').val() != '' && $('#passnew2').val() != ''){
        	if($('#passnew').val() == $('#passnew2').val()){
		    	$('#btn_pass').removeAttr('disabled');
		    	$('#btn_pass').removeClass('btn-link');
		    	$('#btn_pass').addClass('btn-outline-primary');
        	}else{
        		console.log("No es igual");
        	}
        }else{
        	$('#btn_pass').attr('disabled',true);
	    	$('#btn_pass').addClass('btn-link');
	    	$('#btn_pass').removeClass('btn-outline-primary');
        }
	});

	$("#btn_pass").click(function(){
		if($('#passold').val() != '' && $('#passnew').val() != '' && $('#passnew2').val() != ''){
			if($('#passnew').val() == $('#passnew2').val()){
			var passold = $('#passold').val();
			var passnew = $('#passnew').val();
			$.ajax({
				type : "POST",
				url : "/administrador",
				data : {
					tabla: tableName,
					opcion : "changePass",
					passold : passold,
					passnew : passnew,
					limit : "",
					interval : "",
					palabra : "",
					fechaInicio : "",
					fechaFin : ""
				},
				beforeSend : function() {
					$(".page-loader").fadeIn();
				},
				success : function(data) {
					$(".page-loader").fadeOut();
					$('#changePassId')[0].reset();
					$("#modal-small").modal('hide');
					not("Contraseña se ha actualizado con éxito!");
					
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("errores pass");
					
				}
			});
			}else{
				$('#btn_pass').attr('disabled',true);
		    	$('#btn_pass').addClass('btn-link');
		    	$('#btn_pass').removeClass('btn-outline-primary');
			}
		}else{
			$('#btn_pass').attr('disabled',true);
	    	$('#btn_pass').addClass('btn-link');
	    	$('#btn_pass').removeClass('btn-outline-primary');
		}
	});
	
	$("#btn_cancel").click(function(){
		$('#changePassId')[0].reset();
	});
	
	$("#checkpass").click(function(){
		$(this).is(':checked') ? $('#passold').attr('type', 'text') : $('#passold').attr('type', 'password');
		$(this).is(':checked') ? $('#passnew').attr('type', 'text') : $('#passnew').attr('type', 'password');
		$(this).is(':checked') ? $('#passnew2').attr('type', 'text') : $('#passnew2').attr('type', 'password');
	});

    /*--------------------------------------
     Bootstrap Notify Notifications
     ---------------------------------------*/

    function not(res) {
        var from = 'bottom';
        var align = 'center';
        var icon = "";
        var type = 'inverse';
        var animIn = 'animated fadeInUp';
        var animOut = 'animated fadeOutDown';
        $.notify({
            icon: icon,
            title: 'Tu!',
            message: res,
            url: ''
        },{
            element: 'body',
            type: type,
            allow_dismiss: true,
            placement: {
                from: from,
                align: align
            },
            offset: {
                x: 15, // Keep this as default
                y: 15  // Unless there'll be alignment issues as this value is targeted in CSS
            },
        	onShow: tst(),
            spacing: 10,
            z_index: 1031,
            delay: 2500,
            timer: 1000,
            url_target: '_blank',
            mouse_over: false,
            animate: {
                enter: animIn,
                exit: animOut
            },
            template:   '<div data-notify="container" class="alert alert-dismissible alert-{0} alert--notify" role="alert">' +
            '<span data-notify="icon"></span> ' +
            '<span data-notify="title">{1}</span> ' +
            '<span data-notify="message">{2}</span>' +
            '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
            '</div>' +
            '<a href="{3}" target="{4}" data-notify="url"></a>' +

            '</div>'
        });

    }
    
//    Evitar Actualizar
 
    function disableF5(e) { if ((e.which || e.keyCode) == 116 || (e.which || e.keyCode) == 82) e.preventDefault(); };
    
    function disableIE() {
        if (document.all) {
            return false;
        }
    }
    function disableNS(e) {
        if (document.layers || (document.getElementById && !document.all)) {
            if (e.which==2 || e.which==3) {
                return false;
            }
        }
    }
    
    
    
    function tst(){
    	$.ajax({
			type : "POST",
			url : "/logout",
			success : function(data) {
				if (document.layers) {
		            document.captureEvents(Event.MOUSEDOWN);
		            document.onmousedown = disableNS;
		        }
		        else {
		            document.onmouseup = disableNS;
		            document.oncontextmenu = disableIE;
		        }
		    	window.location.hash="no-back-button";
		    	window.location.hash="Again-No-back-button";//esta linea es necesaria para chrome
		    	window.onhashchange=function(){window.location.hash="no-back-button";}
		        document.oncontextmenu=new Function("return false");
//		        $("body").attr("onload","deshasbilitar();")
		    	$(document).bind("keydown", disableF5);
		    	/* OR jQuery >= 1.7 */
		    	$(document).on("keydown", disableF5);
		    	window.setTimeout("$('#exitPass').modal('show');",2000);
//		    	 setTimeout(function() {
//		           window.location.href = '/logout';
//		       }, 4000);
			}
		});
    	
    }
    

});