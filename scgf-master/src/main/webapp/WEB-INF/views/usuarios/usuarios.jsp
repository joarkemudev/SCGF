<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.usuarios" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">person</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">Usuarios</h4>
						<div class="toolbar">
                            <sec:authorize access="hasAnyRole('ALTA_USUARIOS')">
                            <a href="add" class="btn brn-success"> <i
								class="fa fa-plus"></i> <span class="hidden-480"> Nuevo </span>
							</a>
							</sec:authorize>
                        </div>
						<table id="tableUsuarios"
							class="table table-striped table-no-bordered table-hover">
							<thead>
								<tr>	
									<th>Id</th>					
									<th><fmt:message key="catalogos.usuarios.nombre" /></th>
									<th><fmt:message key="catalogos.usuarios.nombreAcceso" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_USUARIOS')">
									<th><fmt:message key="catalogos.usuarios.rol" /></th>
									</sec:authorize>
									<th><fmt:message key="catalogos.usuarios.estado" /></th>
									<th>Empresa</th>
									<sec:authorize access="hasAnyRole('ELIMINA_USUARIOS')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>

								</tr>
							</thead>
							<tbody>
								<c:forEach var="usu" items="${usuarios}">
									<tr>
									<td>${rol.id}</td>
										<td>
										    <sec:authorize access="hasAnyRole('EDITA_USUARIOS')">
										        <!-- Si el usuario tiene permiso, muestra el nombre como un enlace -->
										        <a href="edit?id=${usu.id}">${usu.nombre}</a>
										    </sec:authorize>
										    <sec:authorize access="!hasAnyRole('EDITA_USUARIOS')">
										        <!-- Si el usuario no tiene permiso, muestra solo el nombre como texto -->
										        ${usu.nombre}
										    </sec:authorize>
										</td>
<%-- 										<sec:authorize access="hasAnyRole('CAMBIA_CLAVE')"> --%>
<%-- 										<td><a href="cambio?id=${usuario.id}">${usuario.nombre}</a></td> --%>
<%-- 										</sec:authorize> --%>
										<td>${usu.nombreAcceso}</td>
										<sec:authorize access="hasAnyRole('ELIMINA_USUARIOS')">
										<td>${usu.rol.nombre}</td>
										</sec:authorize>
										<td>${usu.estado.label}</td>
										<td>${usu.empresa.nombre}</td>				
										<sec:authorize access="hasAnyRole('ELIMINA_USUARIOS')">
										<td>
										<button type="button" onclick="eliminar('delete?id=${usu.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button>
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
	$('#catalogos_op').addClass("active ");
	$('#catalogos').addClass("collapse in");
	$('#usuarios_op').addClass("active");

	$('.table').DataTable( {
		responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
    });
   </tiles:putAttribute>

</tiles:insertDefinition>