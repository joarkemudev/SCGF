<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-striped table-no-bordered table-hover ">
	<thead>
		<tr>
			<th>Socio XXX</th>
			<th>Detalle</th>
			<th>Fecha Pago</th>
			<th>Médio de Pago</th>
			<th>Valor Pago</th>
			<th>Id Factura</th>
			<th>Estado Factura</th>
		</tr>
	</thead>
	<tbody >
		<c:forEach var="item" items="${tablaDatos}">
			<tr>									   
		        <td>${item.id}</td>										 
			</tr>
		</c:forEach>
	</tbody>

</table>