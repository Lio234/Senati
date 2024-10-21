$(function () {
  $(document).ready(function () {
    $(".btn_mostrar_mar").on("click", function (e) {
      e.preventDefault(); // Evitar la acción por defecto del enlace
      const cod_mar = $(this).data("cod");

      // Cargar el contenido del archivo mostrar_marca.php
      $.ajax({
        url: "mostrar_marca.php", // Cambia esta línea a la ubicación de tu archivo
        type: "GET",
        data: { cod_mar: cod_mar }, // Envía el código del marca
        success: function (response) {
          $("#detalleMarca").html(response); // Inserta el contenido en el contenedor
        },
        error: function () {
          $("#detalleMarca").html(
            '<p class="text-danger">Error al cargar los detalles del marca.</p>'
          );
        },
      });
    });
  });

  let cod_mar; // Variable global para almacenar el código de la maregoría a eliminar
  let mar; // Variable para almacenar el nombre de la maregoría
  
  // Borrar marca al hacer clic en el botón de borrar
  $(".reg_marca .btn_borrar_mar").click(function (e) {
      e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
  
      // Obtener el código y el nombre de la maregoría
      cod_mar = $(this).closest(".reg_marca").children(".cod_mar").text();
      mar = $(this).closest(".reg_marca").children(".mar").text();
  
      // Llenar el modal con el nombre y código de la maregoría
      $("#md_borrar_mar .lbl_mar").text(mar);
      $("#md_borrar_mar .lbl_codmar").text(cod_mar);
  
      // Mostrar el modal de confirmación
      $("#md_borrar_mar").modal("show");
  });
  
  // Confirmar la eliminación
  $("#confirm_borrar").click(function () {
      $.ajax({
          url: "../controller/ctr_borrar_mar.php?cod_mar=" + cod_mar,
          method: "GET",
          dataType: "json",
          success: function (response) {
              if (response.status === "success") {
                  // Si la maregoría fue eliminada, recargar la página
                  lomarion.reload();
              } else {
                  // Si hubo un error, mostrar el mensaje en el modal de error
                  $("#error_message").text(response.message); // Asigna el mensaje de error al contenedor
                  $("#md_error_mar").modal("show"); // Muestra el modal de error
              }
          },
          error: function () {
              $("#error_message").text("Ocurrió un error inesperado."); // Mensaje por defecto en caso de error
              $("#md_error_mar").modal("show"); // Muestra el modal de error
          }
      });
  
      // Cierra el modal de confirmación
      $("#md_borrar_mar").modal("hide");
  });
  

    // Consultar marca al perder el foco el campo de código de marca
    $("#frm_consultar_mar #txt_codmar").focusout(function (e) {
      e.preventDefault();

      let codmar = $(this).val();

      if (codmar != "") {
        $.ajax({
          url: "../controller/ctr_consultar_mar.php",
          type: "POST",
          data: { cod_mar: codmar },
          success: function (rpta) {
            let rp = JSON.parse(rpta);
            console.log("Respuesta:", rpta);

            if (rp) {
              $(".mar").html(rp.marca);
            } else {
              $("#codigoErroneo").text(codmar);
              $("#modalError").modal("show");

              $("#txt_codmar").val("");

              let vacio = "&nbsp;";
              $(".mar").html(vacio);

              $("#txt_codmar").focus();
            }
          },
        });
      }
    });

  // Filtrar marcas al enviar el formulario
  $("#frm_filtrar_mar").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_mar.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron marcas
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron marcas.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los marcas. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
