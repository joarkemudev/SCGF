<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.clientes" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="catalogos.clientes" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">		
		<div class="row">	
		
		<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">perm_identity</i>
					</div>
					<div class="toolbar">
						<button type="button" class="btn"
							onclick="getBitacora()" data-toggle="tooltip" 
							title="Muestra la información de la bitácora de la Solicitud.">
							<i class="fa fa-list-alt"></i> <span class="hidden-480">Bitácora</span>
						</button>

					</div>
					<div class="card-content">
						<h4 class="card-title">
						<c:if test="${empty cliente.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty cliente.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="cliente" usuario="form" >
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="cliente">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">
							
							<spring:bind path="cliente.nombre">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.clientes.nombre"/> *</label>							
									<form:input path="nombre" maxlength="100" required="true" type="text" class="form-control input" oninput="this.value = this.value.toUpperCase();"/>					
									<form:errors path="nombre" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>	
							
							<spring:bind path="cliente.primerApellido">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.clientes.primerApellido"/> *</label>							
									<form:input path="primerApellido" maxlength="100" required="true" type="text" class="form-control input" oninput="this.value = this.value.toUpperCase();" />					
									<form:errors path="primerApellido" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="cliente.segundoApellido">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.clientes.segundoApellido"/> *</label>
									<form:input path="segundoApellido" maxlength="100" required="true" type="text" class="form-control input" oninput="this.value = this.value.toUpperCase();"/>					
									<form:errors path="segundoApellido" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>
							<spring:bind path="cliente.fechaNacimiento"> 	     
								<div class="col-md-3">
	                                 <div class="form-group ${status.error ? 'has-error' : ''}">
	                                 	<label class="control-label"><fmt:message key="catalogos.clientes.fechaNacimiento"/> *</label>
										<div style="color: red;">
											<fmt:formatDate pattern="dd/MM/yyyy" value="${cliente.fechaNacimiento}" />
										</div>
	                                    <div class="input-group input-medium date date-picker"
	                                         data-date-format="yyyy/mm/dd" id="fechaNacimiento"> 
	                                         <form:input path="fechaNacimiento" type="date" maxlength="10" 
	                                             class="form-control" id="fechaNacimiento" pattern="yyyy/mm/dd"/>
	                                         <form:errors path="fechaNacimiento" class="help-block"></form:errors>
	                                    </div>
	                                 </div>
	                             </div>
							</spring:bind>
														
							</div>
							
							<div class="row">
							<spring:bind path="cliente.tipoIdentificacion">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">						
										<label class="control-label"><fmt:message key="catalogos.clientes.tipoIdentificacion"/> *</label>									
										<form:select path="tipoIdentificacion" required="true" multiple="false" class="select2-container form-control select2me">
											<form:option value=""></form:option>
											<form:options items="${tipoIdenti}"/>								
										</form:select>															
										<form:errors path="tipoIdentificacion" class="help-block"></form:errors>			
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="cliente.numIdentificacion">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">						
									<label class="control-label"><fmt:message key="catalogos.clientes.numIdentificacion"/> *</label>									
									<form:input path="numIdentificacion" maxlength="40" required="true" type="password" class="form-control input" />					
									<form:errors path="numIdentificacion" class="help-block"></form:errors>		
									</div>
								</div>
							</spring:bind>
													
							<spring:bind path="cliente.correoElectronico">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">						
										<label class="control-label"><fmt:message key="catalogos.usuarios.correoElectronico"/> *</label>									
										<form:input path="correoElectronico" maxlength="80" required="true" type="email" class="form-control input upper" />					
										<form:errors path="correoElectronico" class="help-block"></form:errors>		
									</div>
								</div>
							</spring:bind>
		
							<spring:bind path="cliente.telefonoMovil">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.clientes.telefonoMovil"/> *</label>											
										<form:input path="telefonoMovil" maxlength="13" minlength="10" required="true" type="currency" class="form-control input upper"/>					
										<form:errors path="telefonoMovil" class="help-block"></form:errors>											
									</div>
								</div>
							</spring:bind>
							
							</div>
							
							<div class="row">
							
							<spring:bind path="cliente.genero">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.clientes.genero"/> *</label>
										<form:select path="genero" required="true" multiple="false" class="select2-container form-control select2me">
											<form:options items="${tipoGen}"/>
										</form:select>															
										<form:errors path="genero" class="help-block"></form:errors>										
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="cliente.estado">
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
							<c:if test="${empty cliente.id}">
							    <!-- Botón para agregar una nueva partida -->
							    <spring:bind path="cliente.plan.id">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label">Plan *</label>
										<form:select id="plan" path="plan.id" multiple="false" class="select2-container form-control select2me">
											<form:option value="" label="Seleccionar un plan"/>
											<c:if test="${empty cliente.id}">
											<form:options itemValue="id" itemLabel="nombre" items="${planes}"/>
											</c:if>
										</form:select>															
										<form:errors path="plan.id" class="help-block"></form:errors>										
									</div>
								</div>
							</spring:bind>  
							</c:if>
							</div>

							<c:if test="${not empty cliente.id}">
							<h4 class="form-section">Datos Informativos </h4>
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
									<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
									<div ><fmt:formatDate value="${cliente.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
									<div><c:out value="${cliente.usuarioAlta}"/></div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
									<div ><fmt:formatDate value="${cliente.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
									<div><c:out value="${cliente.usuarioModifica}"/></div>
									</div>
								</div>				
							</div>
							</c:if>
							
						</div>
						<div class="form-actions">
							
						
							<c:if test="${empty cliente.id}">
								
								<sec:authorize access="hasAnyRole('ALTA_CLIENTES')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
								</sec:authorize>
							</c:if>
							<c:if test="${not empty cliente.id}">
							    <!-- Botón para agregar una nueva partida -->
							    
							</c:if>
						 	<c:if test="${not empty cliente.id}">
							    <!-- Botón de confirmación de cambios, protegido por el control de roles -->
							    <sec:authorize access="hasAnyRole('EDITA_CLIENTES')">
							        <button type="submit" class="btn btn-success">
							            <fmt:message key="labels.buttons.save.changes"/>
							        </button>
							    </sec:authorize>
							</c:if>
							
							<a id="cancelar" href="<c:url value="/clientes/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
						</div>
						
						<form:hidden path="id"/>
						<form:hidden path="plan.id"/>
						<form:hidden path="usuarioAlta"/>
						<form:hidden path="fechaAlta"/>
						<form:hidden path="usuarioModifica"/>
						<form:hidden path="fechaModifica"/>
					</form:form>
						
               	</div>
        	</div>
        </div>
	</div>
	<c:if test="${not empty cliente.id}" >
		<div class="row">	
			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">folder</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">Facturas Pendientes </h4>
						<button class="btn btn-success" id="btnAgregaPago" data-toggle="tooltip" 
							title="Agrega un nuevo pago al cliente">
						    <i class="fa fa-plus"></i>
						 	<span class="hidden-480">Agregar Pago</span>
						 </button>
							
						<table id="tableFacturas"
							class="table table-striped table-no-bordered table-hover">
							<thead>
								<tr>
									<th>Id Factura</th>
									<th>Estado</th>
									<th>Detalle</th>
									<th>Iva Aplicable</th>
									<th>Sub Total</th>						
									<th>Valor Iva</th>
									<th>Total</th>
									<th>Monto Pagado</th>
									<th>Monto a Pagar</th>
									<th>Fecha de Emisión</th>
									<th>Acciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${facturas}" var="fact">
									<tr>
										<c:if test="${fact.estado == 'PENDIENTE'}">
 											<td><a >${fact.uuid}</a></td>
											<td><c:out value="${fact.estado.label}" /></td>
											<td><c:out value="${fact.detalle}" /></td>
											<td>${fact.ivaAplicable} %</td>
											<td ><fmt:formatNumber type="currency" value="${fact.subTotal}" /></td>
											
											<td ><fmt:formatNumber type="currency" value="${fact.valorIva}" /></td>
											
											<td ><fmt:formatNumber type="currency" value="${fact.total}" /></td>
											<td ><fmt:formatNumber type="currency" value="${fact.montoPagado}" /></td>
											<td>
											    <fmt:formatNumber type="currency" value="${fact.total - fact.montoPagado}" />
											</td>
											<td><fmt:formatDate value="${fact.fechaAlta}" pattern="dd/MM/yyyy H:m" /></td>
											
											<td>
												<sec:authorize access="hasAnyRole('ELIMINA_FACTURAS')">												
												<button class="btn btn-danger btn-xs btn-simple" onclick="eliminar('delete?id=${fact.id}');">
													<i class="fa fa-trash"></i>
												</button> 
												</sec:authorize>
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
								
						</table>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	
	<c:import url="/WEB-INF/views/include/modalBitacora.jsp" />
	<c:import url="/WEB-INF/views/include/modalAltaPago.jsp" />
	</tiles:putAttribute>
	
	<tiles:putAttribute name="scripts">
	
	<script src="<c:url value='/assets/js/scgf/modalBitacora.js'/>"></script>
	<script src="<c:url value='/assets/js/scgf/modalAltaPago.js'/>"></script>
	<script src="<c:url value='/assets/js/trumbowyg.js'/>"></script>
	<script src="<c:url value='/assets/js/langs/es.min.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/assets/js/ui/trumbowyg.min.css'/>"></script>
	
	</tiles:putAttribute>
		
	<tiles:putAttribute name="ready"> 
	
	$('#clientes_op').addClass("active ");
	$('#clientes').addClass("collapse in");
	
	$('#add').validate({
		 lang: 'es',
       submitHandler: function(form) {
          guardar(form);
        }
	});
	
	$("#btnAgregaPago").click(function(){
		$("#agregaPagoModal").modal();
	});
	
	$('#cancelar').click(function(e) {
      e.preventDefault(); // Prevent the href from redirecting directly
      var linkURL = $(this).attr("href");
      cancelar(linkURL);
    });
    
    $('#estado').change(function() {		 
	    const estado = $(this).val(); // Obtén el ID del plan seleccionado
	    console.log("cuentas Solicitud Normal : "+ estado);	

	});
	
	$('.datetimepicker').datetimepicker({
	    icons: {
	        time: "fa fa-clock-o",
	        date: "fa fa-calendar",
	        up: "fa fa-chevron-up",
	        down: "fa fa-chevron-down",
	        previous: 'fa fa-chevron-left',
	        next: 'fa fa-chevron-right',
	        today: 'fa fa-screenshot',
	        clear: 'fa fa-trash',
	        close: 'fa fa-remove', 
	        useCurrent: true
	    }
	});

	</tiles:putAttribute>
	
</tiles:insertDefinition>