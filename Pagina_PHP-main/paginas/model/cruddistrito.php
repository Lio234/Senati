<?php
class CRUDDistrito extends Conexion
{
    public function ListarDistrito()
    {
        $arr_dist = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_distritos();";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_dist = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_dist;
    }
}