<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Lista de Categorias";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    include "../includes/cargar_clases.php";

    $crudcategoria = new CRUDCategoria();
    $rs_cat = $crudcategoria->ListarCategoria();
    ?>
    <div class="container mt-3">
        <header>
            <h1>
                <i class="fas fa-list-alt"></i> Lista de Categorias
            </h1>
            <hr />
        </header>
    </div>
    <nav>
        <div class="btn-group col-md-5" role="group">
            <a href="#" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalRegistrarCategoria">
                <i class="fas fa-plus-circle"></i> Registrar
            </a>
            <a href="consultar_categoria.php" class="btn btn-outline-primary ">
                <i class="fas fa-search"></i> Consultar
            </a>
            <a href="filtrar_categoria.php" class="btn btn-outline-primary">
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
                            <th>Categoria</th>
                            <th colspan="3">Acciones</th>
                        </tr>
                        <?php
                        $i = 0;
                        foreach ($rs_cat as $cat) {
                            $i++;
                        ?>
                            <tr class="reg_categoria text-center">
                                <td><?= $i ?></td>
                                <td class="cod_cat"><?= $cat->id_categoria ?></td>
                                <td class="cat"><?= $cat->categoria ?></td>
                                <td><a href="#" class="btn_mostrar_cat btn btn-outline-info" data-cod="<?= $cat->id_categoria ?>" data-bs-toggle="modal" data-bs-target="#modalMostrarCategoria"><i class="fas fa-exclamation"></i></a>
                                </td>
                                <td>
                                    <a href="#" class="btn_editar_cat btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#modalEditarCategoria" data-cod="<?= $cat->id_categoria ?>"
                                        onclick="abrirModalEditar(<?= $cat->id_categoria ?>)">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                                <td><a href="#" class="btn_borrar_cat btn btn-outline-danger"><i
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

    <!-- Modal para Registrar Categoria -->
    <div class="modal fade" id="modalRegistrarCategoria" tabindex="-1" aria-labelledby="modalRegistrarCategoriaLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalRegistrarCategoriaLabel">Registrar Categoria</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de registrar_categoria.php -->
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para mostrar detalles del categoria -->
    <div class="modal fade" id="modalMostrarCategoria" tabindex="-1" aria-labelledby="modalMostrarCategoriaLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalMostrarCategoriaLabel">Detalle del Categoria</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de mostrar_categoria.php -->
                    <div id="detalleCategoria">
                        <!-- Este contenedor se llenará con el contenido de mostrar_categoria.php -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Borrar Categoria -->
<div class="modal fade" id="md_borrar_cat" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-danger" id="staticBackdropLabel"><i class="fas fa-trash-alt"></i> Borrar
                    Categoria</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row justify-content-center">
                    <h5 class="card-title">¿Seguro de borrar el registro?</h5>
                    <p class="card-text">
                        <span class="lbl_cat"></span> (<span class="lbl_codcat"></span>)
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn_borrar_cat btn btn-outline-danger" id="confirm_borrar">Borrar</button>
            </div>
        </div>
    </div>
</div>

    <!-- Modal para mostrar errores -->
    <div class="modal fade" id="md_error_cat" tabindex="-1" aria-labelledby="md_error_cat_label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-danger" id="md_error_cat_label"><i class="fas fa-exclamation-triangle"></i> Error al Borrar</h4>
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





    <!-- Modal para Editar Categoria -->
    <div class="modal fade" id="modalEditarCategoria" tabindex="-1" aria-labelledby="modalEditarCategoriaLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEditarCategoriaLabel">Editar Categoria</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de editar_categoria.php -->
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Cargar el contenido de registrar_categoria.php solo una vez
            let modalBodyRegistrar = $('#modalRegistrarCategoria .modal-body');
            $.ajax({
                url: 'registrar_categoria.php', // Cargar el contenido de registrar_categoria.php
                method: 'GET',
                success: function(response) {
                    modalBodyRegistrar.html(response); // Insertar el contenido en el modal
                },
                error: function() {
                    modalBodyRegistrar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                }
            });

            // Cargar el contenido de editar_categoria.php al abrir el modal
            $('#modalEditarCategoria').on('show.bs.modal', function(event) {
                let button = $(event.relatedTarget); // Botón que activó el modal
                let codCat = button.data('cod'); // Extraer la información del atributo data-cod

                let modalBodyEditar = $(this).find('.modal-body');
                $.ajax({
                    url: 'editar_categoria.php?cod_cat=' + codCat, // Cargar el contenido de editar_categoria.php
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