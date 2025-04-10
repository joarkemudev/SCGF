<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!doctype html>
<html lang="es-MX">

<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="<c:url value='/assets/img/apple-icon.png'/>" />
<link rel="icon" type="image/png"
	href="<c:url value='/assets/img/favicon.png'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>CONTROLGYM - SCGF</title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />
<meta name="viewport" content="width=device-width" />
<!-- Bootstrap core CSS     -->
<link href="<c:url value='/assets/css/bootstrap.min.css'/>"
	rel="stylesheet" />
<!--  Material Dashboard CSS    -->
<link href="<c:url value='/assets/css/material-dashboard.css'/>"
	rel="stylesheet" />
<!--  CSS for Demo Purpose, don't include it in your project     -->
<link href="<c:url value='/assets/css/demo.css'/>" rel="stylesheet" />
<!--     Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" />

	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
	

<script type="text/javascript">
	
	var tiempoSesion = <%= (session.getMaxInactiveInterval()-600) * 1000%>;  //600 = 10 min : 30-10 = 20
	var tiempoFinSesion = <%= session.getMaxInactiveInterval() * 1000%>; //30 min
	//alert('Tiempo de session: ' + tiempoSesion);
    window.setTimeout('checkIfContinue()', tiempoSesion);  //10 minutes
    window.setTimeout('endSession()', tiempoFinSesion);  //0 minutes

    function endSession(){
    	//alert('Tiempo de session final: ' +tiempoFinSesion);
    	window.location.href = '<c:url value="/j_spring_security_logout"></c:url>';	
    }
    function pretty_time_string(num) {
        return ( num < 10 ? "0" : "" ) + num;
      }
    	
    function checkIfContinue()
    {
    	//alert('Tiempo de session: ' + tiempoSesion);
    	var total_seconds =  (tiempoSesion / 1000) - 600; //600
    	
    	setInterval(function() {
		  total_seconds--; 
		 
		
		  var minutes = Math.floor(total_seconds / 60);
		  var total_seconds2 = total_seconds % 60;
		
		  var seconds = Math.floor(total_seconds2);
	
		  minutes = pretty_time_string(minutes);
		  seconds = pretty_time_string(seconds);
		
		  var currentTimeString = minutes + ":" + seconds;
		
		  $('.timer').text(currentTimeString);
		}, 1000);
    	
    	swal({
    		title : 'La sesión será finalizada por inactividad, al menos que continue trabajando en el sistema',
    		type : 'warning',
    		html: 'Tiempo restante: <span class="timer"></span><br>¿Deseas continuar trabajando?',
    		showCancelButton : true,
    		confirmButtonText : 'Sí',
    		cancelButtonText : 'No',
    		confirmButtonClass : "btn btn-success",
    		cancelButtonClass : "btn btn-danger",
    		buttonsStyling : false
    	}).then(function() {
    		<% session.setMaxInactiveInterval(1800); %> //1800

        	//alert('Tiempo de sesión reasignado');
        	
		       window.setTimeout('checkIfContinue()',tiempoSesion);  //start the timer again   		
    		
    	}, function(dismiss) {
    		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
    		if (dismiss === 'cancel') {
    			  window.location.href = '<c:url value="/j_spring_security_logout"></c:url>';	
    		}
    	});    
    }

</script>

</head>

<body>
	<div class="wrapper">

		<c:import url="/WEB-INF/views/template/menu.jsp"></c:import>

		<div class="main-panel">

			<c:import url="/WEB-INF/views/template/topmenu.jsp"></c:import>
			<div class="content">
				<div class="container-fluid">

					<tiles:insertAttribute name="body" />

				</div>
			</div>

			<c:import url="/WEB-INF/views/template/footer.jsp"></c:import>

		</div>
	</div>
</body>
<!--   Core JS Files   -->
<script src="<c:url value='/assets/js/jquery-3.1.1.min.js'/>"
	type="text/javascript"></script>
	
	
<script src="<c:url value='/assets/js/jquery-ui.min.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/bootstrap.min.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/material.min.js'/>"
	type="text/javascript"></script>
