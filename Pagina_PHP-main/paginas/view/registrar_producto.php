<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Registrar Producto";
include("../includes/cabecera.php");
?>

<body>
    <?php
    include("../includes/menu.php");
    include("../includes/cargar_clases.php");
    $crudmarca = new CRUDMarca();
    $crudcategoria = new CRUDCategoria();
    $rs_mar = $crudmarca->ListarMarca();
    $rs_cat = $crudcategoria->ListarCategoria();
    ?>
    <div class="contenier mt-3">
        <header>
            <h1 class="text-primary"><i class="fas fa-plus-circle"></i>Registrar Producto</h1>
        </header>
    </div>
    <nav>
        <a href="listar_producto.php" class="btn btn-outline-secundary btn-sm">
            <i class="fas fa-arrow-circle-left"></i>Regresar
        </a>
    </nav>

    <section>
        <article>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card">
                        <div class="container mt-5">
                            <form id="frm_registrar_prod" name="frm_registrar_prod" action="../controller/ctr_grabar_prod.php" autocomplete="off" method="POST">
                                <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
                                <div class="row g3">
                                    <div class="col-md-4">
                                        <label for="txt_codprod" class="form-label">ID Producto:</label>
                                        <input type="text" class="form-control" id="txt_codprod" name="txt_codprod" placeholder="Código" maxlength="5" autofocus>
                                    </div>
                                    <div class="col-md-8">
                                        <label for="txt_prod" class="form-label">Nombre del Producto:</label>
                                        <input type="text" class="form-control" id="txt_prod" name="txt_prod" placeholder="Nombre de Producto" maxlength="40">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="txt_stk" class="form-label">Stock Disponible:</label>
                                        <input type="number" class="form-control" id="txt_stk" name="txt_stk" placeholder="Stock" maxlength="4" min="1" max="9999">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="txt_cst" class="form-label">Costo:</label>
                                        <input type="number" class="form-control" id="txt_cst" name="txt_cst" placeholder="Costo" maxlength="8">
                                    </div>
                                    <div class="form-group">
                                        <label for="txt_gnc" class="form-label">Ganancia:</label>
                                        <input type="number" class="form-control" id="txt_gnc" name="txt_gnc" min="1" max="100" step="0.01">
                                    </div>
                                    <div class="form-group">
                                        <label for="cbo_mar" class="form-label">Marca:</label>
                                        <select class="form-select form-select-lg mb-3" id="cbo_mar" name="cbo_mar">
                                            <option value="" selected>Seleccione una Marca</option>
                                            <?php foreach ($rs_mar as $mar): ?>
                                                <option value="<?= $mar->id_marca ?>"><?= $mar->marca ?></option>
                                            <?php endforeach; ?>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cbo_cat" class="form-label">Categoria:</label>
                                        <select class="form-select form-select-lg mb-3" id="cbo_cat" name="cbo_cat">
                                            <option value="" selected>Seleccione una Categoria</option>
                                            <?php
                                            foreach ($rs_cat as $cat) {
                                            ?>
                                                <option value="<?= $cat->id_categoria ?>"><?= $cat->categoria ?></option>
                                            <?php
                                            }
                                            ?>
                                        </select>
                                    </div>
                                </div>
                                <div class="text-center"><button type="submit" class="btn btn-outline-primary" id="btn_registrar_prod" name="btn_registrar_prod"><i class="fas fa-save"></i>Registrar Producto</button></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </section>

    <?php include("../includes/pie.php") ?>

</body>

</html>