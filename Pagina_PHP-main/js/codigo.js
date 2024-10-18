$(function () {
  // Mostrar producto al hacer clic en el botón de mostrar
  $(".reg_producto .btn_mostrar").click(function (e) {
    let cod_prod = $(this)
      .closest(".reg_producto")
      .children(".cod_prod")
      .text();

    location.href = "mostrar_producto.php?cod_prod=" + cod_prod;
    console.log("Código del producto: ", cod_prod);
  });

  // Editar producto al hacer clic en el botón de editar
  $(".reg_producto .btn_editar").click(function (e) {
    let cod_prod = $(this)
      .closest(".reg_producto")
      .children(".cod_prod")
      .text();

    location.href = "editar_producto.php?cod_prod=" + cod_prod;
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
