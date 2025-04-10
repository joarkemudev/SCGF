<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="defaultTemplate">
<fmt:setLocale value="en_US" scope="session"/>

	<tiles:putAttribute name="title">
		<fmt:message key="catalogos.pagos" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="row">

			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">monetization_on</i>
					</div>
					<div class="card-content">
						<h4 class="card-title"><fmt:message key="catalogos.pagos" /></h4>
						
						<div class="toolbar">
					
							<div>
								<form:form id="buscarForm" action="buscarP" method="POST"
								modelAttribute="bp">
									
								<!-- Filtros especiales para facturas -->

								<div class="row">

									<input type="hidden" name="idEmpresa" value="${empId}" />

									<div class="col-md-2">
										<div class="form-group  ${status.error ? 'has-error' : ''}">

											<label class="control-label">Documento</label>
											<form:select path="tipoDocumento" multiple="false"
												class="select2-container form-control select2me" >
												<form:option value=""></form:option>
												<form:options items="${tipos}" />
											</form:select>
										</div>
									</div>
									
									<div class="col-md-2" id="bPagos" style="display: none;">
										<div class="form-group  ${status.error ? 'has-error' : ''}">

											<label class="control-label">Método de Pago</label>
											<form:select path="metodoPago" multiple="false"
												class="select2-container form-control select2me" >
												<form:option value=""></form:option>
												<form:options items="${metodos}" />
											</form:select>
										</div>
									</div>
									<div class="col-md-2">
										<div class="form-group  ${status.error ? 'has-error' : ''}">

											<label class="control-label">Socio</label>
											<form:select path="idCliente" multiple="false"
												class="select2-container form-control select2me" >
												<form:option value=""></form:option>
												<form:options itemValue="id" itemLabel="nombreCompleto"
													items="${socios}" />
											</form:select>
										</div>
									</div>

									<div class="col-md-2">
										<div class="form-group  ${status.error ? 'has-error' : ''}">
											<label class="control-label">Fecha Carga "del"</label>
											<form:input path="fi" class="form-control datepicker" />
											<span class="material-input"></span>
										</div>
									</div>

									<div class="col-md-2">
										<div class="form-group  ${status.error ? 'has-error' : ''}">
											<label class="control-label">Fecha Carga "al"</label>
											<form:input path="ff" class="form-control datepicker" />
										</div>

									</div>

									<div class="col-md-1">
										<button type="button" id="submitBtn" class="btn btn-success">Buscar</button>

									</div>
								</div>
								
							</form:form>
						</div>

                        </div>
                        
                        <!-- Resultado de búsqueda de Pagos -->
                        <div id="divPagos" style="display: none;">
						<h4>Resultados de la búsqueda</h4><hr>
						<table id="tablePago" class="table table-striped table-no-bordered table-hover ">
							<thead>
								<tr>
									<th>Id</th>
									<th>Socio</th>
									<th>Detalle</th>
									<th>Fecha Pago</th>
									<th>Médio de Pago</th>
									<th>Monto Pago</th>
									<th>Id Factura</th>
									<th>Estado Factura</th>
<%-- 									<sec:authorize access="hasAnyRole('ELIMINA_PAGOS')"> --%>
<%-- 									<th><fmt:message key="catalogos.acciones" /></th> --%>
<%-- 									</sec:authorize> --%>
								</tr>
							</thead>
							<tbody >
								<c:forEach var="pag" items="${pagos}">
									<tr>
										<td>${pag.id}</td>
										<td>										   
								        <a >${pag.cliente.nombre} ${pag.cliente.primerApellido}</a>										 
										</td> 
										<td>${pag.factura.detalle}</td>
										<td><fmt:formatDate value="${pag.fechaPago}" pattern="dd/MM/yyyy" /></td>
										<td>${pag.metodoPago}</td>
	                                   	<td ><fmt:formatNumber type="currency" value="${pag.montoPagado}" /></td>	                                   	 	                               
  	                                   	<td>${pag.factura.uuid}</td>
  	                                   	<td>${pag.factura.estado.label}</td>
