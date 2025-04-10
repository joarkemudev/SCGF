/**
 * Contiene funciones basicas de los catálogos
 */

function cancelar(linkURL) {
	
	swal({
		title : '�Est�s seguro de que deseas Cancelar?',
		text : 'El registro no sufrir� cambios.',
		type : 'warning',
		showCancelButton : true,
		confirmButtonText : 'Si, Cancelar',
		cancelButtonText : 'No, Regresar',
		confirmButtonClass : "btn btn-success",
		cancelButtonClass : "btn btn-danger",
		buttonsStyling : false
	}).then(function() {
		window.location.href = linkURL;
	}, function(dismiss) {
		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
		if (dismiss === 'cancel') {
			//Acciones despues de cancelar.
		}
	});
}

function salir(linkURL) {
	
	swal({
		title : '�Est�s seguro de que deseas Salir?',
		text : '',
		type : 'warning',
		showCancelButton : true,
		confirmButtonText : 'Si',
		cancelButtonText : 'No, Regresar',
		confirmButtonClass : "btn btn-success",
		cancelButtonClass : "btn btn-danger",
		buttonsStyling : false
	}).then(function() {
		window.location.href = linkURL;
	}, function(dismiss) {
		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
		if (dismiss === 'cancel') {
			//Acciones despues de cancelar.
		}
	});
}

function confirma(linkURL) {
	
	swal({
		title : '�Est�s seguro de que deseas proceder con esta acci�n?',
		text : '',
		type : 'warning',
		showCancelButton : true,
		confirmButtonText : 'Si',
		cancelButtonText : 'No, Regresar',
		confirmButtonClass : "btn btn-success",
		cancelButtonClass : "btn btn-danger",
		buttonsStyling : false
	}).then(function() {
		window.location.href = linkURL;
	}, function(dismiss) {
		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
		if (dismiss === 'cancel') {
			//Acciones despues de cancelar.
		}
	});
}

// funcion para accionar el tipo de solicitud 

function accionTipo() {
		swal({
			title : '�Que tipo de Solicitud de Gasto deseas crear?',
			text : '',
			html:'Normal <input type="radio" name="tipo" value="Normal" id="opcionA"/> Programada <input type="radio" name="tipo" value="Programada" id="opcionB"/> ',
			type : 'warning',
			showCancelButton : true,
			confirmButtonText : 'Entrar',
			cancelButtonText : 'Cancelar',
			confirmButtonClass : "btn btn-success",
			cancelButtonClass : "btn btn-danger",
			buttonsStyling : false
		}).then(function() {
			//window.location.href = linkURL;
			if ($('input:radio[name=tipo]:checked').val()== 'Normal'){
				
			window.location.href ="add";
				
			}else if ($('input:radio[name=tipo]:checked').val()== 'Programada') {
				
			alert ("Pantalla en construcci�n");	
				
			}
		}, function(dismiss) {
			// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
			if (dismiss === 'cancel') {
				//Acciones despues de cancelar.
				alert($('input:radio[name=tipo]:checked').val());
			}
		});
	
}

function guardar(form) {

	swal({
		title : '�Est�s seguro de que deseas Guardar?',
		text : 'Los datos se guardar� en el sistema.',
		type : 'warning',
		showCancelButton : true,
		confirmButtonText : 'Si, Guardar',
		cancelButtonText : 'No, Cancelar',
		confirmButtonClass : "btn btn-success",
		cancelButtonClass : "btn btn-danger",
		buttonsStyling : false
	}).then(function() {
		form.submit();
	}, function(dismiss) {
		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
		if (dismiss === 'cancel') {
			swal({
				title : 'Cancelado',
				text : 'No se alterar�n los datos.',
				type : 'error',
				confirmButtonClass : "btn btn-info",
				buttonsStyling : false
			});
		}
	});

}

function eliminar(url) {

	swal({
		title : '�Est�s seguro de que deseas Eliminar el registro?',
		text : 'El registro ser� eliminado del sistema.',
		type : 'warning',
		showCancelButton : true,
		confirmButtonText : 'Si, Eliminar',
		cancelButtonText : 'No, Cancelar',
		confirmButtonClass : "btn btn-success",
		cancelButtonClass : "btn btn-danger",
		buttonsStyling : false
	}).then(function() {
		window.location.replace(url);
	}, function(dismiss) {
		// dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
		if (dismiss === 'cancel') {
			swal({
				title : 'Cancelado',
				text : 'No se elimin� el registro.',
				type : 'error',
				confirmButtonClass : "btn btn-info",
				buttonsStyling : false
			});
		}
		return false;
	});

}

$('.confirma').click(function(e) {
    e.preventDefault(); // Prevent the href from redirecting directly
    var linkURL = $(this).attr("href");
    confirma(linkURL);
  });

function mensajeInfo(mensaje){	
	swal('Informaci�n',mensaje,'warning');	
}
