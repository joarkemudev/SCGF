<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="sidebar" data-active-color="blue"
	data-background-color="black"
	data-image="<c:url value='/assets/img/sidebar-3.png'/>">
	<!--
        Tip 1: You can change the color of active element of the sidebar using: data-active-color="purple | blue | green | orange | red | rose"
        Tip 2: you can also add an image using data-image tag
        Tip 3: you can change the color of the sidebar with data-background-color="white | black"
    -->

	<div class="logo">
		<a href="#" target="_blank" class="simple-text logo-mini">
		    <div class="logo-img">
		        <c:choose>
		            <c:when test="${not empty empresaLogo}">
		                <img alt="logo" src="<c:url value='${empresaLogo}'/>">
		            </c:when>
		            <c:otherwise>
		                <img alt="logo" src="<c:url value='/assets/img/logo_hbm.png'/>">
		            </c:otherwise>
		        </c:choose>
		    </div>
		</a>

		<hr>
		<span class="simple-text"> Control  Gastos Gym Fit</span>
	</div>
	
	<div class="logo logo-mini">
		<a href="#" class="simple-text"> SCGF </a>
	</div>
	
	<div class="sidebar-wrapper">

		<ul class="nav">
			<li id="principal_op">
				<a href="<c:url value='/principal'/>">
					<i class="material-icons">dashboard</i>
					<p>Principal</p>
				</a>
			</li>
			<sec:authorize access="hasAnyRole('CONSULTA_ROLES')">			
			<li id="roles_op">
				<a href="<c:url value="/roles/"/>">
					<i class="material-icons">account_circle</i>
					<p>Roles</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_PLANES')">			
			<li id="planes_op">
				<a href="<c:url value="/planes/"/>">
					<i class="material-icons">apps</i>
					<p>Planes</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_CLIENTES')">			
			<li id="clientes_op">
				<a href="<c:url value="/clientes/"/>">
					<i class="material-icons">people_outline</i>
					<p>Socios</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_CLASES')">			
			<li id="clases_op">
				<a href="<c:url value="/clases/"/>">
					<i class="material-icons">fitness_center</i>
					<p>Clases</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_MEMBRESIAS')">			
			<li id="membresias_op">
				<a href="<c:url value="/membresias/"/>">
					<i class="material-icons">storage</i>
					<p>Membresías</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_PAGOS')">			
			<li id="pagos_op">
				<a href="<c:url value="/pagos/"/>">
					<i class="material-icons">monetization_on</i>
					<p>Caja</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_CORREOS')">			
			<li id="correos_op">
				<a href="<c:url value="/correos/"/>">
					<i class="material-icons">mail_outline</i>
					<p>Correos</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_USUARIOS')">			
			<li id="usuarios_op">
				<a href="<c:url value="/usuarios/"/>">
					<i class="material-icons">account_box</i>
					<p>Usuarios</p>
				</a>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('CONSULTA_EMPRESAS')">			
			<li id="empresas_op">
				<a href="<c:url value="/empresas/"/>">
					<i class="material-icons">apartment</i>
					<p>Empresas</p>
				</a>
			</li>
			</sec:authorize>
			<li id="sesion_op">	
				<a href="<c:url value="/j_spring_security_logout"></c:url>">
					<i class="material-icons">exit_to_app</i>
					<p>Cerrar sesi&oacute;n </p> 
				</a>
			</li>
			
		</ul>
		
		<div class="logo">
		<hr>
		<a href="#" target="_blank" class="simple-text logo-mini">
		    <div class="logo-img">    
				<img alt="logo" src="<c:url value='/assets/img/favicon1.png'/>">		        
		    </div>
		</a>
		<hr>
		<span class="simple-text">CGF</span>
		</div>
	</div>
</div>