<?php
include "../includes/cargar_clases.php";

$crudprovincia = new CRUDProvincia();

if (isset($_POST["cod_pro"])) {
    $cod_provincia = $_POST["cod_pro"];
    $provincia = $crudprovincia->ConsultarProvinciaPorCodigo($cod_provincia);
    echo json_encode($provincia);
}
