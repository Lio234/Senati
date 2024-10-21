<?php
include "../includes/cargar_clases.php";

$crudcategoria = new CRUDcategoria();

if (isset($_POST["cod_cat"])) {
    $cod_cat = $_POST["cod_cat"];
    $categoria = $crudcategoria->ConsultarcategoriaPorCodigo($cod_cat);
    echo json_encode($categoria);
}
