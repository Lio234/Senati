<?php
include "../includes/cargar_clases.php";

$cruddistrito = new CRUDDistrito();

if (isset($_GET["cod_dis"])) {
    $cod_dis = $_GET["cod_dis"];

    try {
        $cruddistrito->BorrarDistrito($cod_dis);
        echo json_encode(['status' => 'success']);
    } catch (Exception $e) {
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }
    
}
?>
