<?php
include "../includes/cargar_clases.php";

$crudmarca = new CRUDMarca();

if (isset($_GET["cod_mar"])) {
    $cod_mar = $_GET["cod_mar"];
    $marca = $crudmarca->ConsultarMarcaPorCodigo($cod_mar);

    if ($marca) {
        // Muestra los detalles del marca
        echo '
             <div id="detalle_marca">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <h6 class="card-title">Código</h6>
                            <p class="mar card-text">' . $marca->id_marca . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Marca</h6>
                            <p class="mar card-text">' . $marca->marca . '</p>
                        </div>
                    </div>
                </div>
        ';
    } else {
        echo '<p class="text-danger">Marca no encontrado.</p>';
    }
} else {
    echo '<p class="text-warning">Código del marca no especificado.</p>';
}



