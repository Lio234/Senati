<?php
include "../includes/cargar_clases.php";

$cruddistrito = new CRUDDistrito();

if (isset($_POST["cod_dis"])) {
    $cod_distrito = $_POST["cod_dis"];
    $distrito = $cruddistrito->ConsultarDistritoPorCodigo($cod_distrito);
    echo json_encode($distrito);
}
