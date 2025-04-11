<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">
<fmt:setLocale value="en_US" scope="session"/>

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.clientes" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">people_outline</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.clientes" /></h4>
						
						<div class="toolbar">
							<sec:authorize access="hasAnyRole('ALTA_CLIENTES')">
							<a href='<c:url value="add"/>' class="btn btn-success" data-toggle="tooltip" 
								title="Crea un nuevo Socio"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a> 
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ALTA_CLIENTES')">
							<a href='<c:url value="cargaMasiva"/>' class="btn " data-toggle="tooltip" 
								title="Realiza la carga masiva a través de un archivo de excel de Socios"> <i
								class="fa fa-plus"> </i> <span class="hidden-480"> Carga
									Masiva </span>
							</a>
							</sec:authorize> 
							
						</div>
						
						<hr>
						<div class="toolbar">

							<label class="control-label"></label>
	
						</div>
						
						<table id="tableClientes" class="table table-striped table-no-bordered table-hover ">
							<thead>
								<tr>
									<th>Id</th>
									<th><fmt:message key="catalogos.clientes.nombre" /></th>
									<th><fmt:message key="catalogos.clientes.primerApellido" /></th>								
									<th><fmt:message key="catalogos.clientes.segundoApellido" /></th>
									<th><fmt:message key="catalogos.clientes.tipoIdentificacion" /></th>
 									<th><fmt:message key="catalogos.clientes.numIdentificacion" /></th> 
									<th><fmt:message key="catalogos.usuarios.correoElectronico" /></th>
									<th>Plan</th>
									<th><fmt:message key="catalogos.clientes.telefonoMovil" /></th>
									<th><fmt:message key="catalogos.clientes.genero" /></th>
									<th><fmt:message key="catalogos.usuarios.estado" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_CLIENTES')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="cl" items="${clientes}">
									<tr>
										<td>${cl.id}</td>
										<td>
										    <sec:authorize access="hasAnyRole('EDITA_CLIENTES')">
										        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
										        <a href="edit?id=${cl.id}">${cl.nombre}</a>
										    </sec:authorize>
										    <sec:authorize access="!hasAnyRole('EDITA_CLIENTES')">
										        <!-- Si el usuario no tiene permiso, muestra solo el nombre como texto -->
										        ${cl.nombre}
										        
										        
										    </sec:authorize>
										</td> 
	                                   	<td>${cl.primerApellido}</td>
	                                   	<td>${cl.segundoApellido}</td>	
	                                   	<td>${cl.tipoIdentificacion.nombre}</td>
 	                                   	<td>${cl.numIdentificacion}</td>
 	                                   	<td>${cl.correoElectronico}</td>
 	                                   	<td>${cl.plan.nombre}</td>
<%--  	                                   	<td><fmt:formatDate value="${cl.fechaNacimiento}" pattern="dd/MM/yyyy" /></td> --%>
 	                                   	<td>${cl.telefonoMovil}</td> 
 	                                   	<td>${cl.genero.nombre}</td> 
 	                                   	<td>${cl.estado}</td>	                          
 	                                   	<sec:authorize access="hasAnyRole('ELIMINA_CLIENTES')">
 	                                   	<td>
 										<button type="button" onclick="eliminar('delete?id=${cl.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
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
	$('#clientes_op').addClass("active ");
	$('#clientes').addClass("collapse in");

	$('#tableClientes').DataTable({
        responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });

   </tiles:putAttribute>

</tiles:insertDefinition>