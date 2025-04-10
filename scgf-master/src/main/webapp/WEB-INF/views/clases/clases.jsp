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
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">fitness_center</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.clases" /></h4>
						
						<div class="toolbar">
							<sec:authorize access="hasAnyRole('ALTA_CLASES')">
							<a href='<c:url value="add"/>' class="btn btn-success" data-toggle="tooltip" 
								title="Crea una nueva Clase"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a> 
							</sec:authorize>
						</div>
						
						<hr>
						<div class="toolbar">

							<label class="control-label"></label>
	
						</div>
						<table id="tableClases" class="table table-striped table-no-bordered table-hover ">
							<thead>
								<tr>
									<th>Id</th>
									<th><fmt:message key="catalogos.planes.nombre" /></th>
									<th><fmt:message key="catalogos.planes.descripcion" /></th>	
									<th>Fecha Hora Inicio</th>
									<th>Fecha HoraFin</th>
									<th>Cap Máxima</th>
									<th>Instructor</th>
									<sec:authorize access="hasAnyRole('ELIMINA_CLASES')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="cl" items="${clases}">
									<tr>
										<td>${cl.id}</td>
										<td>
											<sec:authorize access="hasAnyRole('EDITA_CLASES')">
										        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
										        <a href="edit?id=${cl.id}">${cl.nombre}</a></td>
										    </sec:authorize>
										</td> 
	                                   	<td>${cl.descripcion}</td>
  	                                   	<td><fmt:formatDate value="${cl.fechaHoraInicio}" pattern="dd/MM/yyyy hh:mm a" /></td>
  	                                   	<td><fmt:formatDate value="${cl.fechaHoraFin}" pattern="dd/MM/yyyy hh:mm a" /></td>
 	                                   	<td><c:out value="${cl.capacidadMaxima}" /> Socios</td>	
 	                                   	<td>${cl.inscripciones.size()}</td>								
										<td>${cl.usuario.nombre}</td>	                          
 	                                   	<sec:authorize access="hasAnyRole('ELIMINA_CLASES')">
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
	$('#clases_op').addClass("active ");
	$('#clases').addClass("collapse in");

	$('#tableClases').DataTable({
        responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });

   </tiles:putAttribute>

</tiles:insertDefinition>