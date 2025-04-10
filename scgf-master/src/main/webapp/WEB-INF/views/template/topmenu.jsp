<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-transparent navbar-absolute">
    <div class="container-fluid">
        <div class="navbar-minimize">
            <button id="minimizeSidebar" class="btn btn-round btn-white btn-fill btn-just-icon">
                <i class="material-icons visible-on-sidebar-regular">more_vert</i>
                <i class="material-icons visible-on-sidebar-mini">view_list</i>
            </button>
        </div>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">
                <p>${empresaNombre}</p>
            </a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="material-icons">notifications</i>
                        <span class="notification" id="numNotif">0</span>
                        <p class="hidden-lg hidden-md">Notificaciones <b class="caret"></b></p>
                    </a>
                    <ul class="dropdown-menu" id="notiMenu">
                        <li><a href="#">Cargando notificaciones...</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="material-icons">person</i>
                        <span class="hidden-xs hidden-s">
                            <sec:authentication property="principal.username"/>
                        </span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/usuarios/edit?id=${idUsuario}" />">Perfil</a></li>
<%--                         <li><a href="<c:url value="/usuarios/"/>">Cambiar Contraseña</a></li> --%>
                        <li><a href="<c:url value="/j_spring_security_logout"/>">Cerrar Sesión</a></li>
                    </ul>
                </li>
                <li>
				    <a href="#" data-toggle="modal" data-target="">
				        <i class="material-icons">settings</i>
				        <p class="hidden-lg hidden-md">Configuración</p>
				    </a>
				</li>
            </ul>
        </div>
    </div>
</nav>
<c:import url="/WEB-INF/views/include/modalConfiguracion.jsp" />

<script src="<c:url value='/assets/js/scgf/modalConfiguracion.js'/>"></script>
