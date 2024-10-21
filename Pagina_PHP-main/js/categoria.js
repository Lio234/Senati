$(function () {
  $(document).ready(function () {
    $(".btn_mostrar_cat").on("click", function (e) {
      e.preventDefault(); // Evitar la acción por defecto del enlace
      const cod_cat = $(this).data("cod");

      // Cargar el contenido del archivo mostrar_categoria.php
      $.ajax({
        url: "mostrar_categoria.php", // Cambia esta línea a la ubicación de tu archivo
        type: "GET",
        data: { cod_cat: cod_cat }, // Envía el código del categoria
        success: function (response) {
          $("#detalleCategoria").html(response); // Inserta el contenido en el contenedor
        },
        error: function () {
          $("#detalleCategoria").html(
            '<p class="text-danger">Error al cargar los detalles del categoria.</p>'
          );
        },
      });
    });
  });

  let cod_cat; // Variable global para almacenar el código de la categoría a eliminar
  let cat; // Variable para almacenar el nombre de la categoría
  
  // Borrar categoria al hacer clic en el botón de borrar
  $(".reg_categoria .btn_borrar_cat").click(function (e) {
      e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace
  
      // Obtener el código y el nombre de la categoría
      cod_cat = $(this).closest(".reg_categoria").children(".cod_cat").text();
      cat = $(this).closest(".reg_categoria").children(".cat").text();
  
      // Llenar el modal con el nombre y código de la categoría
      $("#md_borrar_cat .lbl_cat").text(cat);
      $("#md_borrar_cat .lbl_codcat").text(cod_cat);
  
      // Mostrar el modal de confirmación
      $("#md_borrar_cat").modal("show");
  });
  
  // Confirmar la eliminación
  $("#confirm_borrar").click(function () {
      $.ajax({
          url: "../controller/ctr_borrar_cat.php?cod_cat=" + cod_cat,
          method: "GET",
          dataType: "json",
          success: function (response) {
              if (response.status === "success") {
                  // Si la categoría fue eliminada, recargar la página
                  location.reload();
              } else {
                  // Si hubo un error, mostrar el mensaje en el modal de error
                  $("#error_message").text(response.message); // Asigna el mensaje de error al contenedor
                  $("#md_error_cat").modal("show"); // Muestra el modal de error
              }
          },
          error: function () {
              $("#error_message").text("Ocurrió un error inesperado."); // Mensaje por defecto en caso de error
              $("#md_error_cat").modal("show"); // Muestra el modal de error
          }
      });
  
      // Cierra el modal de confirmación
      $("#md_borrar_cat").modal("hide");
  });
  

  // Consultar categoria al perder el foco el campo de código de categoria
  $("#frm_consultar_cat #txt_codcat").focusout(function (e) {
    e.preventDefault();

    let codcat = $(this).val();

    if (codcat != "") {
      $.ajax({
        url: "../controller/ctr_consultar_cat.php",
        type: "POST",
        data: { cod_cat: codcat },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".cat").html(rp.categoria);
          } else {
            $("#codigoErroneo").text(codcat);
            $("#modalError").modal("show");

            $("#txt_codcat").val("");

            let vacio = "&nbsp;";
            $(".cat").html(vacio);

            $("#txt_codcat").focus();
          }
        },
      });
    }
  });

  // Filtrar categorias al enviar el formulario
  $("#frm_filtrar_cat").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_cat.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron categorias
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron categorias.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los categorias. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
