<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.membresias" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="catalogos.membresias" />
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
						<c:if test="${empty membresias.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty membresias.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="membresia" usuario="form" >
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="membresia">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">

							
								<div class="col-md-4" >
								<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="control-label">Nombre Socio :</label>
								<form:input path="cliente.nombre" maxlength="100" type="text" readonly="true" class="form-control input" onkeyup="this.value = this.value.toUpperCase();"/>												
								</div>
								</div>
							
								<div class="col-md-4" >
								<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="control-label"><fmt:message key="catalogos.clientes.primerApellido" />: *</label>
								<form:input path="cliente.primerApellido" maxlength="100" type="text" readonly="true" class="form-control input" onkeyup="this.value = this.value.toUpperCase();"/>												
								</div>
								</div>
							
								<spring:bind path="membresia.plan">
									<div class="col-md-4">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="control-label">Plan *</label>
											<form:select path="plan.id" multiple="false" class="select2-container form-control select2me">
												<form:options itemValue="id" itemLabel="nombre" items="${planes}"/>
											</form:select>															
											<form:errors path="plan.id" class="help-block"></form:errors>										
										</div>
									</div>
								</spring:bind>
							
							</div>
							

							<c:if test="${not empty membresia.id}">
							<div class="row">
								<div class="col-md-3">
								<h4 class="form-section">Seleccionar Nueva Fecha: </h4>
								</div>
								<spring:bind path="membresia.fechaInicio"> 	     
									<div class="col-md-3">
		                                 <div class="form-group ${status.error ? 'has-error' : ''}">
		                                 	<label class="control-label">Fecha de Inicio del Plan: *</label>
											<div style="color: red;">
												<fmt:formatDate pattern="dd/MM/yyyy" value="${membresia.fechaInicio}" />
											</div>
		                                    <div class="input-group input-medium date date-picker"
		                                         data-date-format="yyyy/mm/dd" id="fechaInicio"> 
		                                         <form:input path="fechaInicio" type="date" maxlength="10" 
		                                             class="form-control" id="fechaInicio" pattern="yyyy/mm/dd"/>
		                                         <form:errors path="fechaInicio" class="help-block"></form:errors>
		                                    </div>
		                                 </div>
		                             </div>
								</spring:bind>
								
								<div class="col-md-3">
								<div class="form-group">
								<label>Estado: </label>									
								<div><c:out value="${membresia.estado}"/></div>
								</div>
								</div>	
											
							</div>
							</c:if>
							
													
						</div>
						<div class="form-actions">
							<c:if test="${empty membresia.id}">
								<sec:authorize access="hasAnyRole('ALTA_MEMBRESIAS')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
								</sec:authorize>
							</c:if>
							<c:if test="${not empty membresia.id}">
								<sec:authorize access="hasAnyRole('EDITA_MEMBRESIAS')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save.changes"/></button>									
								</sec:authorize>
							</c:if>
							
							<a id="cancelar" href="<c:url value="/membresias/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
						</div>
						
						<form:hidden path="id"/>
						<form:hidden path="idEmpresa"/>
						<form:hidden path="cliente.id"/>
						<form:hidden path="estado"/>
						<form:hidden path="fechaFin"/>
					</form:form>
						
                   	</div>
               	</div>
        </div>
		
		
		
	</div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="scripts">
	
	<%-- <script src="<c:url value='/assets/js/bootstrap-wysiwyg.js'/>"></script> --%>
	<script src="<c:url value='/assets/js/trumbowyg.js'/>"></script>
	<script src="<c:url value='/assets/js/langs/es.min.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/assets/js/ui/trumbowyg.min.css'/>"></script>
	
	</tiles:putAttribute>
		
	<tiles:putAttribute name="ready"> 
	
	$('#membresias_op').addClass("active ");
	$('#membresias').addClass("collapse in");
	
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
