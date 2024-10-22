<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicación de Ventas - Editar Cliente";
include "../includes/cargar_clases.php";

if (isset($_GET["id_cliente"])) {
    $id_cliente= $_GET["id_cliente"];
    $crudcliente = new CRUDCliente();
    $rs_clien = $crudcliente->BuscarClientePorCodigo($id_cliente);
    
    if (!empty($rs_clien)) {
        $cruddistrito = new CRUDDistrito();
        $rs_dist = $cruddistrito->ListarDistrito();
    } else {
        header("location: listar_cliente.php");
        exit;
    }
} else {
    header("location: listar_cliente.php");
    exit;
}
?>

<body>
    <div class="container mt-5">
        <form id="frm_editar_clien" name="frm_editar_clien" action="../controller/ctr_grabar_clien.php" method="post" autocomplete="off">
            <input type="hidden" id="txt_tipo" name="txt_tipo" value="e">
            <div class="row g3">
                <!-- ID Cliente -->
                <div class="col-md-4">
                    <label for="txt_id_cliente" class="form-label">ID Cliente:</label>
                    <input type="text" class="form-control" id="txt_id_cliente" name="txt_id_cliente" readonly value="<?= htmlspecialchars($rs_clien->id_cliente) ?>">
                </div>
                <!-- Nombre Cliente -->
                <div class="col-md-8">
                    <label for="txt_nom" class="form-label">Nombre del cliente:</label>
                    <input type="text" class="form-control" id="txt_nom" name="txt_nom" placeholder="Nombre del cliente" maxlength="40" value="<?= htmlspecialchars($rs_clien->nombre) ?>" autofocus>
                </div>
                <!-- Apellido Paterno -->
                <div class="col-md-4">
                    <label for="txt_a_pat" class="form-label">Apellido paterno:</label>
                    <input type="text" class="form-control" id="txt_a_pat" name="txt_a_pat" placeholder="Apellido paterno" maxlength="40" value="<?= htmlspecialchars($rs_clien->ap_paterno) ?>">
                </div>
                <!-- Apellido Materno -->
                <div class="col-md-4">
                    <label for="txt_a_mat" class="form-label">Apellido materno:</label>
                    <input type="text" class="form-control" id="txt_a_mat" name="txt_a_mat" placeholder="Apellido materno" maxlength="40" value="<?= htmlspecialchars($rs_clien->ap_materno) ?>">
                </div>
                <!-- Dirección -->
                <div class="col-md-8">
                    <label for="txt_direc" class="form-label">Dirección:</label>
                    <input type="text" class="form-control" id="txt_direc" name="txt_direc" placeholder="Dirección" maxlength="100" value="<?= htmlspecialchars($rs_clien->direccion) ?>">
                </div>
                <!-- Correo -->
                <div class="col-md-6">
                    <label for="txt_cor" class="form-label">Correo:</label>
                    <input type="email" class="form-control" id="txt_cor" name="txt_cor" placeholder="Correo electrónico" value="<?= htmlspecialchars($rs_clien->correo) ?>">
                </div>
                <!-- Teléfono -->
                <div class="col-md-6">
                    <label for="txt_tel" class="form-label">Teléfono:</label>
                    <input type="text" class="form-control" id="txt_tel" name="txt_tel" placeholder="Teléfono" maxlength="15" value="<?= htmlspecialchars($rs_clien->telefono) ?>">
                </div>
                <!-- Distrito -->
                <div class="col-md-12">
                    <label for="cbo_dist" class="form-label">Distrito:</label>
                    <select class="form-select form-select-lg mb-3" id="cbo_dist" name="cbo_dist">
                        <option value="">Seleccione un Distrito</option>
                        <?php foreach ($rs_dist as $dist): ?>
                            <option value="<?= $dist->id_distrito ?>" <?= ($dist->id_distrito == $rs_clien->cliente_id_distrito) ? 'selected' : '' ?>>
                                <?= htmlspecialchars($dist->distrito) ?>
                            </option>
                        <?php endforeach; ?>
                    </select>
                </div>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-outline-primary" id="btn_registrar_clien" name="btn_registrar_clien">
                    <i class="fas fa-save"></i> Actualizar Cliente
                </button>
            </div>
        </form>
    </div>
</body>
</html>