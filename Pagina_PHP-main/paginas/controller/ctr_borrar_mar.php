<?php
include "../includes/cargar_clases.php";

$crudmarca = new CRUDMarca();

if (isset($_GET["cod_mar"])) {
    $cod_mar = $_GET["cod_mar"];

    try {
        $crudmarca->BorrarMarca($cod_mar);
        echo json_encode(['status' => 'success']);
    } catch (Exception $e) {
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }
    
}
?>
