<?php
    include "../includes/cargar_clases.php";

    $crudproducto = new CRUDProducto();

    if(isset($_POST["cod_prod"])){
        $cod_prod=$_POST["cod_prod"];

        $crudproducto->ConsultarProductoPorCodigo($cod_prod);

    }
