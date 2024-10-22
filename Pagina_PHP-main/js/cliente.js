$(function () {
  $(document).ready(function () {
    // Cuando el modal para mostrar el cliente se muestra
    $('.btn_mostrar_clien').click(function () {
      let codCliente = $(this).data('cod'); // Obtener el código del cliente desde el atributo data-cod

      // Hacer una solicitud AJAX para obtener los detalles del cliente
      $.ajax({
        url: 'mostrar_cliente.php', // Archivo que procesará el ID y devolverá los detalles del cliente
        method: 'GET',
        data: { id_cliente: codCliente }, // Enviar el código del cliente
        success: function (response) {
          // Cargar el contenido en el modal
          $('#detalleCliente').html(response);
        },
        error: function () {
          $('#detalleCliente').html('<p class="text-danger">Error al cargar los detalles del cliente.</p>');
        }
      });
    });
  });



  // Editar producto al hacer clic en el botón de editar
  $(".reg_cliente .btn_editar_clien").click(function (e) {
    e.preventDefault(); // Evita el comportamiento por defecto del botón

    // Buscar el id_cliente correctamente dentro del contenedor .reg_cliente
    let id_cliente = $(this)
      .closest(".reg_cliente")
      .find(".id_cliente") // Utilizamos .find() si id_cliente no es un hijo directo
      .text();

    // Imprime el ID del cliente a editar, no el código del producto
    console.log("ID del cliente a editar:", id_cliente);

    // Aquí puedes añadir cualquier acción adicional, como abrir un modal o realizar una redirección.
  });

  // Borrar cliente al hacer clic en el botón de borrar
  $(".reg_cliente .btn_borrar_clien").click(function (e) {
    e.preventDefault(); // Prevenir el comportamiento predeterminado del enlace

    let id_cliente = $(this)
      .closest(".reg_cliente")
      .children(".id_cliente")
      .text();
    let nom = $(this).closest(".reg_cliente").children(".nom").text();

    // Asignar los valores al modal
    $("#md_borrar_clien .lbl_id_cliente").text(id_cliente);
    $("#md_borrar_clien .lbl_nom").text(nom);

    // Configurar el enlace para borrar el cliente
    $("#md_borrar_clien .btn_borrar_prod").attr(
      "href",
      "../controller/ctr_borrar_clien.php?id_cliente=" + id_cliente
    );

    // Mostrar el modal
    $("#md_borrar_clien").modal("show");
  });

  // Consultar producto al perder el foco el campo de código de producto
  $("#frm_consultar_clien #txt_id_cliente").focusout(function (e) {
    e.preventDefault();

    let id_cliente = $(this).val();

    if (id_cliente!= "") {
      $.ajax({
        url: "../controller/ctr_consultar_cliente.php",
        type: "POST",
        data: { id_cliente: id_cliente },
        success: function (rpta) {
          let rp = JSON.parse(rpta);
          console.log("Respuesta:", rpta);

          if (rp) {
            $(".nom").html(rp.nombre);
            $(".a_pat").html(rp.ap_paterno);
            $(".a_mat").html(rp.ap_materno);
            $(".direc").html(rp.direccion);
            $(".cor").html(rp.correo);
            $(".tel").html(rp.telefono);
            $(".id_dist").html(rp.distrito);
          } else {
            $("#codigoErroneo").text(id_cliente);
            $("#modalError").modal("show");

            $("#txt_id_cliente").val("");

            let vacio = "&nbsp;";
            $(".nom").html(vacio);
            $(".a_pat").html(vacio);
            $(".a_mat").html(vacio);
            $(".direc").html(vacio);
            $(".cor").html(vacio);
            $(".tel").html(vacio);
            $(".id_dist").html(vacio);
            $("#txt_id_cliente").focus();
          }
        },
      });
    }
  });

  // Filtrar productos al enviar el formulario
  $("#frm_filtrar_clien").submit(function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe normalmente

    let valor = $("#txt_valor").val(); // Obtener el valor del input

    if (valor != "") {
      $.ajax({
        url: "../controller/ctr_filtrar_clien.php", // Archivo PHP que procesará la solicitud
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
