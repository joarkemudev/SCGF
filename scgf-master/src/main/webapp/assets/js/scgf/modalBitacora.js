//Acciones del modal de Bitácora.

$(function(){

	tableBitacora = $("#tableBitacora").DataTable({
		data:[],
		columns: [
			{ "data": "estado" },
			{ "data": "observaciones" },
			{ "data": "usuario" },
			{ "data": "fecha" }

		],
		rowCallback: function (row, data) {},
		filter: true,
		info: true,
		ordering: false,
		processing: true,
		retrieve: true,
		 "language": {
	            "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
	        }
	});
	
	
});

function getBitacora(cl){
	$("#bitacoraModal").modal();
	
	$.getJSON("../clientes/consultaBitacora", {
			idCliente : cl	
		}, function(bitacora) {			
			$("#tableBitacora").DataTable().clear().draw();
			$("#tableBitacora").DataTable().rows.add(bitacora).draw();
			
		});
}
