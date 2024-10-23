<?php
class CRUDProvincia extends Conexion
{
    public function ListarProvincia()
    {
        $arr_pro = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_provincias()";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_pro = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_pro;
    }
    public function BuscarProvinciaPorCodigo($cod_pro)
    {
        $arr_pro = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_provincia_por_id(:cod_pro);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_pro', $cod_pro, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_pro = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;
        return $arr_pro;
    }


    public function MostrarProvinciaPorCodigo($cod_pro)
    {
        $arr_pro = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_provincia_por_id(:cod_pro);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_pro', $cod_pro, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_pro = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_pro;
    }
    public function ConsultarProvinciaPorCodigo($cod_pro)
    {
        $arr_pro = null;

        $cn = $this->Conectar();

        $sql = "CALL sp_mostrar_provincia_por_id(:cod_pro);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_pro', $cod_pro, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_pro = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
        }

        $cn = null;

        return $arr_pro; // Retornar el provincia
    }


    public function FiltrarProvincia($valor)
    {
        $arr_pro = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_provincia(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_pro = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-priproy'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>provincia</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_pro as $pro) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $pro->id_provincia . "</td>";
                echo "<td>" . $pro->provincia . "</td>";
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


    public function RegistrarProvincia(provincia $provincia)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_provincia(:id_pro, :pro);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_pro', $provincia->id_provincia);
            $snt->bindParam(':pro', $provincia->provincia);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }


    public function EditarProvincia(provincia $provincia)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_provincia(:cod_pro, :pro);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_pro', $provincia->id_provincia);
            $snt->bindParam(':pro', $provincia->provincia);
            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }

    
    public function BorrarProvincia($id_provincia)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_provincia(:cod_pro);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_pro', $id_provincia);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
