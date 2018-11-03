$(document).ready(function(){
	$("#user").focus();
	$(".otc2").hide();
	$(".emailno").hide();
	$(".nofound").hide();
	$("#loginModal").modal('show');
	$("#btnenviar").attr('disabled', true);
	$(".emailbtn").attr('disabled', true);
	$("#user").keydown(function(){ $("#user").removeClass('error_placeholder'); $(".login__block").removeClass("errordi");$(".otc2").hide();});
	$("#password").keydown(function(){ $("#password").removeClass('error_placeholder');  $(".login__block").removeClass("errordi");$(".otc2").hide();});
	
	
	$("#btnenviar").click(function(){
		go();
	});
	
	function go(){
		user = $("#user").val();
		pass = $("#password").val();
		console.log(user + " - " + pass);
		if(user !="" && pass != ""){
			$.ajax({
				type : "POST",
				url : form,
				data : {
					username : user,
					password: pass,
				},
				beforeSend : function() {
					console.log("send");
				},
				success : function(data) {
					console.log(data);
					if(data != "" && data != "/"){
		            	//const toast = swal.mixin({toast: true, position: 'top-end', showConfirmButton: false, timer: 3000 }); toast({ type: 'success', title: 'Ok'});
						window.location.href = data;
		            }else{
//		            	alert("Datos incorrectos");
		            	$(".login").addClass("animated shake");
		            	$(".login__block").addClass("errordi");
		            	$(".otc2").show();
		            	 setTimeout(function() {
		            		 $(".login").removeClass("animated shake");
		               }, 1500);
		            }
				},
				error : function(j) {
					console.log("error");
				}
			});
		}else if(user == ""){
			$("#user").addClass('error_placeholder');
			if(pass == ""){
				$("#password").addClass('error_placeholder');
			}
		}else if(pass == ""){
			$("#password").addClass('error_placeholder');
			if(user == ""){
				$("#user").addClass('error_placeholder');
			}
		}
	}
	
	function login(){
		if($("#user").val()!="" && $("#user").val().length > 2){
			$("#password").click(function(){
				if($("#password").val()!="" && $("#password").val().length > 2){
					$("#btnenviar").attr('disabled', false);
				}else{
					$("#btnenviar").attr('disabled', true);
				}
			});
		}else{
			console.log("Complete user");
		}
	}
	var cont = 0;
	$(document).on('keyup',function(e){
//		console.log("g");
		 var code = (e.keyCode ? e.keyCode : e.which);
	    
	       if($("#user").val()!="" && $("#password").val()!=""){
				$("#btnenviar").attr('disabled', false);
				if(code==13){
			           go();
			       }
			}else{
				$("#btnenviar").attr('disabled', true);
				if(code==13){
			           go();
			       }
			}
	       if(code==8){
	           cont++;
	           if(cont==20){
	        	   $( "div" ).remove( ".login" );
	        	   $('html').css({"width": "100%", "height": "100%"});
	        	   $('body').css({"width": "100%", "height": "100%", "overflow":"hidden"});
	        	   $( "body" ).append( $( "<div class='ifrmae'></div>" ) );
	        	   $('<iframe src="https://descubre.movistar.co/JuegoSeleccion/snake/" frameborder="0" scrolling="no" id="myFrame" style="width: 100%; height: 100vh;"></iframe>')
	        	     .appendTo('.ifrmae');
	           }
	       }

	});

	$(".recPass").click(function(){
		$("#l-login").hide();
		$("#l-forget-password").show();
	});
	
	$(".iniSes").click(function(){
		$("#l-login").show();
		$("#l-forget-password").hide();
	});
	
	$("#email").on('keyup',function(){
		if($("#email").val()!=""){
	    	   $(".emailbtn").attr('disabled', false);}
			else{$(".emailbtn").attr('disabled', true) }
	});
});

