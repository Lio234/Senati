<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Editar Producto";
include "../includes/cargar_clases.php";

if (isset($_GET["cod_prod"])) {
    $cod_prod = $_GET["cod_prod"];
    $crudproducto = new CRUDProducto();
    $rs_prod = $crudproducto->BuscarProductoPorCodigo($cod_prod);
    
    if (!empty($rs_prod)) {
        $crudmarca = new CRUDMarca();
        $crudcategoria = new CRUDCategoria();

        $rs_mar = $crudmarca->ListarMarca();
        $rs_cat = $crudcategoria->ListarCategoria();
    } else {
        header("location: listar_producto.php");
        exit;
    }
} else {
    header("location: listar_producto.php");
    exit;
}
?>

<body>
    <div class="container mt-5">
        <form id="frm_editar_prod" name="frm_editar_prod" action="../controller/ctr_grabar_prod.php" method="post" autocomplete="off">
            <input type="hidden" id="txt_tipo" name="txt_tipo" value="e">
            <div class="row g3">
                <div class="col-md-4">
                    <label for="txt_codprod" class="form-label">ID Producto:</label>
                    <input type="text" class="form-control" id="txt_codprod" name="txt_codprod" placeholder="Código" maxlength="5" readonly value="<?= htmlspecialchars($rs_prod->id_producto) ?>">
                </div>
                <div class="col-md-8">
                    <label for="txt_prod" class="form-label">Nombre del Producto:</label>
                    <input type="text" class="form-control" id="txt_prod" name="txt_prod" placeholder="Nombre de Producto" maxlength="40" value="<?= htmlspecialchars($rs_prod->producto) ?>" autofocus>
                </div>
                <div class="col-md-4">
                    <label for="txt_stk" class="form-label">Stock Disponible:</label>
                    <input type="number" class="form-control" id="txt_stk" name="txt_stk" placeholder="Stock" maxlength="4" min="1" max="9999" value="<?= htmlspecialchars($rs_prod->stock_disponible) ?>">
                </div>
                <div class="col-md-4">
                    <label for="txt_cst" class="form-label">Costo:</label>
                    <input type="number" class="form-control" id="txt_cst" name="txt_cst" placeholder="Costo" maxlength="8" value="<?= htmlspecialchars($rs_prod->costo) ?>">
                </div>
                <div class="col-md-4">
                    <label for="txt_gnc" class="form-label">% Ganancia:</label>
                    <input type="number" class="form-control" id="txt_gnc" name="txt_gnc" min="1" max="100" step="0.01" value="<?= htmlspecialchars($rs_prod->ganancia * 100) ?>">
                </div>
                <div class="form-group">
                    <label for="cbo_mar" class="form-label">Marca:</label>
                    <select class="form-select form-select-lg mb-3" id="cbo_mar" name="cbo_mar">
                        <option value="">Seleccione una Marca</option>
                        <?php foreach ($rs_mar as $mar): ?>
                            <option value="<?= $mar->id_marca ?>" <?= ($mar->id_marca == $rs_prod->producto_id_marca) ? 'selected' : '' ?>>
                                <?= htmlspecialchars($mar->marca) ?>
                            </option>
                        <?php endforeach; ?>
                    </select>
                </div>
                <div class="form-group">
                    <label for="cbo_cat" class="form-label">Categoría:</label>
                    <select class="form-select form-select-lg mb-3" id="cbo_cat" name="cbo_cat">
                        <option value="">Seleccione una Categoría</option>
                        <?php foreach ($rs_cat as $cat): ?>
                            <option value="<?= $cat->id_categoria ?>" <?= ($cat->id_categoria == $rs_prod->producto_id_categoria) ? 'selected' : '' ?>>
                                <?= htmlspecialchars($cat->categoria) ?>
                            </option>
                        <?php endforeach; ?>
                    </select>
                </div>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-outline-primary" id="btn_registrar_prod" name="btn_registrar_prod">
                    <i class="fas fa-save"></i> Actualizar Producto
                </button>
            </div>
        </form>
    </div>
</body>
</html>