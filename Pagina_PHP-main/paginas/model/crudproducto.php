<?php
class CRUDProducto extends Conexion
{
    public function ListarProducto()
    {
        $arr_prod = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_productos();";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_prod = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_prod;
    }


    public function BuscarProductoPorCodigo($cod_prod)
    {
        $arr_prod = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_producto_por_id(:cod_prod);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_prod', $cod_prod, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_prod = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null; 
        return $arr_prod;
    }



    public function MostrarProductoPorCodigo($cod_prod)
    {
        $arr_prod = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_producto_por_id(:cod_prod);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_prod', $cod_prod, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_prod = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_prod;
    }


    public function ConsultarProductoPorCodigo($cod_prod)
    {
        $arr_prod = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_producto_por_id(:cod_prod);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_prod', $cod_prod, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_prod = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        echo json_encode($arr_prod);
    }


    public function FiltrarProducto($valor)
    {
        $arr_prod = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_por_producto(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_prod = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-success table-striped'>";
            echo "<tr class='table-primary'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>Producto</th>";
            echo "<th>Stock Disponible</th>";
            echo "<th>Costo</th>";
            echo "<th>% Ganancia</th>";
            echo "<th>Marca</th>";
            echo "<th>Categoría</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_prod as $prod) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $prod->id_producto . "</td>";
                echo "<td>" . $prod->producto . "</td>";
                echo "<td class='text-center'>" . $prod->stock_disponible . "</td>";
                echo "<td>S/." . $prod->costo . "</td>";
                echo "<td class='text-center'>" . $prod->ganancia . "%</td>";
                echo "<td>" . $prod->marca . "</td>";
                echo "<td>" . $prod->categoria . "</td>";
                echo "</tr>";
            }
            echo "</table>";
        } else {
            echo "<div class='alert alert-danger alert-dismissible fade show' role='alert'>";
            echo "No hay registros.";
            echo "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
            echo "</div>";
        }
        $cn = null;
    }


    public function RegistrarProducto(Producto $producto)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_producto(:cod_prod, :prod, :stk, :cst, :gnc, :cod_mar, :cod_cat);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_prod', $producto->id_producto);
            $snt->bindParam(':prod', $producto->producto);
            $snt->bindParam(':stk', $producto->stock_disponible);
            $snt->bindParam(':cst', $producto->costo);
            $snt->bindParam(':gnc', $producto->ganancia);
            $snt->bindParam(':cod_mar', $producto->producto_id_marca);
            $snt->bindParam(':cod_cat', $producto->producto_id_categoria);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
    public function EditarProducto(Producto $producto)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_producto(:cod_prod, :prod, :stk, :cst, :gnc, :cod_mar, :cod_cat);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_prod', $producto->id_producto);
            $snt->bindParam(':prod', $producto->producto);
            $snt->bindParam(':stk', $producto->stock_disponible);
            $snt->bindParam(':cst', $producto->costo);
            $snt->bindParam(':gnc', $producto->ganancia);
            $snt->bindParam(':cod_mar', $producto->producto_id_marca);
            $snt->bindParam(':cod_cat', $producto->producto_id_categoria);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
    public function BorrarProducto($id_producto)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_producto(:cod_prod);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_prod', $id_producto);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
