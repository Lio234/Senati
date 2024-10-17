<?php
    class CRUDEmpleado extends Conexion{
        public function ListarEmpleado(){
            $arr_empleado= null;
            
            $cn= $this-> Conectar();

            $sql = "call sp_listar_empleados()";

            $snt = $cn->prepare($sql);
            
            $snt->execute();

            $arr_empleado=$snt->fetchAll(PDO::FETCH_OBJ);

            $cn=null;

            return $arr_empleado;
        }
    }