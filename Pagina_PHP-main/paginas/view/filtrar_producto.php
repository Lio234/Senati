    <!DOCTYPE html>
    <html lang="es">
    <?php
    $ruta = "../..";
    $titulo = "Aplicación de Ventas - Filtrar Productos";
    include("../includes/cabecera.php");
    ?>

    <body>
        <?php include("../includes/menu.php"); ?>
        <div class="container mt-3">
            <header>
                <h1><i class="fas fa-filter"></i> Filtrar Productos</h1>
                <hr />
            </header>

            <nav>
                <a href="listar_producto.php" class="btn btn-outline-secondary btn-sm">
                    <i class="fas fa-arrow-circle-left"></i> Regresar
                </a>
            </nav>

            <section>
                <article>
                    <div class="row justify-content-center mt-3">
                        <div class="col-md-5">
                            <!-- Inicio del Formulario -->
                            <form id="frm_filtrar_prod" name="frm_filtrar_prod" method="post">
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

            <section class="mt-3">
                <!-- Mostrar los resultados del filtro -->
                <div id="tabla"></div>
            </section>

        </div>
        <?php include("../includes/pie.php"); ?>

        <!-- JavaScript para limpiar el formulario sin recargar la página -->
        <script>
            $(document).ready(function() {
                $("#btn_nuevo").click(function() {
                    $("#frm_filtrar_prod")[0].reset(); // Limpia el formulario
                    $("#tabla").html(''); // Limpia los resultados de la tabla
                });
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
                        No se encontraron productos que coincidan con su búsqueda.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>


    </body>

    </html>