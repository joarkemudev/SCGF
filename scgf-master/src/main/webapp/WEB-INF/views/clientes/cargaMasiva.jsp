<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.clientes" var="entity"/> 

	<tiles:putAttribute name="title">${entity}</tiles:putAttribute>

	<tiles:putAttribute name="body">		
		<div class="row">	
		
		<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">playlist_add</i>
					</div>
					<div class="card-content">
						<h4>Carga masiva de Socios</h4>
						
						<c:if test="${not empty mensaje.mensajeFijo}">
							<span class="h4 text-danger">No se sealizó la carga por los siguientes errores: </span>
							<div class="text-danger">
							<c:forEach items="${mensaje.mensajeFijo}" var="msg">
								${msg}<br>
							</c:forEach>
							</div>
							
						</c:if>
						
						
						<p>
						 Agrega nuevos socios mediante un archivo de excel.<br>
						 Descarga el template del archivo de carga masiva <a href="../documentos/CargaSociosMasivo.xls" " target="_blank">aquí</a>.
						</p>
						
						
						<form:form id="ejecutaCargaMasiva" action="ejecutaCargaMasiva" method="POST" modelAttribute="archivo" enctype="multipart/form-data">
						
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="archivo">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">
							<spring:bind path="archivo.archivo">
								<div class="col-md-4">
								<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
									<label class="control-label">Archivo *</label>
										
										<div class="fileinput fileinput-new text-center" data-provides="fileinput">
										    <div class="fileinput-new thumbnail">
										        <img src="../assets/img/excel.jpg" alt="...">
										    </div>
										    <div class="fileinput-preview fileinput-exists thumbnail"></div>
										    <div>
										        <span class="btn btn-danger btn-round btn-file">
										            <span class="fileinput-new">Selecciona Archivo</span>
										            <span class="fileinput-exists">Cambiar</span>
										            <input type="file" name="archivo" accept="application/vnd.ms-excel" />
										        </span>
										        <a href="#pablo" class="btn btn-danger btn-round fileinput-exists" data-dismiss="fileinput"><i class="fa fa-times"></i> Remover</a>
										    </div>
										</div>
																															
									<form:errors path="archivo" class="help-block"></form:errors>										
								</div>
								</div>
							</spring:bind>
							
							</div>
							</div>	
							
							<div class="form-actions">
							<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.process"/></button>									
							<a id="cancelar" href="<c:url value="/partidas/partidas"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
							</div>
						
							
					</form:form>
						
                   	</div>
               	</div>
        </div>
		
	</div>
	</tiles:putAttribute>
	
	<tiles:putAttribute name="ready"> 

	$('#clientes').addClass("collapse in");
	$('#clientes_op').addClass("active");
	
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