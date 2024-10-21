<?php
class CRUDMarca extends Conexion
{
    public function ListarMarca()
    {
        $arr_mar = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_marcas()";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_mar = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_mar;
    }
    public function BuscarMarcaPorCodigo($cod_mar)
    {
        $arr_mar = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_marca_por_id(:cod_mar);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_mar', $cod_mar, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_mar = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;
        return $arr_mar;
    }


    public function MostrarMarcaPorCodigo($cod_mar)
    {
        $arr_mar = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_marca_por_id(:cod_mar);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_mar', $cod_mar, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_mar = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_mar;
    }
    public function ConsultarMarcaPorCodigo($cod_mar)
    {
        $arr_mar = null;

        $cn = $this->Conectar();

        $sql = "CALL sp_mostrar_marca_por_id(:cod_mar);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_mar', $cod_mar, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_mar = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
        }

        $cn = null;

        return $arr_mar; // Retornar el marca
    }


    public function FiltrarMarca($valor)
    {
        $arr_mar = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_marca(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_mar = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-primary'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>marca</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_mar as $mar) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $mar->id_marca . "</td>";
                echo "<td>" . $mar->marca . "</td>";
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


    public function RegistrarMarca(marca $marca)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_marca(:id_mar, :mar);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_mar', $marca->id_marca);
            $snt->bindParam(':mar', $marca->marca);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }


    public function EditarMarca(marca $marca)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_marca(:cod_mar, :mar);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_mar', $marca->id_marca);
            $snt->bindParam(':mar', $marca->marca);
            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }

    
    public function BorrarMarca($id_marca)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_marca(:cod_mar);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_mar', $id_marca);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
