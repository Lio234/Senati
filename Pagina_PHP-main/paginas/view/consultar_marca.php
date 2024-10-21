<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de ventas - Consultar Marca";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    ?>

    <div class="container mt-3">
        <header>
            <h1><i class="fas fa-search"></i>Consultar Marca</h1>
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
                    <div class="card col-md-6">
                        <div class="card-body">
                            <form id="frm_consultar_mar" name="frm_consultar_mar" method="post">
                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <label for="txt_codmar" class="form-label">Codigo</label>
                                        <input type="text" class="form-control" id="txt_codmar" name="txt_codmar"
                                            placeholder="codigo buscar" maxlength="5" autofocus />
                                    </div>

                                    <div class="col-md-8"></div>

                                    <div class="col-md-8">
                                        <h5 class="card-title">Marca</h5>
                                        <p class="mar card-text">&nbsp;</p>
                                    </div>
                                </div>
                            </form>
                            <div class="text-center mt-3">
                                <a href="consultar_marca.php" class="btn btn-outline-primary">
                                    <i class="fas fa-file"></i> Nueva Consulta
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </article>
        </section>
        <?php
        include("../includes/pie.php")
        ?>
        <!-- Modal -->
        <div class="modal fade" id="modalError" tabindex="-1" aria-labelledby="modalErrorLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered"> <!-- Clase agregada para centrar verticalmente -->
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalErrorLabel">Error</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        El código <span id="codigoErroneo"></span> no existe.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>