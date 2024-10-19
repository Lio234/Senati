$(function () {
  $(document).ready(function() {
    $(".btn_mostrar").on("click", function(e) {
        e.preventDefault();  // Evitar la acción por defecto del enlace
        const cod_prod = $(this).data("cod");

        // Cargar el contenido del archivo mostrar_producto.php
        $.ajax({
            url: 'mostrar_producto.php', // Cambia esta línea a la ubicación de tu archivo
            type: 'GET',
            data: { cod_prod: cod_prod }, // Envía el código del producto
            success: function(response) {
                $("#detalleProducto").html(response); // Inserta el contenido en el contenedor
            },
            error: function() {
                $("#detalleProducto").html('<p class="text-danger">Error al cargar los detalles del producto.</p>');
            }
        });
    });
});



// Editar producto al hacer clic en el botón de editar
$(".reg_producto .btn_editar").click(function (e) {
  e.preventDefault(); // Evita el comportamiento por defecto del botón
  
  let cod_prod = $(this)
    .closest(".reg_producto")
    .children(".cod_prod")
    .text();

  // Solo se imprime el código del producto, no se redirige
  console.log("Código del producto a editar:", cod_prod);

  // Si deseas realizar alguna acción adicional aquí, como abrir un modal o una función, puedes hacerlo.
});

  // Borrar producto al hacer clic en el botón de borrar
  $(".reg_producto .btn_borrar").click(function (e) {
    e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace

    let cod_prod = $(this)
      .closest(".reg_producto")
      .children(".cod_prod")
      .text();
    let prod = $(this).closest(".reg_producto").children(".prod").text();

    $("#md_borrar .lbl_codprod").text(cod_prod);
    $("#md_borrar .lbl_prod").text(prod);

    $("#md_borrar .btn_borrar").attr(
      "href",
      "../controller/ctr_borrar_prod.php?cod_prod=" + cod_prod
    );

    $("#md_borrar").modal("show");
  });

  // Consultar producto al perder el foco el campo de código de producto
  $("#frm_consultar_prod #txt_codprod").focusout(function (e) {
    e.preventDefault();

    let codprod = $(this).val();

    if (codprod != "") {
      $.ajax({
        url: "../controller/ctr_consultar_prod.php",
        type: "POST",
        data: { cod_prod: codprod },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".prod").html(rp.producto);
            $(".stk").html(rp.stock_disponible);
            $(".cst").html("S/" + rp.costo);
            $(".gnc").html(rp.ganancia + "%");
            $(".prc").html("S/" + rp.precio);
            $(".mar").html(rp.marca);
            $(".cat").html(rp.categoria);
          } else {
            $("#codigoErroneo").text(codprod);
            $("#modalError").modal("show");

            $("#txt_codprod").val("");

            let vacio = "&nbsp;";
            $(".prod").html(vacio);
            $(".stk").html(vacio);
            $(".cst").html(vacio);
            $(".gnc").html(vacio);
            $(".prc").html(vacio);
            $(".mar").html(vacio);
            $(".cat").html(vacio);
            $("#txt_codprod").focus();
          }
        },
      });
    }
  });

  // Filtrar productos al enviar el formulario
  $("#frm_filtrar_prod").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_prod.php", // Archivo PHP que procesará la solicitud
        type: "POST",
        data: { valor: valor }, // Enviar el valor ingresado al PHP
        success: function (response) {
          if (response.trim() === "") {
            // Mostrar el modal si no se encontraron productos
            $("#modalNoResultados").modal("show");
            $("#tabla").html("<p>No se encontraron productos.</p>"); // Mostrar mensaje si no hay resultados
          } else {
            $("#tabla").html(response); // Actualizar la sección #tabla con los resultados
          }
        },
        error: function (xhr, status, error) {
          console.log("Error:", error); // Mostrar el error en consola
          $("#tabla").html(
            "<p>Hubo un error al intentar filtrar los productos. Por favor, intenta de nuevo.</p>"
          );
        },
      });
    }
  });
});
