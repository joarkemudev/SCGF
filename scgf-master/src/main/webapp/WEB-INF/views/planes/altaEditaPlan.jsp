<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.planes" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="catalogos.planes" />
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
						<c:if test="${empty plan.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty plan.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="plan" usuario="form" >
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="plan">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">
							
							<spring:bind path="plan.nombre">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.planes.nombre"/> *</label>							
									<form:input path="nombre" maxlength="100" required="true" type="text" class="form-control input" oninput="this.value = this.value.toUpperCase();"/>					
									<form:errors path="nombre" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>	
							
							<spring:bind path="plan.descripcion">
								<div class="col-md-5">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.planes.descripcion"/> *</label>							
									<form:input path="descripcion" maxlength="100" required="true" type="text" class="form-control input" />					
									<form:errors path="descripcion" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="plan.duracion">
								<div class="col-md-3">
									<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="control-label"><fmt:message key="catalogos.planes.duracion"/> *</label>
									<form:select path="duracion" required="true" multiple="false" class="select2-container form-control select2me">
										<form:options items="${tipoEntre}"/>
									</form:select>					
									<form:errors path="duracion" class="help-block"></form:errors>								
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="plan.duracionCantidad">
								<div class="col-md-1">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="control-label">Cantidad *</label>											
										<form:input path="duracionCantidad" maxlength="9" minlength="1" required="true" type="currency" class="form-control input upper"/>					
										<form:errors path="duracionCantidad" class="help-block"></form:errors>											
									</div>
								</div>
							</spring:bind>
							
							</div>
							<div class="row">
							<spring:bind path="plan.precio">
								<div class="col-md-3">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}"> <fmt:formatNumber type="currency" value="${pl.precio}" />
										<label class="control-label"><fmt:message key="catalogos.planes.precio"/>*</label>										 												
										<form:input path="precio" required="true" type="currency" maxFractionDigits="4" class="form-control input upper"/>					
										<form:errors path="precio" class="help-block"></form:errors>											
									</div>
								</div>
							</spring:bind>
							<spring:bind path="plan.accesoClases">
								<div class="col-md-3">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.planes.accesoClases" /> *</label>
										<form:select path="accesoClases" required="true"
											multiple="false"
											class="select2-container form-control select2me">
											<form:option value="1">Si</form:option>
											<form:option value="0">No</form:option>
										</form:select>
										<form:errors path="accesoClases" class="help-block"></form:errors>
									</div>
								</div>
							</spring:bind>	
							
							<spring:bind path="plan.invitadosPermitidos">
								<div class="col-md-3">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.planes.invitados" /> *</label>
										<form:select path="invitadosPermitidos" required="false"
											multiple="false"
											class="select2-container form-control select2me">
											<form:option value="0"></form:option>
											<form:option value="1">Uno</form:option>
											<form:option value="2">Dos</form:option>
											<form:option value="3">Tres</form:option>
										</form:select>
										<form:errors path="invitadosPermitidos" class="help-block"></form:errors>
									</div>
								</div>
							</spring:bind>
							
							<spring:bind path="plan.estado">
								<div class="col-md-3">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message key="catalogos.usuarios.estado"/> *</label>
										<form:select path="estado" required="true" multiple="false" class="select2-container form-control select2me">
											<form:options items="${estados}"/>
										</form:select>															
										<form:errors path="estado" class="help-block"></form:errors>										
									</div>
								</div>
							</spring:bind>	
							
							</div>

							<c:if test="${not empty plan.id}">
							<h4 class="form-section">Datos Informativos </h4>
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
									<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
									<div ><fmt:formatDate value="${plan.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
									<div><c:out value="${plan.usuarioAlta}"/></div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
									<div ><fmt:formatDate value="${plan.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
									<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
									<div><c:out value="${plan.usuarioModifica}"/></div>
									</div>
								</div>				
							</div>
							</c:if>
							
													
						</div>
						<div class="form-actions">
							<c:if test="${empty plan.id}">
								<sec:authorize access="hasAnyRole('ALTA_PLANES')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
								</sec:authorize>
							</c:if>
							<c:if test="${not empty plan.id}">
								<sec:authorize access="hasAnyRole('EDITA_PLANES')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save.changes"/></button>									
								</sec:authorize>
							</c:if>
							
							<a id="cancelar" href="<c:url value="/planes/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
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
	<link rel="stylesheet" href="<c:url value='/assets/js/ui/trumbowyg.min.css'/>"></script>
	
	</tiles:putAttribute>
		
	<tiles:putAttribute name="ready"> 
	
	$('#planes_op').addClass("active ");
	$('#planes').addClass("collapse in");
	
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