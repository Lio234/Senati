<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Lista de Productos";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    include "../includes/cargar_clases.php";

    $crudcliente = new CRUDCliente();
    $rs_clien = $crudcliente->ListarCliente();
    ?>
    <div class="container mt-3">
        <header>
            <h1>
                <i class="fas fa-list-alt"></i> Lista de Clientes
            </h1>
            <hr />
        </header>
    </div>
    <nav>
        <div class="btn-group col-md-5" role="group">
            <a href="#" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalRegistrarCliente">
                <i class="fas fa-plus-circle"></i> Registrar
            </a>
            <a href="consultar_cliente.php" class="btn btn-outline-primary">
                <i class="fas fa-search"></i> Consultar
            </a>
            <a href="filtrar_cliente.php" class="btn btn-outline-primary">
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
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th colspan="3">Acciones</th>
                        </tr>
                        <?php
                        $i = 0;
                        foreach ($rs_clien as $clien) {
                            $i++;
                            ?>
                            <tr class="reg_cliente text-center">
                                <td><?= $i ?></td>
                                <td class="id_cliente"><?= $clien->id_cliente ?></td>
                                <td class="nom"><?= $clien->nombre ?></td>
                                <td><?= $clien->ap_paterno ?></td>
                                <td>
                                    <a href="#" class="btn_mostrar_clien btn btn-outline-info"
                                        data-cod="<?= $clien->id_cliente ?>" data-bs-toggle="modal"
                                        data-bs-target="#modalMostrarCliente">
                                        <i class="fas fa-exclamation"></i>
                                    </a>
                                </td>
                                <td>
                                    <a href="#" class="btn_editar_clien btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#modalEditarCliente" data-cod="<?= $clien->id_cliente ?>">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                                <td><a href="#" class="btn_borrar_clien btn btn-outline-danger"><i
                                class="fas fa-trash-alt"></i></a></td>
                                    </a>
                                </td>
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

    <!-- Modal para Registrar Producto -->
    <div class="modal fade" id="modalRegistrarCliente" tabindex="-1" aria-labelledby="modalRegistrarClienteLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalRegistrarClienteLabel">Registrar Cliente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de registrar_producto.php -->
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para mostrar detalles del producto -->
    <div class="modal fade" id="modalMostrarCliente" tabindex="-1" aria-labelledby="modalMostrarClienteLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalMostrarClienteLabel">Detalle del Cliente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de mostrar_producto.php -->
                    <div id="detalleCliente">
                        <!-- Este contenedor se llenará con el contenido de mostrar_producto.php -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal para Borrar Cliente -->
    <div class="modal fade" id="md_borrar_clien" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-danger" id="staticBackdropLabel"><i class="fas fa-trash-alt"></i> Borrar
                        Cliente</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row justify-content-center">
                        <h5 class="card-title">¿Seguro de borrar el registro?</h5>
                        <p class="card-text">
                            <span class="lbl_nom"></span> (<span class="lbl_id_cliente"></span>)
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <a href="#" class="btn_borrar_prod btn btn-outline-danger">Borrar</a>
                </div>
            </div>
        </div>
    </div>



    <!-- Modal para Editar Cliente -->
    <div class="modal fade" id="modalEditarCliente" tabindex="-1" aria-labelledby="modalEditarClienteLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEditarClienteLabel">Editar Cliente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de editar_cliente.php -->
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Cargar el contenido de registrar_cliente.php solo una vez
            let modalBodyRegistrar = $('#modalRegistrarCliente .modal-body');
            $.ajax({
                url: 'registrar_cliente.php',
                method: 'GET',
                success: function (response) {
                    modalBodyRegistrar.html(response); // Insertar el contenido en el modal
                },
                error: function () {
                    modalBodyRegistrar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                }
            });

            // Cargar el contenido de editar_cliente.php al abrir el modal
            $('#modalEditarCliente').on('show.bs.modal', function (event) {
                let button = $(event.relatedTarget); // Botón que activó el modal
                let id_cliente = button.data('cod'); // Extraer la información del atributo data-cod

                let modalBodyEditar = $(this).find('.modal-body');
                $.ajax({
                    url: 'editar_cliente.php?id_cliente=' + id_cliente,
                    method: 'GET',
                    success: function (response) {
                        modalBodyEditar.html(response); // Insertar el contenido en el modal
                    },
                    error: function () {
                        modalBodyEditar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                    }
                });
            });
        });
    </script>

</body>

</html>