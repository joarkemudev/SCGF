<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogo.correos" var="entity"/> 

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
						<h4 class="card-title">
						<c:if test="${empty correo.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty correo.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="correo" usuario="form" >
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="correo">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">
							<spring:bind path="correo.tipoActividad">
								<div class="col-md-4">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">						
									<label class="control-label"><fmt:message key="catalogo.correos.tipoActividad"/> *</label>									
									<form:select path="tipoActividad" required="true" multiple="false" class="select2-container form-control select2me">
									<form:option value=""></form:option>
									<form:options items="${tipoActividades}"/>								
									</form:select>															
									<form:errors path="tipoActividad" class="help-block"></form:errors>			
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="correo.asunto">
								<div class="col-md-8">
								<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
								<label class="control-label"><fmt:message key="catalogo.correos.asunto"/> *</label>
								
								<form:input path="asunto" maxlength="100" required="true" type="text" class="form-control input" />					
								<form:errors path="asunto" class="help-block"></form:errors>								
								</div>
								</div>
							</spring:bind>	
							
							</div>
							
							<div class="row">
							
							
							<spring:bind path="correo.titulo">
								<div class="col-md-12">
								<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
								<label class="control-label"><fmt:message key="catalogo.correos.titulo"/> *</label>
								
								<form:input path="titulo" maxlength="100" required="true" type="text" class="form-control input" />					
								<form:errors path="titulo" class="help-block"></form:errors>								
								</div>
								</div>
							</spring:bind>	
							
							</div>
							
							<div class="row">
							
							
							<spring:bind path="correo.contenido">
								<div class="col-md-12">
								<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
								<label class="control-label"><fmt:message key="catalogo.correos.contenido"/> *</label>
								<div id="contenidoDiv"></div>
								<form:hidden path="contenido"/>			
								<form:errors path="contenido" class="help-block"></form:errors>								
								</div>
								</div>
							</spring:bind>	
							
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="alert alert-info">
									<b>Variables disponibles para Solicitudes: </b>{solicitud}, {url}, {informacion}, {usuario}.<br>
									<b>Variables disponibles para Proveedores: </b>, {proveedor}, {clave}, {usuario}.<br>
									<b>Variables disponibles para Notificación Anticipo: </b>, {usuario} {solicitud}, {estadoMonitoreo}, {fechaComprobacion}.
									</div>
								</div>
							</div>
							
							
							<c:if test="${not empty correo.id}">
							<h4 class="form-section">Datos Informativos </h4>
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
									<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
									<div ><fmt:formatDate value="${correo.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
									<div><c:out value="${correo.usuarioAlta}"/></div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
									<div ><fmt:formatDate value="${correo.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
									<div><c:out value="${correo.usuarioModifica}"/></div>
									</div>
								</div>				
							</div>
							</c:if>
							
													
						</div>
						<div class="form-actions">
							<c:if test="${empty correo.id}">
							<sec:authorize access="hasAnyRole('ALTA_CORREOS')">
							<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
							</sec:authorize>
							</c:if>
							<c:if test="${not empty correo.id}">
							<sec:authorize access="hasAnyRole('EDITA_CORREOS')">
							<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save.changes"/></button>									
							</sec:authorize>
							</c:if>								
							<a id="cancelar" href="<c:url value="/correos/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
						</div>
						
						<form:hidden path="id"/>
						<form:hidden path="usuarioAlta"/>
						<form:hidden path="fechaAlta"/>
						<form:hidden path="usuarioModifica"/>
						<form:hidden path="fechaModifica"/>
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
	<link rel="stylesheet" href="<c:url value='/assets/js/ui/trumbowyg.min.css'/>">
	
	</tiles:putAttribute>
		
	<tiles:putAttribute name="ready"> 
	
	$('#configuracion_op').addClass("active ");
	$('#configuracion').addClass("collapse in");
	$('#correos_op').addClass("active");
	
	
	
	
	$('#cancelar').click(function(e) {
      e.preventDefault(); // Prevent the href from redirecting directly
      var linkURL = $(this).attr("href");
      cancelar(linkURL);
    });
	
	$('#contenidoDiv').trumbowyg({
		lang: 'es'
	});
	
	$('#contenidoDiv').trumbowyg('html', $("#contenido").val());
	
	$("#add").submit(function(){
		
		$("#contenido").val($('#contenidoDiv').trumbowyg('html'));
	});

	
	</tiles:putAttribute>
	
</tiles:insertDefinition>