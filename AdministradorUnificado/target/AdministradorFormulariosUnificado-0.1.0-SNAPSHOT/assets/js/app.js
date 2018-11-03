$(document).ready(function(){
caches.delete('app.js');
  var bodyid = document.getElementById("body");
  var color = bodyid.getAttribute("data-ma-theme");
  var colors = ['green','blue','red','orange','teal','cyan','blue-grey','purple','indigo','brown','pink','deep-orange','deep-purple','light-blue','light-green'];
  colors.forEach(function(element) {
      if (color == element) {
  		$("#"+color).addClass('active');
  }
  });
            /*--------------------------------------
                Bootstrap Notify Notifications
            ---------------------------------------*/

            function notify(message,icon,type){
                $.notify({
                    icon: icon,
                    title:  '¡El tema ',
                    message: message,
                    url: ''
                },{
                    element: 'body',
                    type: type,
                    allow_dismiss: true,
                    placement: {
                        from: 'bottom',
                        align: 'left'
                    },
                    offset: {
                        x: 20,
                        y: 20
                    },
                    spacing: 10,
                    z_index: 1031,
                    delay: 2500,
                    timer: 500,
                    url_target: '',
                    mouse_over: false,
                    animate: {
                        enter: 'animated fadeIn',
                        exit: 'animated fadeOut'
                    },
                    template:   '<div data-notify="container" class="alert alert-dismissible alert-{0} alert--notify" role="alert" style="max-width: 400px; border-radius: 10px;">' +
                                    '<span data-notify="zmdi zmdi-badge-check"></span> ' +
                                    '<span data-notify="title">{1}</span> ' +
                                    '<span data-notify="message">{2}</span>' +
                                    '<div class="progress" data-notify="progressbar">' +
                                        '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                                    '</div>' +
                                '</div>'
                });
            }

$('#btn_not').click(function(e){ var message = " ya esta establecido";
var type = 'inverse';
notify(message,color,type); });

$('body #colores').on('click', 'label', function(){
//  var idus = document.getElementById("idus").getAttribute("idus");
      var colorid = $(this).attr('id');

      var precolor = bodyid.getAttribute("data-ma-theme");
      if (colorid == precolor) {
        var message = colorid + " ya esta establecido";
        var type = 'inverse';
        notify(message,colorid,type);
      }else{
        //Codigo ajax guardar color
        var message = colorid + " se ha establecido con exito!";
        var type = 'info';
        notify(message,colorid,type);
      }
      });

      $(function(){
  var actualizarHora = function(){
    var fecha = new Date(),
        hora = fecha.getHours(),
        minutos = fecha.getMinutes(),
        segundos = fecha.getSeconds(),
        diaSemana = fecha.getDay(),
        dia = fecha.getDate(),
        mes = fecha.getMonth(),
        anio = fecha.getFullYear(),
        ampm;

    var $pHoras = $("#horas"),
        $pSegundos = $("#segundos"),
        $pMinutos = $("#minutos"),
        $pAMPM = $("#ampm"),
        $pDiaSemana = $("#diaSemana"),
        $pDia = $("#dia"),
        $pMes = $("#mes"),
        $pAnio = $("#anio");
    var semana = ['Domingo','Lunes','Martes','Miercoles','Jueves','Viernes','Sabado'];
    var meses = ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'];

    $pDiaSemana.text(semana[diaSemana]);
    $pDia.text(dia);
    $pMes.text(meses[mes]);
    $pAnio.text(anio);
    if(hora>=12){
      hora = hora - 12;
      ampm = "PM";
    }else{
      ampm = "AM";
    }
    if(hora == 0){
      hora = 12;
    }
    if(hora<10){$pHoras.text("0"+hora)}else{$pHoras.text(hora)};
    if(minutos<10){$pMinutos.text("0"+minutos)}else{$pMinutos.text(minutos)};
    if(segundos<10){$pSegundos.text("0"+segundos)}else{$pSegundos.text(segundos)};
    $pAMPM.text(ampm);

  };


  actualizarHora();
  var intervalo = setInterval(actualizarHora,1000);
});


});

(function(){

  var t = {
    playlist: [
  /*    {
        file: "img/resources/tracks/01.mp3",
        thumb: "img/resources/thumbs/01.jpg",
        trackName: "Explicale",
        trackArtist: "Yandel ft Bad Bunny",
        trackAlbum: "Update",
      },
      {
        file: "img/resources/tracks/02.mp3",
        thumb: "img/resources/thumbs/02.jpg",
        trackName: "Que nos paso",
        trackArtist: "Hebert Vargas",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/03.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/04.mp3",
        thumb: "img/resources/thumbs/01.jpg",
        trackName: "Explicale",
        trackArtist: "Yandel ft Bad Bunny",
        trackAlbum: "Update",
      },
      {
        file: "img/resources/tracks/05.mp3",
        thumb: "img/resources/thumbs/02.jpg",
        trackName: "Que nos paso",
        trackArtist: "Hebert Vargas",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/06.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/07.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/08.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/09.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/10.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/11.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/12.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/13.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/14.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/15.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/16.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/17.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/18.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/19.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/20.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/21.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/22.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/23.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/24.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/25.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/26.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/27.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/28.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/29.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/30.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },
      {
        file: "img/resources/tracks/31.mp3",
        thumb: "img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      },*/
      {
        file: "https://www.w3schools.com/html/horse.mp3",
        thumb: "http://localhost/bs4/img/resources/thumbs/03.jpg",
        trackName: "Tu no metes cabra",
        trackArtist: "Bad Bunny Remix",
        trackAlbum: "Single",
      }
    ]
  }

  $(".jAudio").jAudio(t);

})();

jQuery(function($) {

// MAD-RIPPLE // (jQ+CSS)
$(document).on("mousedown", "[data-ripple]", function(e) {

  var $self = $(this);

  if($self.is(".btn-disabled")) {
    return;
  }
  if($self.closest("[data-ripple]")) {
    e.stopPropagation();
  }

  var initPos = $self.css("position"),
      offs = $self.offset(),
      x = e.pageX - offs.left,
      y = e.pageY - offs.top,
      dia = Math.min(this.offsetHeight, this.offsetWidth, 100), // start diameter
      $ripple = $('<div/>', {class:"ripple", appendTo:$self});

  if(!initPos || initPos==="static") {
    $self.css({position:"relative"});
  }

  $('<div/>', {
    class : "rippleWave",
    css : {
      background: $self.data("ripple"),
      width: dia,
      height: dia,
      left: x - (dia/2),
      top: y - (dia/2),
    },
    appendTo : $ripple,
    one : {
      animationend : function(){
        $ripple.remove();
      }
    }
  });
});

});
