<?php
include "../includes/cargar_clases.php";

$crudmarca = new CRUDMarca();

if (isset($_POST["cod_mar"])) {
    $cod_marca = $_POST["cod_mar"];
    $marca = $crudmarca->ConsultarmarcaPorCodigo($cod_marca);
    echo json_encode($marca);
}
