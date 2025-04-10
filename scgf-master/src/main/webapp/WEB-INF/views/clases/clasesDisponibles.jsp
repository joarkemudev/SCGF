<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">
<fmt:setLocale value="en_US" scope="session"/>

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.clases" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon" data-background-color="blue">
						<i class="material-icons">fitness_center</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.clases" /></h4>
						
						<div class="toolbar">
							
						</div>
						
						<hr>
						
						<!-- Filtros de búsqueda -->
						<div class="row">
							<div class="col-md-4">
								<div class="form-group label-floating">
									<label class="control-label">Buscar por nombre</label>
									<input type="text" class="form-control" id="searchNombre">
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group label-floating">
									<label class="control-label">Filtrar por instructor</label>
									<select class="form-control" id="filterInstructor">
<!-- 										<option value="">Todos los instructores</option> -->
										<c:forEach var="usuario" items="${usuarios}">
											<option value="${usuario.id}">${usuario.nombre}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						
						<!-- Contenedor de las cards -->
						<div class="row" id="clases-container">
							<c:forEach var="cl" items="${clases}">
								<div class="col-md-4 clase-card" 
									data-nombre="${cl.nombre.toLowerCase()}" 
									data-instructor="${cl.usuario.id}">
									
									<div class="card">
										<div class="card-header" data-background-color="blue">
											<h4 class="card-title">
												<sec:authorize access="hasAnyRole('EDITA_CLASES')">
													<a href="edit?id=${cl.id}" style="color: white;">${cl.nombre}</a>
												</sec:authorize>
												<sec:authorize access="!hasAnyRole('EDITA_CLASES')">
													${cl.nombre}
												</sec:authorize>
											</h4>
											 <p class="category">Instructor: ${cl.usuario.nombre}</p>
										</div>
										<div class="card-content">
											<div class="row">
												<div class="col-md-12">
													<p><strong>Descripción:</strong> ${cl.descripcion}</p>
													<p><strong>Horario:</strong> 
														<fmt:formatDate value="${cl.fechaHoraInicio}" pattern="dd/MM/yyyy hh:mm a" /> - 
														<fmt:formatDate value="${cl.fechaHoraFin}" pattern="hh:mm a" />
													</p>
													<p><strong>Capacidad:</strong> ${cl.capacidadMaxima} socios</p>
												</div>
											</div>
											
											<!-- Botón de Registrarse -->
										<!-- Dentro de cada card de clase, modifica la sección del botón así: -->
<!-- 										<div class="text-center" style="margin-top: 15px;"> -->
<%-- 										    <c:choose> --%>
<%-- 										        <c:when test="${cl.inscripciones.size() lt cl.capacidadMaxima}"> --%>
<%-- 										            <form:form action="${pageContext.request.contextPath}/clases/inscribirSocio" method="post" modelAttribute="inscritosClase"> --%>
<%-- 										                <input type="hidden" name="empresa.id" value="${emp.id}" /> --%>

<%-- 										                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
										                
										               
<%-- 														<button onclick="enviarInscripcion(${cl.id})" class="btn btn-success"> --%>
<!-- 														    <i class="material-icons">how_to_reg</i> Registrarse 1 -->
<!-- 														</button> -->
										               
<%-- 										            </form:form> --%>
<%-- 										        </c:when> --%>
<%-- 										        <c:otherwise> --%>
<!-- 										            <button class="btn btn-danger btn-round" disabled> -->
<!-- 										                <i class="material-icons">block</i> Cupo lleno -->
<!-- 										            </button> -->
<%-- 										        </c:otherwise> --%>
<%-- 										    </c:choose> --%>
<!-- 										</div> -->
										<div class="text-center" style="margin-top: 15px;">
										    <c:choose>
										        <c:when test="${cl.inscripciones.size() lt cl.capacidadMaxima}">
										            <form action="inscribirSocio" method="post" modelAttribute="inscritosClase">
										            	<input type="hidden" name="empresa.id" value="${cl.empresa.id}" />	
										                <input type="hidden" name="clase.id" value="${cl.id}">
										                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
										                
										                <!-- Solo un botón de submit -->
										                <button type="submit" class="btn btn-success btn-round">
										                    <i class="material-icons">how_to_reg</i> Registrarse
										                </button>
										            </form>
										        </c:when>
										        <c:otherwise>
										            <button class="btn btn-danger btn-round" disabled>
										                <i class="material-icons">block</i> Cupo lleno
										            </button>
										        </c:otherwise>
										    </c:choose>
										</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						
						<!-- Mensaje cuando no hay resultados -->
						<div class="row" id="no-results" style="display: none;">
							<div class="col-md-12">
								<div class="alert alert-info">
									<h4>No se encontraron clases</h4>
									<p>No hay clases que coincidan con los criterios de búsqueda.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>

	<tiles:putAttribute name="ready"> 
	$('#clases_op').addClass("active ");
	$('#clases').addClass("collapse in");
	
	// Filtros de búsqueda
	$('#searchNombre, #filterInstructor').on('keyup change', function() {
		var searchText = $('#searchNombre').val().toLowerCase();
		var instructorId = $('#filterInstructor').val();
		var hasResults = false;
		
		$('.clase-card').each(function() {
			var cardNombre = $(this).data('nombre');
			var cardInstructor = $(this).data('instructor');
			var matchesSearch = searchText === '' || cardNombre.includes(searchText);
			var matchesInstructor = instructorId === '' || cardInstructor == instructorId;
			
			if (matchesSearch && matchesInstructor) {
				$(this).show();
				hasResults = true;
			} else {
				$(this).hide();
			}
		});
		
		if (hasResults) {
			$('#no-results').hide();
		} else {
			$('#no-results').show();
		}
	});
   </tiles:putAttribute>
   
    <script>
    function enviarInscripcion(claseId) {
        console.log('Intentando inscribir en clase:', claseId);
        
        fetch('${pageContext.request.contextPath}/clases/inscribirSocio', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                '${_csrf.headerName}': '${_csrf.token}'
            },
            body: `claseId=${claseId}&${_csrf.parameterName}=${_csrf.token}`
        })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                response.text().then(text => {
                    alert("Error al inscribirse: " + text);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error de conexión al inscribirse");
        });
    }
    </script>

</tiles:insertDefinition>