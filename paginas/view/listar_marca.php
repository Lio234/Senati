<!DOCTYPE html>
<html lang="es">
    <?php
    $ruta = "../..";
    $titulo = "Listas de Marcas";
    include "../includes/cabecera.php";
    ?>
    <body>
        <?php
            include "../includes/cargar_clases.php";
            include "../includes/menu.php";
            
            $crudmarca = new CRUDMarca();
            $rs_mar = $crudmarca->ListarMarca();
        ?>
        <div class="container mt-3">
        <header>
                <h1><i class="fas fa-university"></i><?=$titulo?></h1>
                <hr/>
            </header>

            <section>
                <article>
                <div class="row justify-content-center mt-3">
                    <div class="col-md-6">
                        <table class="table table-hover table-sm table-sucess">
                            <tr class="table-primary text-center">
                                    <th>N°</th>
                                    <th>Código</th>
                                    <th>Marca</th>
                                </tr>
                                <?php
                                    $i = 0;
                                    foreach($rs_mar as $mar){
                                        $i++;
                                ?>
                                <tr>
                                    <td class="text-center"><?=$i?></td>
                                    <td class="text-center"><?=$mar->id_marca?></td>
                                    <td><?=$mar->marca?></td>
                                </tr>
                                <?php
                                    }
                                ?>
                        </table>
                    </div>
                </div>
                </article>
            </section>
            <?php
            include "../includes/pie.php";
            ?>
        </div>
    </body>
</html>
