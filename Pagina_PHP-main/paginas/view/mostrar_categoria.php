<?php
include "../includes/cargar_clases.php";

$crudcategoria = new CRUDCategoria();

if (isset($_GET["cod_cat"])) {
    $cod_cat = $_GET["cod_cat"];
    $categoria = $crudcategoria->ConsultarCategoriaPorCodigo($cod_cat);

    if ($categoria) {
        // Muestra los detalles del categoria
        echo '
             <div id="detalle_categoria">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <h6 class="card-title">Código</h6>
                            <p class="cat card-text">' . $categoria->id_categoria . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Categoria</h6>
                            <p class="cat card-text">' . $categoria->categoria . '</p>
                        </div>
                    </div>
                </div>
        ';
    } else {
        echo '<p class="text-danger">Categoria no encontrado.</p>';
    }
} else {
    echo '<p class="text-warning">Código del categoria no especificado.</p>';
}



