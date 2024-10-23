<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Editar Departamento";
include "../includes/cargar_clases.php";

if (isset($_GET["cod_dep"])) {
    $cod_dep = $_GET["cod_dep"];
    $cruddepartamento = new CRUDDepartamento();
    $rs_dep = $cruddepartamento->BuscarDepartamentoPorCodigo($cod_dep);
} else {
    header("location: listar_departamento.php");
    exit;
}
?>

<body>
    <div class="container mt-5">
        <form id="frm_editar_dep" name="frm_editar_dep" action="../controller/ctr_grabar_dep.php" method="post" autocomplete="off">
            <input type="hidden" id="txt_tipo" name="txt_tipo" value="e">
            <div class="row g3">
                <div class="col-md-4">
                    <label for="txt_coddep" class="form-label">ID Departamento:</label>
                    <input type="text" class="form-control" id="txt_coddep" name="txt_coddep" placeholder="Código" maxlength="5" readonly value="<?= htmlspecialchars($rs_dep->id_departamento) ?>">
                </div>
                <div class="col-md-8">
                    <label for="txt_dep" class="form-label">Nombre del Departamento:</label>
                    <input type="text" class="form-control" id="txt_dep" name="txt_dep" placeholder="Nombre de Departamento" maxlength="40" value="<?= htmlspecialchars($rs_dep->departamento) ?>" autofocus>
                </div
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-outline-primary" id="btn_registrar_dep" name="btn_registrar_dep">
                    <i class="fas fa-save"></i> Actualizar Departamento
                </button>
            </div>
        </form>
    </div>
</body>
</html>