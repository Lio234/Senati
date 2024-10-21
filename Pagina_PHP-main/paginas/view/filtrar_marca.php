<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Filtrar Marcas";
include("../includes/cabecera.php");
?>

<body>
    <?php include("../includes/menu.php"); ?>
    <div class="container mt-3">
        <header>
            <h1><i class="fas fa-filter"></i> Filtrar Marcas</h1>
            <hr />
        </header>

        <nav>
            <a href="listar_marca.php" class="btn btn-outline-secondary btn-sm">
                <i class="fas fa-arrow-circle-left"></i> Regresar
            </a>
        </nav>

        <section>
            <article>
                <div class="row justify-content-center mt-3">
                    <div class="col-md-5">
                        <!-- Inicio del Formulario -->
                        <form id="frm_filtrar_mar" name="frm_filtrar_mar" method="post">
                            <div class="input-group mb-3">
                                <span class="input-group-text" id="basic-addon1">
                                    <i class="fas fa-search"></i>
                                </span>
                                <input type="text" class="form-control" id="txt_valor" name="txt_valor" maxlength="40" placeholder="Valor a buscar..." autofocus />
                                <button class="btn btn-outline-success" type="submit" id="btn_filtrar" name="btn_filtrar">Filtrar</button>
                                <!-- Botón para reiniciar el formulario sin recargar la página -->
                                <button class="btn btn-outline-primary" type="button" id="btn_nuevo">Nuevo</button>
                            </div>
                        </form>
                        <!-- Fin del Formulario -->
                    </div>
                </div>
            </article>
        </section>

        <!-- Modal para mostrar los resultados del filtro -->
        <div class="modal fade" id="modalResultados" tabindex="-1" aria-labelledby="modalResultadosLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalResultadosLabel">Resultados de la Búsqueda</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div id="tabla"></div> <!-- Aquí se mostrará la tabla -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <?php include("../includes/pie.php"); ?>

    <!-- JavaScript para limpiar el formulario sin recargar la página -->
    <script>
        $(document).ready(function () {
            $("#btn_nuevo").click(function () {
                $("#frm_filtrar_mar")[0].reset(); // Limpia el formulario
                $("#tabla").html(''); // Limpia los resultados de la tabla
            });
        });

        // Mostrar modal con resultados al filtrar
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
                            $("#modalResultados").modal("show"); // Mostrar el modal con los resultados
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
    </script>

    <div class="modal fade" id="modalNoResultados" tabindex="-1" aria-labelledby="modalNoResultadosLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalNoResultadosLabel">Sin Resultados</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    No se encontraron marcas que coincidan con su búsqueda.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

</body>

</html>