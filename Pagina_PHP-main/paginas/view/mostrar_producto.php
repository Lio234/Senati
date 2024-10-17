<!DOCTYPE html>
<html lang="es">
<?php
$ruta = "../..";
$titulo = "Aplicacion de Ventas - Mostrar Producto";
include("../includes/cabecera.php");
?>

<body>
    <?php include("../includes/menu.php"); ?>

    <div class="container mt-3">
        <header>
            <h1><i class="fas fa-info-circle"></i> Detalle del Producto</h1>
            <hr />
        </header>

        <section id="productoInfo">
            <!-- Aquí se cargarán los detalles del producto a través de AJAX -->
        </section>

        <div class="text-center mt-3">
            <a href="listar_producto.php" class="btn btn-outline-secondary">Regresar</a>
        </div>
    </div>

    <?php include("../includes/pie.php"); ?>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            const cod_prod = new URLSearchParams(window.location.search).get('cod_prod');

            if (cod_prod) {
                $.ajax({
                    url: "../controller/ctr_consultar_prod.php",
                    type: "POST",
                    data: {
                        cod_prod: cod_prod
                    },
                    success: function(rpta) {
                        let rp = JSON.parse(rpta);
                        if (rp) {
                            $("#productoInfo").html(`
 
     <article>
    <div class="row justify-content-center mt-3">
        <div class="card col-md-6">
            <div class="card-body">
                    <div class="row g-3">
                        <div class="col-md-8">
                            <h5 class="card-title">Codigo</h5>
                            <p class="prod card-text">${rp.id_producto}</p>
                        </div>
                        <div class="col-md-8"></div>

                        <div class="col-md-8">
                            <h5 class="card-title">Producto</h5>
                            <p class="prod card-text">${rp.producto}</p>
                        </div>

                        <div class="col-md-4">
                            <h5 class="card-title">Stock disponible</h5>
                            <p class="stk card-text">${rp.stock_disponible}</p>
                        </div>

                        <div class="col-md-4">
                            <h5 class="card-title">Costo</h5>
                            <p class="cst card-text">${rp.costo}</p>
                        </div>

                        <div class="col-md-4">
                            <h5 class="card-title">% Ganancia</h5>
                            <p class="gnc card-text">${rp.ganancia}</p>
                        </div>

                        <div class="col-md-4">
                            <h5 class="card-title">Precio</h5>
                            <p class="prc card-text">S/${rp.precio}</p>
                        </div>

                        <div class="col-md-6">
                            <h5 class="card-title">Marca</h5>
                            <p class="mar card-text">${rp.marca}</p>
                        </div>

                        <div class="col-md-6">
                            <h5 class="card-title">Categoria</h5>
                            <p class="cat card-text">${rp.categoria}</p>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</article>
        `);
                        } else {
                            $("#productoInfo").html(`<div class="alert alert-danger">El producto no fue encontrado.</div>`);
                        }
                    },
                    error: function() {
                        $("#productoInfo").html(`<div class="alert alert-danger">Error al consultar el producto.</div>`);
                    }
                });
            } else {
                $("#productoInfo").html(`<div class="alert alert-warning">Código del producto no especificado.</div>`);
            }
        });
    </script>
</body>



</html>