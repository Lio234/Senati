<?php
include "../includes/cargar_clases.php";

$crudcliente = new CRUDCliente();

if (isset($_POST["btn_registrar_clien"])) {
    $cliente = new Cliente();

    $cliente->id_cliente = $_POST["txt_id_cliente"];
    $cliente->nombre = $_POST["txt_nom"];
    $cliente->ap_paterno = $_POST["txt_a_pat"];
    $cliente->ap_materno = $_POST["txt_a_mat"];
    $cliente->direccion = $_POST["txt_direc"];
    $cliente->correo = $_POST["txt_cor"];
    $cliente->telefono = $_POST["txt_tel"];
    $cliente->cliente_id_distrito = $_POST["cbo_dist"];

    $tipo = $_POST["txt_tipo"];

    if ($tipo == "r") {
        $crudcliente->RegistrarCliente($cliente); // Registrar nuevo cliente
    } else if ($tipo == "e") {
        $crudcliente->EditarCliente($cliente); // Editar cliente existente
    }

    header("location: ../view/listar_cliente.php");
}