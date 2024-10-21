<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Editar Categoria";
include "../includes/cargar_clases.php";

if (isset($_GET["cod_cat"])) {
    $cod_cat = $_GET["cod_cat"];
    $crudcategoria = new CRUDCategoria();
    $rs_cat = $crudcategoria->BuscarCategoriaPorCodigo($cod_cat);
} else {
    header("location: listar_categoria.php");
    exit;
}
?>

<body>
    <div class="container mt-5">
        <form id="frm_editar_cat" name="frm_editar_cat" action="../controller/ctr_grabar_cat.php" method="post" autocomplete="off">
            <input type="hidden" id="txt_tipo" name="txt_tipo" value="e">
            <div class="row g3">
                <div class="col-md-4">
                    <label for="txt_codcat" class="form-label">ID Categoria:</label>
                    <input type="text" class="form-control" id="txt_codcat" name="txt_codcat" placeholder="Código" maxlength="5" readonly value="<?= htmlspecialchars($rs_cat->id_categoria) ?>">
                </div>
                <div class="col-md-8">
                    <label for="txt_cat" class="form-label">Nombre del Categoria:</label>
                    <input type="text" class="form-control" id="txt_cat" name="txt_cat" placeholder="Nombre de Categoria" maxlength="40" value="<?= htmlspecialchars($rs_cat->categoria) ?>" autofocus>
                </div
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-outline-primary" id="btn_registrar_cat" name="btn_registrar_cat">
                    <i class="fas fa-save"></i> Actualizar Categoria
                </button>
            </div>
        </form>
    </div>
</body>
</html>