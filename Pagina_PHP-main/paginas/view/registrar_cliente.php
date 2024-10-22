<?php
include("../includes/cargar_clases.php");
$cruddistrito = new CRUDDistrito();
$rs_dist = $cruddistrito->ListarDistrito();
?>
<div class="container mt-5">
    <form id="frm_registrar_clien" name="frm_registrar_clien" action="../controller/ctr_grabar_clien.php" autocomplete="off" method="POST">
        <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
        <div class="row g3">
            <div class="col-md-4">
                <label for="txt_id_cliente" class="form-label">ID Cliente:</label>
                <input type="text" class="form-control" id="txt_id_cliente" name="txt_id_cliente" placeholder="Código" maxlength="5" autofocus>
            </div>
            <div class="col-md-8">
                <label for="txt_nom" class="form-label">Nombre del cliente:</label>
                <input type="text" class="form-control" id="txt_nom" name="txt_nom" placeholder="Nombre del Cliente" maxlength="40">
            </div>
            <div class="col-md-4">
                <label for="txt_a_pat" class="form-label">Apellido paterno:</label>
                <input type="text" class="form-control" id="txt_a_pat" name="txt_a_pat" placeholder="Apellido Paterno" maxlength="40">
            </div>
            <div class="col-md-4">
                <label for="txt_a_mat" class="form-label">Apellido materno:</label>
                <input type="text" class="form-control" id="txt_a_mat" name="txt_a_mat" placeholder="Apellido Materno" maxlength="40">
            </div>
            <div class="form-group">
                <label for="txt_direc" class="form-label">Dirección:</label>
                <input type="text" class="form-control" id="txt_direc" name="txt_direc" placeholder="Dirección" maxlength="100">
            </div>
            <div class="col-md-4">
                <label for="txt_cor" class="form-label">Correo:</label>
                <input type="email" class="form-control" id="txt_cor" name="txt_cor" placeholder="Correo Electrónico" maxlength="100">
            </div>
            <div class="col-md-4">
                <label for="txt_tel" class="form-label">Teléfono:</label>
                <input type="text" class="form-control" id="txt_tel" name="txt_tel" placeholder="Teléfono" maxlength="9">
            </div>
            <div class="form-group">
                <label for="cbo_dist" class="form-label">Distrito:</label>
                <select class="form-select form-select-lg mb-3" id="cbo_dist" name="cbo_dist">
                    <option value="" selected>Seleccione un distrito</option>
                    <?php foreach ($rs_dist as $dist): ?>
                        <option value="<?= $dist->id_distrito ?>"><?= $dist->distrito ?></option>
                    <?php endforeach; ?>
                </select>
            </div>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-outline-primary" id="btn_registrar_clien" name="btn_registrar_clien">
                <i class="fas fa-save"></i> Registrar Cliente
            </button>
        </div>
    </form>
</div>