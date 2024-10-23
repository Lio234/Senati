<?php
class CRUDDistrito extends Conexion
{
    public function ListarDistrito()
    {
        $arr_dis = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_distritos()";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_dis = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_dis;
    }
    public function BuscarDistritoPorCodigo($cod_dis)
    {
        $arr_dis = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_distrito_por_id(:cod_dis);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dis', $cod_dis, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dis = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;
        return $arr_dis;
    }


    public function MostrarDistritoPorCodigo($cod_dis)
    {
        $arr_dis = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_distrito_por_id(:cod_dis);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dis', $cod_dis, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dis = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_dis;
    }
    public function ConsultarDistritoPorCodigo($cod_dis)
    {
        $arr_dis = null;

        $cn = $this->Conectar();

        $sql = "CALL sp_mostrar_distrito_por_id(:cod_dis);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dis', $cod_dis, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dis = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
        }

        $cn = null;

        return $arr_dis; // Retornar el distrito
    }


    public function FiltrarDistrito($valor)
    {
        $arr_dis = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_distrito(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_dis = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-pridisy'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>distrito</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_dis as $dis) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $dis->id_distrito . "</td>";
                echo "<td>" . $dis->distrito . "</td>";
                echo "<td>" . $dis->distrito_id_provincia . "</td>";
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


    public function RegistrarDistrito(distrito $distrito)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_distrito(:id_dis, :dis: id_pro);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_dis', $distrito->id_distrito);
            $snt->bindParam(':dis', $distrito->distrito);
            $snt->bindParam(':id_pro', $distrito->distrito_id_provincia);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }


    public function EditarDistrito(distrito $distrito)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_distrito(:cod_dis, :dis: id_pro);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_dis', $distrito->id_distrito);
            $snt->bindParam(':dis', $distrito->distrito);
            $snt->bindParam(':id_pro', $distrito->distrito_id_provincia);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }

    
    public function BorrarDistrito($id_distrito)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_distrito(:cod_dis);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_dis', $id_distrito);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
