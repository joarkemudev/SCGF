<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.clases" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="catalogos.clases" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">		
		<div class="row">	
		
		<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">add_box</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">
						<c:if test="${empty clases.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty clases.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="clase" usuario="form" >
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="clase">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">

								<input type="hidden" name="empresa.id" value="${emp.id}" />
								
								<spring:bind path="clase.usuario">
									<div class="col-md-3">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="control-label">Instructor *</label>
											<form:select path="usuario.id" multiple="false" class="select2-container form-control select2me">
												<form:option value="">Seleccione un Instructor</form:option>
												<form:options itemValue="id" itemLabel="nombre" items="${usuarios}"/>
											</form:select>															
											<form:errors path="usuario.id" class="help-block"></form:errors>										
										</div>
									</div>
								</spring:bind>
								<spring:bind path="clase.nombre">
									<div class="col-md-3">
										<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.planes.nombre"/> *</label>							
										<form:input path="nombre" maxlength="100" required="true" type="text" class="form-control input" oninput="this.value = this.value.toUpperCase();"/>					
										<form:errors path="nombre" class="help-block"></form:errors>								
										</div>
									</div>
								</spring:bind>	
								
								<spring:bind path="clase.descripcion">
									<div class="col-md-6">
										<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.planes.descripcion"/> *</label>							
										<form:input path="descripcion" maxlength="100" required="true" type="text" class="form-control input" />					
										<form:errors path="descripcion" class="help-block"></form:errors>								
										</div>
									</div>
								</spring:bind>
							
							</div>
							<div class="row">
								<spring:bind path="clase.fechaHoraInicio"> 	     
								    <div class="col-md-3">
								        <div class="form-group ${status.error ? 'has-error' : ''}">
								            <div style="color: red;">
								                <label class="control-label">Hora Inicio de la Clase *</label>
								                <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${clase.fechaHoraInicio}" />
								            </div>
								            <div class="input-group input-medium date date-picker" data-date-format="yyyy/MM/dd HH:mm" id="fechaHoraInicio"> 
								                <form:input path="fechaHoraInicio" type="datetime-local" maxlength="16" required="true"
								                    class="form-control" id="fechaHoraInicio" pattern="yyyy/MM/dd HH:mm"/>
								                <form:errors path="fechaHoraInicio" class="help-block"></form:errors>
								            </div>
								        </div>
								    </div>
								</spring:bind>
								
								<spring:bind path="clase.fechaHoraFin"> 	     
								    <div class="col-md-3">
								        <div class="form-group ${status.error ? 'has-error' : ''}">
								            <div style="color: red;">
								                <label class="control-label">Hora Fin de la Clase *</label>
								                <fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${clase.fechaHoraFin}" />
								            </div>
								            <div class="input-group input-medium date date-picker" data-date-format="yyyy/MM/dd HH:mm" id="fechaHoraInicio"> 
								                <form:input path="fechaHoraFin" type="datetime-local" maxlength="16" required="true"
								                    class="form-control" id="fechaHoraFin" pattern="yyyy/MM/dd HH:mm"/>
								                <form:errors path="fechaHoraFin" class="help-block"></form:errors>
								            </div>
								        </div>
								    </div>
								</spring:bind>
								
								<spring:bind path="clase.capacidadMaxima">
									<div class="col-md-2">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="control-label">Capacidad Máxima *</label>											
											<form:input path="capacidadMaxima" maxlength="9" minlength="1" required="true" type="currency" class="form-control input upper"/>					
											<form:errors path="capacidadMaxima" class="help-block"></form:errors>											
										</div>
									</div>
								</spring:bind>
								
								<spring:bind path="clase.estado">
									<div class="col-md-3">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="control-label"><fmt:message key="catalogos.usuarios.estado"/> *</label>
											<form:select path="estado" required="true" multiple="false" class="select2-container form-control select2me">
												<form:options items="${estados}"/>
											</form:select>															
											<form:errors path="estado" class="help-block"></form:errors>										
										</div>
									</div>
								</spring:bind>			
							</div>
							<c:if test="${not empty clase.id}">
							<h4 class="form-section">Datos Informativos </h4>
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
									<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
									<div ><fmt:formatDate value="${clase.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
									<div><c:out value="${clase.usuarioAlta}"/></div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
									<div ><fmt:formatDate value="${clase.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
									<div><c:out value="${clase.usuarioModifica}"/></div>
									</div>
								</div>				
							</div>
							</c:if>
													
						</div>
						<div class="form-actions">
							<c:if test="${empty clase.id}">
								<sec:authorize access="hasAnyRole('ALTA_CLASES')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
								</sec:authorize>
							</c:if>
							<c:if test="${not empty clase.id}">
								<sec:authorize access="hasAnyRole('EDITA_CLASES')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save.changes"/></button>									
								</sec:authorize>
							</c:if>
							
							<a id="cancelar" href="<c:url value="/clases/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
						</div>
						
						<form:hidden path="id"/>
						<form:hidden path="empresa.id"/>
						<form:hidden path="fechaHoraInicio"/>
						<form:hidden path="fechaHoraFin"/>

						<form:hidden path="usuarioAlta"/>
						<form:hidden path="fechaAlta"/>
						<form:hidden path="usuarioModifica"/>
						<form:hidden path="fechaModifica"/>
					</form:form>
						
                   	</div>
               	</div>
        </div>
		
	</div>
	<c:if test="${not empty clase.id}" >
		<div class="row">	
			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">folder</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">Socios Inscritos </h4>
							
						<table id="tableInscritos"
							class="table table-striped table-no-bordered table-hover">
							<thead>
								<tr>
									<th><fmt:message key="catalogos.clientes.nombre" /></th>
									<th><fmt:message key="catalogos.usuarios.correoElectronico" /></th>
									<th>Plan</th>
									<th><fmt:message key="catalogos.clientes.telefonoMovil" /></th>
									<th><fmt:message key="catalogos.clientes.genero" /></th>
									<th><fmt:message key="catalogos.usuarios.estado" /></th>
									<sec:authorize access="hasAnyRole('ELIMINA_INSCRITOS')">
									<th><fmt:message key="catalogos.acciones" /></th>
									</sec:authorize>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${inscritosClase}" var="ic">
									<tr>
	                                   	<td>${ic.cliente.nombreCompleto}</td>
 	                                   	<td>${ic.cliente.correoElectronico}</td>
 	                                   	<td>${ic.cliente.plan.nombre}</td>
 	                                   	<td>${ic.cliente.telefonoMovil}</td> 
 	                                   	<td>${ic.cliente.genero.nombre}</td> 
 	                                   	<td>${ic.cliente.estado}</td>	                          
 	                                   	<sec:authorize access="hasAnyRole('ELIMINA_INSCRITOS')">
 	                                   	<td>
 										<button class="btn btn-danger btn-xs btn-simple" onclick="eliminar('deleteInscritosClase?id=${ic.id}&idClase=${clase.id}');">
														<i class="fa fa-trash"></i></button> 
 										</td>
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
	</c:if>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="scripts">
	
	<%-- <script src="<c:url value='/assets/js/bootstrap-wysiwyg.js'/>"></script> --%>
	<script src="<c:url value='/assets/js/trumbowyg.js'/>"></script>
	<script src="<c:url value='/assets/js/langs/es.min.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/assets/js/ui/trumbowyg.min.css'/>"></script>
	
	</tiles:putAttribute>
		
	<tiles:putAttribute name="ready"> 
	
	$('#clases_op').addClass("active ");
	$('#clases').addClass("collapse in");
	
	$('#add').validate({
		 lang: 'es',
       submitHandler: function(form) {
          guardar(form);
        }
	});
	
	$('#cancelar').click(function(e) {
      e.preventDefault(); // Prevent the href from redirecting directly
      var linkURL = $(this).attr("href");
      cancelar(linkURL);
    });

	</tiles:putAttribute>
	
</tiles:insertDefinition>
