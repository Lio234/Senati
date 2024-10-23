<?php
   include "../includes/cargar_clases.php";

   $cruddepartamento = new CRUDDepartamento();
   
   if (isset($_POST["btn_registrar_dep"])) {
       $departamento = new Departamento();
   
       $departamento->id_departamento = $_POST["txt_coddep"];
       $departamento->departamento = $_POST["txt_dep"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $cruddepartamento->RegistrarDepartamento($departamento);
       } else if ($tipo == "e") {
           $cruddepartamento->EditarDepartamento($departamento);
       }
   
       header("location: ../view/listar_departamento.php");
   }
   