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

    $crudproducto = new CRUDProducto();
    $rs_prod = $crudproducto->ListarProducto();
    ?>
    <div class="container mt-3">
        <header>
            <h1>
                <i class="fas fa-list-alt"></i> Lista de Productos
            </h1>
            <hr />
        </header>
    </div>
    <nav>
        <div class="btn-group col-md-5" role="group">
            <a href="#" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalRegistrarProducto">
                <i class="fas fa-plus-circle"></i> Registrar
            </a>
            <a href="consultar_producto.php" class="btn btn-outline-primary ">
                <i class="fas fa-search"></i> Consultar
            </a>
            <a href="filtrar_producto.php" class="btn btn-outline-primary">
                <i class="fas fa-search"></i> Filtrar
            </a>
        </div>
    </nav>
    <section>
        <article>
            <div class="row justify-content-center mt-3">
                <div class="col-md-8">
                    <table class="table table-hover table-sm table-success">
                        <tr class="table-primary text-center">
                            <th>N°</th>
                            <th>Código</th>
                            <th>Producto</th>
                            <th>Stock Disponible</th>
                            <th colspan="3">Acciones</th>
                        </tr>
                        <?php
                        $i = 0;
                        foreach ($rs_prod as $prod) {
                            $i++;
                            ?>
                            <tr class="reg_producto">
                                <td><?= $i ?></td>
                                <td class="cod_prod"><?= $prod->id_producto ?></td>
                                <td class="prod"><?= $prod->producto ?></td>
                                <td><?= $prod->stock_disponible ?></td>
                                <td><a href="#" class="btn_mostrar btn btn-outline-info"><i
                                            class="fas fa-exclamation"></i></a></td>
                                <td>
                                    <a href="#" class="btn_editar btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#modalEditarProducto" data-cod="<?= $prod->id_producto ?>"
                                        onclick="abrirModalEditar(<?= $prod->id_producto ?>)">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                                <td><a href="#" class="btn_borrar btn btn-outline-danger"><i
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
</body>

<!-- Modal para Borrar Producto -->
<div class="modal fade" id="md_borrar" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-danger" id="staticBackdropLabel"><i class="fas fa-trash-alt"></i> Borrar
                    Producto</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row justify-content-center">
                    <h5 class="card-title">¿Seguro de borrar el registro?</h5>
                    <p class="card-text">
                        <span class="lbl_prod"></span> (<span class="lbl_codprod"></span>)
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                <a href="#" class="btn_borrar btn btn-outline-danger">Borrar</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal para Registrar Producto -->
<div class="modal fade" id="modalRegistrarProducto" tabindex="-1" aria-labelledby="modalRegistrarProductoLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalRegistrarProductoLabel">Registrar Producto</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Aquí se cargará el contenido de registrar_producto.php -->
            </div>
        </div>
    </div>
</div>

<!-- Modal para Editar Producto -->
<div class="modal fade" id="modalEditarProducto" tabindex="-1" aria-labelledby="modalEditarProductoLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalEditarProductoLabel">Editar Producto</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Aquí se cargará el contenido de editar_producto.php -->
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Cargar el contenido de registrar_producto.php solo una vez
        let modalBodyRegistrar = $('#modalRegistrarProducto .modal-body');
        $.ajax({
            url: 'registrar_producto.php', // Cargar el contenido de registrar_producto.php
            method: 'GET',
            success: function (response) {
                modalBodyRegistrar.html(response); // Insertar el contenido en el modal
            },
            error: function () {
                modalBodyRegistrar.html('<p class="text-danger">Error al cargar el formulario.</p>');
            }
        });

        // Cargar el contenido de editar_producto.php al abrir el modal
        $('#modalEditarProducto').on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Botón que activó el modal
            let codProd = button.data('cod'); // Extraer la información del atributo data-cod

            let modalBodyEditar = $(this).find('.modal-body');
            $.ajax({
                url: 'editar_producto.php?cod_prod=' + codProd, // Cargar el contenido de editar_producto.php
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

</html>