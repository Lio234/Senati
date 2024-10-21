<?php
include("../includes/cargar_clases.php");
?>
<div class="container mt-5">
    <form id="frm_registrar_cat" name="frm_registrar_cat" action="../controller/ctr_grabar_cat.php" autocomplete="off" method="POST">
        <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
        <div class="row g3">
            <div class="col-md-4">
                <label for="txt_codcat" class="form-label">ID Categoria:</label>
                <input type="text" class="form-control" id="txt_codcat" name="txt_codcat" placeholder="Código" maxlength="5" autofocus>
            </div>
            <div class="col-md-8">
                <label for="txt_cat" class="form-label">Nombre del Categoria:</label>
                <input type="text" class="form-control" id="txt_cat" name="txt_cat" placeholder="Nombre de Categoria" maxlength="40">
            </div>
        </div>
        <div class="text-center"><button type="submit" class="btn btn-outline-primary" id="btn_registrar_cat" name="btn_registrar_cat"><i class="fas fa-save"></i>Registrar Categoria</button></div>
    </form>
</div>