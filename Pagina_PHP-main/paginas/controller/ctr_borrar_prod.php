<?php
include "../includes/cargar_clases.php";

$crudproducto = new CRUDProducto();

// Cambia "codprod" a "cod_prod" para que coincida con el nombre del parámetro pasado por la URL
if (isset($_GET["cod_prod"])) {
    $cod_prod = $_GET["cod_prod"];
    $crudproducto->BorrarProducto($cod_prod);
    header("location: ../view/listar_producto.php");
    exit;
}
?>
