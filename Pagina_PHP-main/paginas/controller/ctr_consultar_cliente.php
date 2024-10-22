<?php
include "../includes/cargar_clases.php";

$crudcliente = new CRUDCliente();

if (isset($_POST["id_cliente"])) {
    $id_cliente = $_POST["id_cliente"];
    $cliente = $crudcliente->ConsultarClientePorCodigo($id_cliente);
    echo json_encode($cliente);
    
}
