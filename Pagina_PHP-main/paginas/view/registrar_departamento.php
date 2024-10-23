<?php
include("../includes/cargar_clases.php");
?>
<div class="container mt-5">
    <form id="frm_registrar_dep" name="frm_registrar_dep" action="../controller/ctr_grabar_dep.php" autocomplete="off" method="POST">
        <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
        <div class="row g3">
            <div class="col-md-4">
                <label for="txt_coddep" class="form-label">ID Departamento:</label>
                <input type="text" class="form-control" id="txt_coddep" name="txt_coddep" placeholder="Código" maxlength="5" autofocus>
            </div>
            <div class="col-md-8">
                <label for="txt_dep" class="form-label">Nombre del Departamento:</label>
                <input type="text" class="form-control" id="txt_dep" name="txt_dep" placeholder="Nombre de Departamento" maxlength="40">
            </div>
        </div>
        <div class="text-center"><button type="submit" class="btn btn-outline-primary" id="btn_registrar_dep" name="btn_registrar_dep"><i class="fas fa-save"></i>Registrar Departamento</button></div>
    </form>
</div>