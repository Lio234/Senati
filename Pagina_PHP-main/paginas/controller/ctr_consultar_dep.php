<?php
include "../includes/cargar_clases.php";

$cruddepartamento = new CRUDDepartamento();

if (isset($_POST["cod_dep"])) {
    $cod_departamento = $_POST["cod_dep"];
    $departamento = $cruddepartamento->ConsultarDepartamentoPorCodigo($cod_departamento);
    echo json_encode($departamento);
}
