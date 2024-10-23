<?php
   include "../includes/cargar_clases.php";

   $cruddistrito = new CRUDDistrito();
   
   if (isset($_POST["btn_registrar_dist"])) {
       $distrito = new Distrito();
   
       $distrito->id_distrito = $_POST["txt_coddist"];
       $distrito->distrito = $_POST["txt_dist"];
       $distrito->distrito_id_provincia = $_POST["cbo_pro"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $cruddistrito->RegistrarDistrito($distrito);
       } else if ($tipo == "e") {
           $cruddistrito->EditarDistrito($distrito);
       }
   
       header("location: ../view/listar_distrito.php");
   }
   