<script
	src="<c:url value='/assets/js/perfect-scrollbar.jquery.min.js'/>"
	type="text/javascript"></script>
<!-- Forms Validations Plugin -->
<script src="<c:url value='/assets/js/jquery.validate.min.js'/>"></script>
<!--  Plugin for Date Time Picker and Full Calendar Plugin-->
<script src="<c:url value='/assets/js/moment.min.js'/>"></script>
<!--  Charts Plugin -->
<script src="<c:url value='/assets/js/chartist.min.js'/>"></script>
<!--  Plugin for the Wizard -->
<script src="<c:url value='/assets/js/jquery.bootstrap-wizard.js'/>"></script>
<!--  Notifications Plugin    -->
<script src="<c:url value='/assets/js/bootstrap-notify.js'/>"></script>
<!-- DateTimePicker Plugin -->
<script src="<c:url value='/assets/js/bootstrap-datetimepicker.js'/>"></script>
<!-- Vector Map plugin -->
<script src="<c:url value='/assets/js/jquery-jvectormap.js'/>"></script>
<!-- Sliders Plugin -->
<script src="<c:url value='/assets/js/nouislider.min.js'/>"></script>
<!-- Select Plugin -->
<script src="<c:url value='/assets/js/jquery.select-bootstrap.js'/>"></script>
<!--  DataTables.net Plugin    -->
<script src="<c:url value='/assets/js/jquery.datatables.js'/>"></script>
<!-- Sweet Alert 2 plugin -->
<script src="<c:url value='/assets/js/sweetalert2.js'/>"></script>
<script src="<c:url value='/assets/js/core.js'/>"></script>
<!--	Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
<script src="<c:url value='/assets/js/jasny-bootstrap.min.js'/>"></script>
<!--  Full Calendar Plugin    -->
<script src="<c:url value='/assets/js/fullcalendar.min.js'/>"></script>
<!-- TagsInput Plugin -->
<script src="<c:url value='/assets/js/jquery.tagsinput.js'/>"></script>
<!-- Material Dashboard javascript methods -->
<script src="<c:url value='/assets/js/material-dashboard.js'/>"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="<c:url value='/assets/js/demo.js'/>"></script>
<!-- Utilidades -->
<script src="<c:url value='/assets/js/scgf/util.js'/>"></script>
<script src="<c:url value='/assets/js/es.js'/>"></script>
<script src="<c:url value='/assets/js/messages_es.js'/>"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>

<tiles:insertAttribute name="scripts" />

<script>

	jQuery(document).ready(function() {
		 $('[data-toggle="tooltip"]').tooltip(); 
		 
		 $('.datepicker').datetimepicker({
	    		  format: 'DD/MM/Y',
	        	  locale: 'es',
	  		    icons: {
	    		        time: "fa fa-clock-o",
	    		        date: "fa fa-calendar",
	    		        up: "fa fa-chevron-up",
	    		        down: "fa fa-chevron-down",
	    		        previous: 'fa fa-chevron-left',
	    		        next: 'fa fa-chevron-right',
	    		        today: 'fa fa-screenshot',
	    		        clear: 'fa fa-trash',
	    		        close: 'fa fa-remove'   		        
	    		    }
	    	 
	    		});
	
		<tiles:insertAttribute name="ready" />
	});

	<tiles:insertAttribute name="functions" />

	//Script para mostrar notificaciones:
	var mensaje = "${mensaje.mensaje}";
	var tipo = "${mensaje.tipo}";
	if (mensaje.length > 0) {
		var icon = "";
		var type = "";

		if (tipo == "SUCCESS") {
			type = "success";
			icon = "add_alert";
		} else if (tipo == "DANGER") {
			type = "danger";
			icon = "warning";
		} else {
			type = "INFO";
			icon = "info";
		}

		$.notify({
			icon : icon,
			message : mensaje
		}, {
			type : type,
			timer : 4000,
			placement : {
				from : "top",
				align : "right"
			}
		});
	}
</script>

</html>
