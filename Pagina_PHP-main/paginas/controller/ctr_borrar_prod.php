<?php
    include "../includes/cargar_clases.php";

    $crudproducto = new CRUDProducto();

    if (isset($_GET["codprod"])) {
        $cod_prod = $_GET["codprod"];
        $crudproducto->BorrarProducto($cod_prod);
        header("location: ../view/listar_producto.php");
        exit;
    }
    
