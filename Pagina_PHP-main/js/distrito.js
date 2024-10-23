$(function () {
  $(document).ready(function () {
    $(".btn_mostrar_dis").on("click", function (e) {
      e.preventDefault(); // Evitar la acción por defecto del enlace
      const cod_dis = $(this).data("cod");

      // Cargar el contenido del archivo mostrar_distrito.php
      $.ajax({
        url: "mostrar_distrito.php", // Cambia esta línea a la ubicación de tu archivo
        type: "GET",
        data: { cod_dis: cod_dis }, // Envía el código del distrito
        success: function (response) {
          $("#detalleDistrito").html(response); // Inserta el contenido en el contenedor
        },
        error: function () {
          $("#detalleDistrito").html(
            '<p class="text-danger">Error al cargar los detalles del distrito.</p>'
          );
        },
      });
    });
  });

  let cod_dis; // Variable global para almacenar el código de la distrito a eliminar
  let dis; // Variable para almacenar el nombre de la distrito
  
  // Borrar distrito al hacer clic en el botón de borrar
  $(".reg_distrito .btn_borrar_dis").click(function (e) {
      e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
  
      // Obtener el código y el nombre de la distrito
      cod_dis = $(this).closest(".reg_distrito").children(".cod_dis").text();
      dis = $(this).closest(".reg_distrito").children(".dis").text();
  
      // Llenar el modal con el nombre y código de la distrito
      $("#md_borrar_dis .lbl_dis").text(dis);
      $("#md_borrar_dis .lbl_coddis").text(cod_dis);
  
      // Mostrar el modal de confirmación
      $("#md_borrar_dis").modal("show");
  });
  
  // Confirmar la eliminación
  $("#confirm_borrar").click(function () {
      $.ajax({
          url: "../controller/ctr_borrar_dis.php?cod_dis=" + cod_dis,
          method: "GET",
          dataType: "json",
          success: function (response) {
              if (response.status === "success") {
                  // Si la distrito fue eliminada, recargar la página
                  location.reload();
              } else {
                  // Si hubo un error, mostrar el mensaje en el modal de error
                  $("#error_message").text(response.message); // Asigna el mensaje de error al contenedor
                  $("#md_error_dis").modal("show"); // Muestra el modal de error
              }
          },
          error: function () {
              $("#error_message").text("Ocurrió un error inesperado."); // Mensaje por defecto en caso de error
              $("#md_error_dis").modal("show"); // Muestra el modal de error
          }
      });
  
      // Cierra el modal de confirmación
      $("#md_borrar_dis").modal("hide");
  });
  

  // Consultar distrito al perder el foco el campo de código de distrito
  $("#frm_consultar_dis #txt_coddis").focusout(function (e) {
    e.preventDefault();

    let coddis = $(this).val();

    if (coddis != "") {
      $.ajax({
        url: "../controller/ctr_consultar_dis.php",
        type: "POST",
        data: { cod_dis: coddis },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".dis").html(rp.distrito);
          } else {
            $("#codigoErroneo").text(coddis);
            $("#modalError").modal("show");

            $("#txt_coddis").val("");

            let vacio = "&nbsp;";
            $(".dis").html(vacio);

            $("#txt_coddis").focus();
          }
        },
      });
    }
  });

  // Filtrar distritos al enviar el formulario
  $("#frm_filtrar_dis").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_dis.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron distritos
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron distritos.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los distritos. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
