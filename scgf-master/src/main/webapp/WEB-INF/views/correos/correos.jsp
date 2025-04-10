<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
	
	<tiles:putAttribute name="title">
		<fmt:message key="catalogo.correos" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">mail_outline</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogo.correos" /></h4>
						<div class="toolbar">
                            <sec:authorize access="hasAnyRole('ALTA_CORREOS')">
                            <a href="add" class="btn btn-success"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a>
							</sec:authorize>							
                        </div>
					
						<table id="tableCorreos" class="table table-striped table-no-bordered table-hover">
							<thead>
								<tr>
									<th>Id</th>
									<th><fmt:message key="catalogo.correos.tipoActividad" /></th>
									<th><fmt:message key="catalogo.correos.asunto" /></th>								
									<th><fmt:message key="catalogo.correos.titulo" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_CORREOS')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
									
								</tr>
							</thead>
							<tbody>
                            <c:forEach var="correo" items="${correos}"> 
                                 <tr> 
                                 	<td>${correo.id}</td>
                                    <td><a href="edit?id=${correo.id}">${correo.tipoActividad.nombre}</a> </td> 
                                   	<td>${correo.asunto}</td>
                                   	<td>${correo.titulo}</td>										
                                   
                                   	<sec:authorize access="hasAnyRole('ELIMINA_CORREOS')">
                                   		<td>
                                   	<button type="button" onclick="eliminar('delete?id=${correo.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
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
	$('#correos_op').addClass("active");
	$('#correos').addClass("collapse in");

	$('#tableCorreos').DataTable({
        responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });
    
   </tiles:putAttribute>

</tiles:insertDefinition>