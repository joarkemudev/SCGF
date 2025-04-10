<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="styles">
		<link
			href="<c:url value='/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css'/>"
			rel="stylesheet" type="text/css" />
		<link
			href="<c:url value='/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css'/>"
			rel="stylesheet" type="text/css" />
		<link
			href="<c:url value='/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css'/>"
			rel="stylesheet" type="text/css" />
	</tiles:putAttribute>
	<tiles:putAttribute name="title">Principal</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<%@include file="secciones/messages.jsp"%>

		<div class="row">
		<!-- Imagen con Parallax y Overlay -->
        <div class="v-parallax__content">
            <div class="v-overlay bg-gradient-primary opacity-6 v-overlay--absolute v-overlay--active theme--dark" style="z-index: 5;">
                <div class="v-overlay__scrim" style="opacity: 0.46; background-color: rgb(33, 33, 33); border-color: rgb(33, 33, 33);"></div>
                <div class="v-overlay__content"></div>
            </div>
            <img src="${pageContext.request.contextPath}/assets/img/hero-sb.jpg" alt="Imagen de fondo" class="v-parallax__image" style="width: 100%; height: auto;">
        </div>
		<sec:authorize access="hasAnyRole('CONSULTA_PLANES')">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<a style="display:block" href="<c:url value='/planes/'/>">
				<div class="card card-stats">
					<div class="card-header" data-background-color="blue">
						<i class="material-icons">apps</i>
					</div>
					<div class="card-content">
						<h3 class="card-title">PLANES</h3>
					</div>
					<div class="card-footer">
						<div class="stats">
						Pantalla de búsqueda de Planes
						</div>
					</div>
				</div>
				</a>
			</div>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('CONSULTA_CLIENTES')">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<a style="display:block" href="<c:url value='/clientes/'/>">
				<div class="card card-stats">
					<div class="card-header" data-background-color="blue">
						<i class="material-icons">people_outline</i>
					</div>
					<div class="card-content">
						<h3 class="card-title">SOCIOS</h3>
					</div>
					<div class="card-footer">
						<div class="stats">
						Pantalla de búsqueda de Socios
						</div>
					</div>
				</div>
				</a>
			</div>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('CONSULTA_MEMBRESIAS')">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<a style="display:block" href="<c:url value='/membresias/'/>">
				<div class="card card-stats">
					<div class="card-header" data-background-color="blue">
						<i class="material-icons">storage</i>
					</div>
					<div class="card-content">
						<h3 class="card-title">MEMBRESÍAS</h3>
					</div>
					<div class="card-footer">
						<div class="stats">
						Pantalla de búsqueda de Membresías
						</div>
					</div>
				</div>
				</a>
			</div>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_SOCIO')">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<a style="display:block" href="<c:url value='/clases/disponibles'/>">
				<div class="card card-stats">
					<div class="card-header" data-background-color="blue">
						<i class="material-icons">class</i>
					</div>
					<div class="card-content">
						<h3 class="card-title">CLASES</h3>
					</div>
					<div class="card-footer">
						<div class="stats">
						Pantalla de búsqueda de Clases
						</div>
					</div>
				</div>
				</a>
			</div>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_SOCIO')">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<a style="display:block" href="<c:url value='/clases/disponibles'/>">
				<div class="card card-stats">
					<div class="card-header" data-background-color="blue">
						<i class="material-icons">fitness_center</i>
					</div>
					<div class="card-content">
						<h3 class="card-title">RUTINAS</h3>
					</div>
					<div class="card-footer">
						<div class="stats">
						Pantalla de búsqueda de Rutinas
						</div>
					</div>
				</div>
				</a>
			</div>
		</sec:authorize>
		</div>
		
	</tiles:putAttribute>
	
	<tiles:putAttribute name="ready"> 
	$('#principal_op').addClass("active "); 
    
   </tiles:putAttribute>

</tiles:insertDefinition>