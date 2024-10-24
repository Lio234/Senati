<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Lista de Marcas";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    include "../includes/cargar_clases.php";

    $crudmarca = new CRUDMarca();
    $rs_mar = $crudmarca->ListarMarca();
    ?>
    <div class="container mt-3">
        <header>
            <h1>
                <i class="fas fa-list-alt"></i> Lista de Marcas
            </h1>
            <hr />
        </header>
    </div>
    <nav>
        <div class="btn-group col-md-5" role="group">
            <a href="#" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalRegistrarMarca">
                <i class="fas fa-plus-circle"></i> Registrar
            </a>
            <a href="consultar_marca.php" class="btn btn-outline-primary ">
                <i class="fas fa-search"></i> Consultar
            </a>
            <a href="filtrar_marca.php" class="btn btn-outline-primary">
                <i class="fas fa-search"></i> Filtrar
            </a>
        </div>
    </nav>
    <section>
        <article>
            <div class="row justify-content-center mt-3">
                <div class="col-md-8">
                    <table class="table table-hover table-sm ">
                        <tr class="table-primary text-center">
                            <th>N°</th>
                            <th>Código</th>
                            <th>Marca</th>
                            <th colspan="3">Acciones</th>
                        </tr>
                        <?php
                        $i = 0;
                        foreach ($rs_mar as $mar) {
                            $i++;
                        ?>
                            <tr class="reg_marca text-center">
                                <td><?= $i ?></td>
                                <td class="cod_mar"><?= $mar->id_marca ?></td>
                                <td class="mar"><?= $mar->marca ?></td>
                                <td><a href="#" class="btn_mostrar_mar btn btn-outline-info" data-cod="<?= $mar->id_marca ?>" data-bs-toggle="modal" data-bs-target="#modalMostrarMarca"><i class="fas fa-exclamation"></i></a>
                                </td>
                                <td>
                                    <a href="#" class="btn_editar_mar btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#modalEditarMarca" data-cod="<?= $mar->id_marca ?>"
                                        onclick="abrirModalEditar(<?= $mar->id_marca ?>)">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                                <td><a href="#" class="btn_borrar_mar btn btn-outline-danger"><i
                                            class="fas fa-trash-alt"></i></a></td>
                            </tr>
                        <?php
                        }
                        ?>
                    </table>
                </div>
            </div>
        </article>
    </section>
    <?php
    include("../includes/pie.php");
    ?>

    <!-- Modal para Registrar Marca -->
    <div class="modal fade" id="modalRegistrarMarca" tabindex="-1" aria-labelledby="modalRegistrarMarcaLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalRegistrarMarcaLabel">Registrar Marca</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de registrar_marca.php -->
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para mostrar detalles del marca -->
    <div class="modal fade" id="modalMostrarMarca" tabindex="-1" aria-labelledby="modalMostrarMarcaLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalMostrarMarcaLabel">Detalle del Marca</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de mostrar_marca.php -->
                    <div id="detalleMarca">
                        <!-- Este contenedor se llenará con el contenido de mostrar_marca.php -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Borrar Marca -->
<div class="modal fade" id="md_borrar_mar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-danger" id="staticBackdropLabel"><i class="fas fa-trash-alt"></i> Borrar
                    Marca</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row justify-content-center">
                    <h5 class="card-title">¿Seguro de borrar el registro?</h5>
                    <p class="card-text">
                        <span class="lbl_mar"></span> (<span class="lbl_codmar"></span>)
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn_borrar_mar btn btn-outline-danger" id="confirm_borrar">Borrar</button>
            </div>
        </div>
    </div>
</div>

    <!-- Modal para mostrar errores -->
    <div class="modal fade" id="md_error_mar" tabindex="-1" aria-labelledby="md_error_mar_label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-danger" id="md_error_mar_label"><i class="fas fa-exclamation-triangle"></i> Error al Borrar</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Este contenedor se llenará con el mensaje de error -->
                    <p id="error_message"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>





    <!-- Modal para Editar Marca -->
    <div class="modal fade" id="modalEditarMarca" tabindex="-1" aria-labelledby="modalEditarMarcaLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEditarMarcaLabel">Editar Marca</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de editar_marca.php -->
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Cargar el contenido de registrar_marca.php solo una vez
            let modalBodyRegistrar = $('#modalRegistrarMarca .modal-body');
            $.ajax({
                url: 'registrar_marca.php', // Cargar el contenido de registrar_marca.php
                method: 'GET',
                success: function(response) {
                    modalBodyRegistrar.html(response); // Insertar el contenido en el modal
                },
                error: function() {
                    modalBodyRegistrar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                }
            });

            // Cargar el contenido de editar_marca.php al abrir el modal
            $('#modalEditarMarca').on('show.bs.modal', function(event) {
                let button = $(event.relatedTarget); // Botón que activó el modal
                let codMar = button.data('cod'); // Extraer la información del atributo data-cod

                let modalBodyEditar = $(this).find('.modal-body');
                $.ajax({
                    url: 'editar_marca.php?cod_mar=' + codMar, // Cargar el contenido de editar_marca.php
                    method: 'GET',
                    success: function(response) {
                        modalBodyEditar.html(response); // Insertar el contenido en el modal
                    },
                    error: function() {
                        modalBodyEditar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                    }
                });
            });
        });
    </script>

</body>

</html>