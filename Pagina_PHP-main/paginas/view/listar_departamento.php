<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Lista de Departamentos";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    include "../includes/cargar_clases.php";

    $cruddepartamento = new CRUDDepartamento();
    $rs_dep = $cruddepartamento->ListarDepartamento();
    ?>
    <div class="container mt-3">
        <header>
            <h1>
                <i class="fas fa-list-alt"></i> Lista de Departamentos
            </h1>
            <hr />
        </header>
    </div>
    <nav>
        <div class="btn-group col-md-5" role="group">
            <a href="#" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalRegistrarDepartamento">
                <i class="fas fa-plus-circle"></i> Registrar
            </a>
            <a href="consultar_departamento.php" class="btn btn-outline-primary ">
                <i class="fas fa-search"></i> Consultar
            </a>
            <a href="filtrar_departamento.php" class="btn btn-outline-primary">
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
                            <th>Departamento</th>
                            <th colspan="3">Acciones</th>
                        </tr>
                        <?php
                        $i = 0;
                        foreach ($rs_dep as $dep) {
                            $i++;
                        ?>
                            <tr class="reg_departamento text-center">
                                <td><?= $i ?></td>
                                <td class="cod_dep"><?= $dep->id_departamento ?></td>
                                <td class="dep"><?= $dep->departamento ?></td>
                                <td><a href="#" class="btn_mostrar_dep btn btn-outline-info" data-cod="<?= $dep->id_departamento ?>" data-bs-toggle="modal" data-bs-target="#modalMostrarDepartamento"><i class="fas fa-exclamation"></i></a>
                                </td>
                                <td>
                                    <a href="#" class="btn_editar_dep btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#modalEditarDepartamento" data-cod="<?= $dep->id_departamento ?>"
                                        onclick="abrirModalEditar(<?= $dep->id_departamento ?>)">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                                <td><a href="#" class="btn_borrar_dep btn btn-outline-danger"><i
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

    <!-- Modal para Registrar Departamento -->
    <div class="modal fade" id="modalRegistrarDepartamento" tabindex="-1" aria-labelledby="modalRegistrarDepartamentoLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalRegistrarDepartamentoLabel">Registrar Departamento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de registrar_departamento.php -->
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para mostrar detalles del departamento -->
    <div class="modal fade" id="modalMostrarDepartamento" tabindex="-1" aria-labelledby="modalMostrarDepartamentoLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalMostrarDepartamentoLabel">Detalle del Departamento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de mostrar_departamento.php -->
                    <div id="detalleDepartamento">
                        <!-- Este contenedor se llenará con el contenido de mostrar_departamento.php -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Borrar Departamento -->
<div class="modal fade" id="md_borrar_dep" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-danger" id="staticBackdropLabel"><i class="fas fa-trash-alt"></i> Borrar
                    Departamento</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row justify-content-center">
                    <h5 class="card-title">¿Seguro de borrar el registro?</h5>
                    <p class="card-text">
                        <span class="lbl_dep"></span> (<span class="lbl_coddep"></span>)
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cerrar</button>
                <button type="button" class="btn_borrar_dep btn btn-outline-danger" id="confirm_borrar">Borrar</button>
            </div>
        </div>
    </div>
</div>

    <!-- Modal para mostrar errores -->
    <div class="modal fade" id="md_error_dep" tabindex="-1" aria-labelledby="md_error_dep_label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-danger" id="md_error_dep_label"><i class="fas fa-exclamation-triangle"></i> Error al Borrar</h4>
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

    <!-- Modal para Editar Departamento -->
    <div class="modal fade" id="modalEditarDepartamento" tabindex="-1" aria-labelledby="modalEditarDepartamentoLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalEditarDepartamentoLabel">Editar Departamento</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Aquí se cargará el contenido de editar_departamento.php -->
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Cargar el contenido de registrar_departamento.php solo una vez
            let modalBodyRegistrar = $('#modalRegistrarDepartamento .modal-body');
            $.ajax({
                url: 'registrar_departamento.php', // Cargar el contenido de registrar_departamento.php
                method: 'GET',
                success: function(response) {
                    modalBodyRegistrar.html(response); // Insertar el contenido en el modal
                },
                error: function() {
                    modalBodyRegistrar.html('<p class="text-danger">Error al cargar el formulario.</p>');
                }
            });

            // Cargar el contenido de editar_departamento.php al abrir el modal
            $('#modalEditarDepartamento').on('show.bs.modal', function(event) {
                let button = $(event.relatedTarget); // Botón que activó el modal
                let codDep = button.data('cod'); // Extraer la información del atributo data-cod

                let modalBodyEditar = $(this).find('.modal-body');
                $.ajax({
                    url: 'editar_departamento.php?cod_dep=' + codDep, // Cargar el contenido de editar_departamento.php
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