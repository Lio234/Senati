<?php
include "../includes/cargar_clases.php";

$crudprovincia = new CRUDProvincia();

if (isset($_GET["cod_pro"])) {
    $cod_pro = $_GET["cod_pro"];

    try {
        $crudprovincia->BorrarProvincia($cod_pro);
        echo json_encode(['status' => 'success']);
    } catch (Exception $e) {
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }
    
}
?>
