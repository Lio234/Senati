$(function () {
  $(document).ready(function () {
    $(".btn_mostrar_dep").on("click", function (e) {
      e.preventDefault(); // Evitar la acción por defecto del enlace
      const cod_dep = $(this).data("cod");

      // Cargar el contenido del archivo mostrar_departamento.php
      $.ajax({
        url: "mostrar_departamento.php", // Cambia esta línea a la ubicación de tu archivo
        type: "GET",
        data: { cod_dep: cod_dep }, // Envía el código del departamento
        success: function (response) {
          $("#detalleDepartamento").html(response); // Inserta el contenido en el contenedor
        },
        error: function () {
          $("#detalleDepartamento").html(
            '<p class="text-danger">Error al cargar los detalles del departamento.</p>'
          );
        },
      });
    });
  });

  let cod_dep; // Variable global para almacenar el código de la depegoría a eliminar
  let dep; // Variable para almacenar el nombre de la depegoría
  
  // Borrar departamento al hacer clic en el botón de borrar
  $(".reg_departamento .btn_borrar_dep").click(function (e) {
      e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
  
      // Obtener el código y el nombre de la depegoría
      cod_dep = $(this).closest(".reg_departamento").children(".cod_dep").text();
      dep = $(this).closest(".reg_departamento").children(".dep").text();
  
      // Llenar el modal con el nombre y código de la depegoría
      $("#md_borrar_dep .lbl_dep").text(dep);
      $("#md_borrar_dep .lbl_coddep").text(cod_dep);
  
      // Mostrar el modal de confirmación
      $("#md_borrar_dep").modal("show");
  });
  
  // Confirmar la eliminación
  $("#confirm_borrar").click(function () {
      $.ajax({
          url: "../controller/ctr_borrar_dep.php?cod_dep=" + cod_dep,
          method: "GET",
          dataType: "json",
          success: function (response) {
              if (response.status === "success") {
                  // Si la depegoría fue eliminada, recargar la página
                  location.reload();
              } else {
                  // Si hubo un error, mostrar el mensaje en el modal de error
                  $("#error_message").text(response.message); // Asigna el mensaje de error al contenedor
                  $("#md_error_dep").modal("show"); // Muestra el modal de error
              }
          },
          error: function () {
              $("#error_message").text("Ocurrió un error inesperado."); // Mensaje por defecto en caso de error
              $("#md_error_dep").modal("show"); // Muestra el modal de error
          }
      });
  
      // Cierra el modal de confirmación
      $("#md_borrar_dep").modal("hide");
  });
  

  // Consultar departamento al perder el foco el campo de código de departamento
  $("#frm_consultar_dep #txt_coddep").focusout(function (e) {
    e.preventDefault();

    let coddep = $(this).val();

    if (coddep != "") {
      $.ajax({
        url: "../controller/ctr_consultar_dep.php",
        type: "POST",
        data: { cod_dep: coddep },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".dep").html(rp.departamento);
          } else {
            $("#codigoErroneo").text(coddep);
            $("#modalError").modal("show");

            $("#txt_coddep").val("");

            let vacio = "&nbsp;";
            $(".dep").html(vacio);

            $("#txt_coddep").focus();
          }
        },
      });
    }
  });

  // Filtrar departamentos al enviar el formulario
  $("#frm_filtrar_dep").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_dep.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron departamentos
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron departamentos.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los departamentos. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
