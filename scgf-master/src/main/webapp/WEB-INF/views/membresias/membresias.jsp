<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">
<fmt:setLocale value="en_US" scope="session"/>

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.membresias" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">storage</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.membresias" /></h4>
						
						<div class="toolbar">
							<sec:authorize access="hasAnyRole('ALTA_MEMBRESIAS')">
							<a href='<c:url value="add"/>' class="btn btn-success" data-toggle="tooltip" 
								title="Crea una nueva Membresía"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a> 
							</sec:authorize>
						</div>
						
						<hr>
						<div class="toolbar">

							<label class="control-label"></label>
	
						</div>
						<table id="tableMembresias" class="table table-striped table-no-bordered table-hover ">
							<thead>
								<tr>
									<th>Id</th>
									<th>Nombre Socio</th>
									<th>Plan</th>
									<th><fmt:message key="catalogos.planes.duracion" /></th>
									<th>Fecha Inicio</th>
									<th>Fecha Fin</th>
									<th><fmt:message key="catalogos.usuarios.estado" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_MEMBRESIAS')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="memb" items="${membresias}">
									<tr>
										<td>${memb.id}</td>
										<td>
										    <sec:authorize access="hasAnyRole('EDITA_MEMBRESIAS') and ${memb.estado == 'INACTIVO'}">
										        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
										        <a href="edit?id=${memb.id}">${memb.cliente.nombre} ${memb.cliente.primerApellido}</a>
										    </sec:authorize>
										    <sec:authorize access="!(hasAnyRole('EDITA_MEMBRESIAS') and ${memb.estado == 'INACTIVO'})">
										        <!-- Si el usuario no tiene permiso, muestra solo el nombre como texto -->
										        ${memb.cliente.nombre} ${memb.cliente.primerApellido}
										    </sec:authorize>
										</td> 
	                                   	<td>${memb.plan.nombre}</td>
	                                   	<td>${memb.plan.duracion.nombre}</td>
  	                                   	<td><fmt:formatDate value="${memb.fechaInicio}" pattern="dd/MM/yyyy" /></td>
  	                                   	<td><fmt:formatDate value="${memb.fechaFin}" pattern="dd/MM/yyyy" /></td>
 	                                   	<td>
 	                                   	<sec:authorize access="hasAnyRole('CONSULTA_MEMBRESIAS') and ${memb.estado == 'INACTIVO'}">
										    <!-- Si el usuario tiene permiso y el estado es INACTIVO, muestra el nombre como un enlace para editar -->
										    <a href="editarMembresia?id=${memb.id}">${memb.estado}</a>
										</sec:authorize>
										
										<sec:authorize access="hasAnyRole('CONSULTA_MEMBRESIAS') and ${memb.estado == 'ACTIVO'}">
										    <!-- Si el usuario tiene permiso pero el estado es ACTIVO, muestra el estado sin enlace (solo texto) -->
										    <span>${memb.estado}</span>
										</sec:authorize>
										    
 	                                   	</td>	                          
 	                                   	<sec:authorize access="hasAnyRole('ELIMINA_MEMBRESIAS')">
 	                                   	<td>
 										<button type="button" onclick="eliminar('delete?id=${memb.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
 										</td>
 										</sec:authorize>
									</tr>
								</c:forEach>
							</tbody>

						</table>

					</div>
				</div>
			</div>

		</div>
	</tiles:putAttribute>

	<tiles:putAttribute name="ready"> 
	$('#membresias_op').addClass("active ");
	$('#membresias').addClass("collapse in");
    
    $('#tableMembresias').DataTable({
        responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });

   </tiles:putAttribute>

</tiles:insertDefinition>