$(function () {
  $(document).ready(function () {
    $(".btn_mostrar_pro").on("click", function (e) {
      e.preventDefault(); // Evitar la acción por defecto del enlace
      const cod_pro = $(this).data("cod");

      // Cargar el contenido del archivo mostrar_provincia.php
      $.ajax({
        url: "mostrar_provincia.php", // Cambia esta línea a la ubicación de tu archivo
        type: "GET",
        data: { cod_pro: cod_pro }, // Envía el código del provincia
        success: function (response) {
          $("#detalleProvincia").html(response); // Inserta el contenido en el contenedor
        },
        error: function () {
          $("#detalleProvincia").html(
            '<p class="text-danger">Error al cargar los detalles del provincia.</p>'
          );
        },
      });
    });
  });

  let cod_pro; // Variable global para almacenar el código de la provincia a eliminar
  let pro; // Variable para almacenar el nombre de la provincia
  
  // Borrar provincia al hacer clic en el botón de borrar
  $(".reg_provincia .btn_borrar_pro").click(function (e) {
      e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
  
      // Obtener el código y el nombre de la provincia
      cod_pro = $(this).closest(".reg_provincia").children(".cod_pro").text();
      pro = $(this).closest(".reg_provincia").children(".pro").text();
  
      // Llenar el modal con el nombre y código de la provincia
      $("#md_borrar_pro .lbl_pro").text(pro);
      $("#md_borrar_pro .lbl_codpro").text(cod_pro);
  
      // Mostrar el modal de confirmación
      $("#md_borrar_pro").modal("show");
  });
  
  // Confirmar la eliminación
  $("#confirm_borrar").click(function () {
      $.ajax({
          url: "../controller/ctr_borrar_pro.php?cod_pro=" + cod_pro,
          method: "GET",
          dataType: "json",
          success: function (response) {
              if (response.status === "success") {
                  // Si la provincia fue eliminada, recargar la página
                  location.reload();
              } else {
                  // Si hubo un error, mostrar el mensaje en el modal de error
                  $("#error_message").text(response.message); // Asigna el mensaje de error al contenedor
                  $("#md_error_pro").modal("show"); // Muestra el modal de error
              }
          },
          error: function () {
              $("#error_message").text("Ocurrió un error inesperado."); // Mensaje por defecto en caso de error
              $("#md_error_pro").modal("show"); // Muestra el modal de error
          }
      });
  
      // Cierra el modal de confirmación
      $("#md_borrar_pro").modal("hide");
  });
  

  // Consultar provincia al perder el foco el campo de código de provincia
  $("#frm_consultar_pro #txt_codpro").focusout(function (e) {
    e.preventDefault();

    let codpro = $(this).val();

    if (codpro != "") {
      $.ajax({
        url: "../controller/ctr_consultar_pro.php",
        type: "POST",
        data: { cod_pro: codpro },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".pro").html(rp.provincia);
          } else {
            $("#codigoErroneo").text(codpro);
            $("#modalError").modal("show");

            $("#txt_codpro").val("");

            let vacio = "&nbsp;";
            $(".pro").html(vacio);

            $("#txt_codpro").focus();
          }
        },
      });
    }
  });

  // Filtrar provincias al enviar el formulario
  $("#frm_filtrar_pro").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_pro.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron provincias
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron provincias.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los provincias. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
