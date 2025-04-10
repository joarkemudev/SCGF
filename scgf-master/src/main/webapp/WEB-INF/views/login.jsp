<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png" />
    <link rel="icon" type="image/png" href="assets/img/favicon.png" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>CONTROLGYM - SCGF</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    <!-- Bootstrap core CSS     -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <!--  Material Dashboard CSS    -->
    <link href="assets/css/material-dashboard.css" rel="stylesheet" />
    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="assets/css/demo.css" rel="stylesheet" />
    <!--     Fonts and icons     -->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" />
</head>

<body>
 
    <nav class="navbar navbar-primary navbar-transparent navbar-absolute">
        <div class="container">	
            <div class="navbar-header">
            	<img alt="logo" src='<c:url value="assets/img/logo_hb.png"/>'>
            </div>
            
             <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                    	<a href="#">
                            Sistema Control de Gastos
                        </a>
                    </li>
                 </ul>
             </div>
        </div>
    </nav>
     
    <div class="wrapper wrapper-full-page">
        <div class="full-page login-page" filter-color="black" data-image="assets/img/login3.png">
            <!--   you can change the color of the filter page using: data-color="blue | purple | green | orange | red | rose " -->
            <div class="content">
                <div class="container">               
                     
                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">                               
                                <div class="card card-login card-hidden">                              
                                    <div class="card-header text-center" data-background-color="blue">                                   
                                        <h4 class="card-title">Ingreso al Sistema</h4>              
                                    </div>
                                    <p class="category text-center">
                                        S.C.G.F.
                                    </p>                                
                                    <form class="login-form" action="<c:url value='j_spring_security_check' />" method="post">
                                    <div class="card-content">
                                                                         
                                       <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="material-icons">face</i>
                                            </span>
                                            <div class="form-group label-floating">
                                                <label class="control-label">Usuario</label>
                                                <input class="form-control" type="text" autocomplete="off"  name="j_username" autofocus required/>
                                            </div>
                                        </div>                                    
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="material-icons">lock_outline</i>
                                            </span>
                                            <div class="form-group label-floating">
                                                <label class="control-label">Password</label>
                                                <input class="form-control" type="password" autocomplete="off"  name="j_password" required/>
                                            </div>
                                        </div>											
                                    </div>
                                    <div class="footer text-center">
                                        <button type="submit" class="btn btn-rose btn-simple btn-wd btn-lg">Ingresar</button>
                                    </div>

                                    </form>
                                    
                                </div>
                        </div>
                    </div>
                </div>
            </div>
                  
            <footer class="footer">
                <div class="container">
                    <p class="copyright pull-right">
                        &copy; <script>document.write(new Date().getFullYear())</script>
                        <a href="http://thonaseguros.mx/">CONTROLGYMFIT.</a>
                    </p>
                </div>
            </footer>           
                
        </div>
    </div>
</body>

<!--   Core JS Files   -->
<script src="assets/js/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="assets/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/material.min.js" type="text/javascript"></script>
<script src="assets/js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>
<!-- Forms Validations Plugin -->
<script src="assets/js/jquery.validate.min.js"></script>
<!--  Plugin for Date Time Picker and Full Calendar Plugin-->
<script src="assets/js/moment.min.js"></script>
<!--  Charts Plugin -->
<script src="assets/js/chartist.min.js"></script>
<!--  Plugin for the Wizard -->
<script src="assets/js/jquery.bootstrap-wizard.js"></script>
<!--  Notifications Plugin    -->
<script src="assets/js/bootstrap-notify.js"></script>
<!-- DateTimePicker Plugin -->
<script src="assets/js/bootstrap-datetimepicker.js"></script>
<!-- Vector Map plugin -->
<script src="assets/js/jquery-jvectormap.js"></script>
<!-- Sliders Plugin -->
<script src="assets/js/nouislider.min.js"></script>

<!-- Select Plugin -->
<script src="assets/js/jquery.select-bootstrap.js"></script>
<!--  DataTables.net Plugin    -->
<script src="assets/js/jquery.datatables.js"></script>
<!-- Sweet Alert 2 plugin -->
<script src="assets/js/sweetalert2.js"></script>
<!--	Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
<script src="assets/js/jasny-bootstrap.min.js"></script>
<!--  Full Calendar Plugin    -->
<script src="assets/js/fullcalendar.min.js"></script>
<!-- TagsInput Plugin -->
<script src="assets/js/jquery.tagsinput.js"></script>
<!-- Material Dashboard javascript methods -->
<script src="assets/js/material-dashboard.js"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="assets/js/demo.js"></script>
<script type="text/javascript">
    $().ready(function() {
        demo.checkFullPageBackgroundImage();

        setTimeout(function() {
            // after 1000 ms we add the class animated to the login/register card
            $('.card').removeClass('card-hidden');
        }, 700)
    });
    
    
  //Script para mostrar notificaciones:
    var mensaje ="${mensaje.mensaje}<br> <c:out value='${SPRING_SECURITY_LAST_EXCEPTION.message}'/>";
    var tipo ="${mensaje.tipo}";
    if(tipo.length > 0){
    var icon="";
    var type="";


    if(tipo == "SUCCESS"){
        type="success";
        icon="add_alert";
    }else if(tipo == "DANGER"){
        type="danger";
        icon="warning";
    }else{
      type="INFO";
      icon="info";
    }


    $.notify({
      icon: icon,
      message: mensaje
    },{
        type: type,
        timer: 4000,
        placement: {
            from: "top",
            align: "right"
        }
    });
    }
    
</script>

</html>
