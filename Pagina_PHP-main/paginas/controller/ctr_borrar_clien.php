<?php
include "../includes/cargar_clases.php";

$crudcliente= new CRUDCliente();

if (isset($_GET["id_cliente"])) {
    $id_cliente = $_GET["id_cliente"];
    $crudcliente->BorrarCliente($id_cliente);
    header("location: ../view/listar_cliente.php");
    exit;
}

