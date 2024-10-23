<?php
include "../includes/cargar_clases.php";

$cruddepartamento = new CRUDDepartamento();

if (isset($_GET["cod_dep"])) {
    $cod_dep = $_GET["cod_dep"];
    $departamento = $cruddepartamento->ConsultarDepartamentoPorCodigo($cod_dep);

    if ($departamento) {
        // Muestra los detalles del departamento
        echo '
             <div id="detalle_departamento">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <h6 class="card-title">Código</h6>
                            <p class="dep card-text">' . $departamento->id_departamento . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Departamento</h6>
                            <p class="dep card-text">' . $departamento->departamento . '</p>
                        </div>
                    </div>
                </div>
        ';
    } else {
        echo '<p class="text-danger">Departamento no encontrado.</p>';
    }
} else {
    echo '<p class="text-warning">Código del departamento no especificado.</p>';
}



