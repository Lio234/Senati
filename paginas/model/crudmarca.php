<?php
    class CRUDMarca extends Conexion{
        public function ListarMarca(){
            $arr_marca= null;
            
            $cn= $this-> Conectar();

            $sql = "call sp_listar_marcas()";

            $snt = $cn->prepare($sql);
            
            $snt->execute();

            $arr_marca=$snt->fetchAll(PDO::FETCH_OBJ);

            $cn=null;

            return $arr_marca;
        }
    }