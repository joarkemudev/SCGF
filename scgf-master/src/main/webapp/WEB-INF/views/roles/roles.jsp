<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">

	
	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.roles" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">account_circle</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">Roles</h4>
						<div class="toolbar">
                            <sec:authorize access="hasAnyRole('ALTA_ROLES')">
                            <a href="add" class="btn brn-success"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a>
							</sec:authorize>
                        </div>
					
						<table id="tableRoles"
							class="table table-striped table-no-bordered table-hover">
							<thead>
								<tr>
									<th>Id</th>
									<th><fmt:message key="catalogos.roles.nombre" /></th>
									<th><fmt:message key="catalogos.roles.permisos" /></th>
									<th><fmt:message key="catalogos.acciones" /></th>
									
								</tr>
							</thead>
							<tbody>
                            <c:forEach var="rol" items="${roles}"> 
                                 <tr> 
                                 	<td>${rol.id}</td>
                                 	<td>
									    <sec:authorize access="hasAnyRole('EDITA_ROLES')">
									        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
									        <a href="edit?id=${rol.id}">${rol.nombre}</a>
									    </sec:authorize>
									    <sec:authorize access="!hasAnyRole('EDITA_ROLES')">
									        <!-- Si el usuario no tiene permiso, muestra solo el nombre como texto -->
									        ${rol.nombre}
									    </sec:authorize>
									</td>
                                    <td>${rol.permisos} </td> 
                                    <td>
                                    <sec:authorize access="hasAnyRole('ELIMINA_ROLES')">
                                    <button type="button" onclick="eliminar('delete?id=${rol.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
                                    </sec:authorize>
                                    </td>

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
	$('#roles').addClass("collapse in");
	$('#roles_op').addClass("active");
	
	$('.table').DataTable( {
		responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });
   </tiles:putAttribute>

</tiles:insertDefinition>