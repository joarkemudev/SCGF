<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">
<fmt:setLocale value="en_US" scope="session"/>

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.planes" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">apps</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.planes" /></h4>
						
						<div class="toolbar">
							<sec:authorize access="hasAnyRole('ALTA_PLANES')">
							<a href='<c:url value="add"/>' class="btn btn-success" data-toggle="tooltip" 
								title="Crea un nuevo Plan"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
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
									<th>Id Plan</th>
									<th><fmt:message key="catalogos.planes.nombre" /></th>
									<th><fmt:message key="catalogos.planes.descripcion" /></th>								
									<th><fmt:message key="catalogos.planes.duracion" /></th>
									<th>Cantidad</th>
									<th><fmt:message key="catalogos.planes.precio" /></th>
 									<th><fmt:message key="catalogos.planes.accesoClases" /></th> 
 									<th><fmt:message key="catalogos.planes.invitados" /></th> 
									<th><fmt:message key="catalogos.usuarios.estado" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_PLANES')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="pl" items="${planes}">
									<tr>
										<td>
											<sec:authorize access="hasAnyRole('EDITA_PLANES')">
										        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
										        <a href="edit?id=${pl.id}"><fmt:formatNumber
													pattern="00000" value="${pl.id}" /></a></td>
										    </sec:authorize>
										</td>
										<td>${pl.nombre}</td>
 	                                   	<td>${pl.descripcion}</td>
 	                                   	<td>${pl.duracion.nombre}</td>
 	                                   	<td>${pl.duracionCantidad}</td>
 	                                   	<td align="right"><fmt:formatNumber type="currency" value="${pl.precio}" /></td>
 	                                   	<td>${pl.accesoClases ? 'Sí' : 'No'}</td>
 	                                   	<td>${pl.invitadosPermitidos}</td>                                  
 	                                   	<td>${pl.estado.label}</td> 	                          
 	                                   	<sec:authorize access="hasAnyRole('ELIMINA_PLANES')">
 	                                   	<td>
 										<button type="button" onclick="eliminar('delete?id=${pl.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
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
	$('#planes_op').addClass("active ");
	$('#planes').addClass("collapse in");

	$('.table').DataTable( {
		responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });

   </tiles:putAttribute>

</tiles:insertDefinition>