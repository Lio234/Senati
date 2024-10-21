<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Editar Marca";
include "../includes/cargar_clases.php";

if (isset($_GET["cod_mar"])) {
    $cod_mar = $_GET["cod_mar"];
    $crudmarca = new CRUDMarca();
    $rs_mar = $crudmarca->BuscarMarcaPorCodigo($cod_mar);
} else {
    header("lomarion: listar_marca.php");
    exit;
}
?>

<body>
    <div class="container mt-5">
        <form id="frm_editar_mar" name="frm_editar_mar" action="../controller/ctr_grabar_mar.php" method="post" autocomplete="off">
            <input type="hidden" id="txt_tipo" name="txt_tipo" value="e">
            <div class="row g3">
                <div class="col-md-4">
                    <label for="txt_codmar" class="form-label">ID Marca:</label>
                    <input type="text" class="form-control" id="txt_codmar" name="txt_codmar" placeholder="Código" maxlength="5" readonly value="<?= htmlspecialchars($rs_mar->id_marca) ?>">
                </div>
                <div class="col-md-8">
                    <label for="txt_mar" class="form-label">Nombre del Marca:</label>
                    <input type="text" class="form-control" id="txt_mar" name="txt_mar" placeholder="Nombre de Marca" maxlength="40" value="<?= htmlspecialchars($rs_mar->marca) ?>" autofocus>
                </div
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-outline-primary" id="btn_registrar_mar" name="btn_registrar_mar">
                    <i class="fas fa-save"></i> Actualizar Marca
                </button>
            </div>
        </form>
    </div>
</body>
</html>