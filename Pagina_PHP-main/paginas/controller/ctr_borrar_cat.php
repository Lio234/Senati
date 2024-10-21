<?php
include "../includes/cargar_clases.php";

$crudcategoria = new CRUDCategoria();

if (isset($_GET["cod_cat"])) {
    $cod_cat = $_GET["cod_cat"];

    try {
        $crudcategoria->BorrarCategoria($cod_cat);
        echo json_encode(['status' => 'success']);
    } catch (Exception $e) {
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }
    
}
?>
