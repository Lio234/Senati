<?php
include "../includes/cargar_clases.php";

$crudproducto = new CRUDProducto();

if (isset($_GET["cod_prod"])) {
    $cod_prod = $_GET["cod_prod"];
    $crudproducto->BorrarProducto($cod_prod);
    header("location: ../view/listar_producto.php");
    exit;
}
?>
