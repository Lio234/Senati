<?php
include("../includes/cargar_clases.php");
?>
<div class="container mt-5">
    <form id="frm_registrar_mar" name="frm_registrar_mar" action="../controller/ctr_grabar_mar.php" autocomplete="off" method="POST">
        <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
        <div class="row g3">
            <div class="col-md-4">
                <label for="txt_codmar" class="form-label">ID Marca:</label>
                <input type="text" class="form-control" id="txt_codmar" name="txt_codmar" placeholder="Código" maxlength="5" autofocus>
            </div>
            <div class="col-md-8">
                <label for="txt_mar" class="form-label">Nombre del Marca:</label>
                <input type="text" class="form-control" id="txt_mar" name="txt_mar" placeholder="Nombre de Marca" maxlength="40">
            </div>
        </div>
        <div class="text-center"><button type="submit" class="btn btn-outline-primary" id="btn_registrar_mar" name="btn_registrar_mar"><i class="fas fa-save"></i>Registrar Marca</button></div>
    </form>
</div>