<%--  	                                   	<sec:authorize access="hasAnyRole('ELIMINA_PAGOS')"> --%>
<!--  	                                   	<td> -->
<%--  										<button type="button" onclick="eliminar('delete?id=${pag.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button> --%>
<!--  										</td> -->
<%--  										</sec:authorize> --%>
									</tr>
								</c:forEach>
							</tbody>

						</table>
						</div>
						<!-- Resultado de búsqueda de Facturas -->
                        <div id="divFacturas" style="display: none;">
						<h4>Resultados de la búsqueda</h4><hr>
						<table id="tableFactura" class="table table-striped table-no-bordered table-hover ">
							<thead>
								<tr>
									<th>Id</th>
									<th>Socio</th>
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
								</tr>
							</thead>
							<tbody >
								<c:forEach var="fac" items="${facturas}">
									<tr>
										<td>${fac.id}</td>
										<td>										   
								        <a >${fac.cliente.nombre} ${fac.cliente.primerApellido} </a>										 
										</td> 
										<td>${fac.uuid}</td>
										<td>${fac.estado}</td>
										<td>${fac.detalle}</td>
										<td>${fac.ivaAplicable} %</td>
										<td ><fmt:formatNumber type="currency" value="${fac.subTotal}" /></td>
										<td ><fmt:formatNumber type="currency" value="${fac.valorIva}" /></td>
										<td ><fmt:formatNumber type="currency" value="${fac.total}" /></td>
										<td ><fmt:formatNumber type="currency" value="${fac.montoPagado}" /></td>
										<td>
										    <fmt:formatNumber type="currency" value="${fac.total - fac.montoPagado}" />
										</td>
										<td><fmt:formatDate value="${fac.fechaEmision}" pattern="dd/MM/yyyy" /></td>

<%--  	                                   	<sec:authorize access="hasAnyRole('ELIMINA_FACTURAS')"> --%>
<!--  	                                   	<td> -->
<%--  										<button type="button" onclick="eliminar('delete?id=${fac.id}');" class="btn btn-xs btn-danger btn-simple"><i class="fa fa-trash-o"></i></button> --%>
<!--  										</td> -->
<%--  										</sec:authorize> --%>
									</tr>
								</c:forEach>
							</tbody>

						</table>
						</div>

					</div>
		
				</div>
			</div>

		</div>
	</tiles:putAttribute>

	<tiles:putAttribute name="ready"> 
	$('#pagos_op').addClass("active ");
	$('#pagos').addClass("collapse in");

	if($("#tipoDocumento").val() != ""){
		filtros1($("#tipoDocumento").val());
	}
	
	$("#tipoDocumento").change(function(){
		console.log($(this).val());
		filtros1($(this).val());
	});
	
	$("#submitBtn").click(function(){
		 console.log("Submit.");
		 var mensaje="";
		 
		 if($("#tipoDocumento").val() == ""){
		  mensaje+="Favor de seleccionar un tipo de documento.<br>";   
		 }

		 if(mensaje.length>0){
		     swal("Atención",mensaje);
		     return
		 }else{
		     $("#buscarForm").submit();
		 }
		 
	});
	
	$('.table').DataTable( {
		responsive: true,
        searching: true,
        info:false,
        "language": {"url": "<c:url value="/assets/js/SpanishDT.json"/>"}
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
   
   <tiles:putAttribute name="scripts">
		<script>
		function filtros1(val) {
			if (val == "PAGOS") {
				$("#bPagos").show();
				$("#divPagos").show();
				$("#divFacturas").hide(); 
			} else if (val == "FACTURAS") {
				$("#divPagos").hide();
				$("#bPagos").hide();
				$("#divFacturas").show(); 
			}	
		}
	
	</script>


	</tiles:putAttribute>

</tiles:insertDefinition>