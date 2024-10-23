<?php
include "../includes/cargar_clases.php";

$cruddepartamento = new CRUDDepartamento();

if (isset($_GET["cod_dep"])) {
    $cod_dep = $_GET["cod_dep"];

    try {
        $cruddepartamento->BorrarDepartamento($cod_dep);
        echo json_encode(['status' => 'success']);
    } catch (Exception $e) {
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }
    
}
?>
