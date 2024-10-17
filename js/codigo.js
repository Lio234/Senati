
$(function() {

    // Evento click del botón filtrar de la página filtrar_producto.php
    $("#frm_filtrar_prod #btn_filtrar").on("click", function (e) {
        e.preventDefault();

        var valor = $("#txt_valor").val();

        if (valor != "") {
            $.post("../controlador/ctr_filtrar_prod.php",
                { valor: valor },
                function (rpta) {
                    $("#tabla").html(rpta);
                });
        } else {
            $("#tabla").html("");
            alert("Escriba un valor para filtrar...");
            $("#txt_valor").focus();
        }
    });

    $(".reg_producto .btn_mostrar").click(function(e) {
        let cod_prod = $(this).closest(".reg_producto").children(".cod_prod").text();
    
        location.href = "mostrar_producto.php?cod_prod=" + cod_prod;
        console.log("Código del producto: ", cod_prod);

    });

    $(".reg_producto .btn_editar").click(function(e){
        let cod_prod = $(this).closest(".reg_producto").children(".cod_prod").text();

        location.href = "editar_producto.php?cod_prod="+cod_prod;
    })

    $(".reg_producto .btn_borrar").click(function(e) {
        let cod_prod = $(this).closest(".reg_producto").children(".cod_prod").text();
        let prod = $(this).closest(".reg_producto").children(".prod").text();
    
        $("#md_borrar .lbl_codprod").text(cod_prod);
        $("#md_borrar .lbl_prod").text(prod);
    
        $("#md_borrar .btn_borrar").attr("href", "../controller/ctr_borrar_prod.php?cod_prod=" + cod_prod);
    
        $("#md_borrar").modal("show");
    });
    

    $("#frm_consultar_prod #txt_codprod").focusout(function(e){
        // alert("hola");
        e.preventDefault();

        let codprod = $(this).val();

        if (codprod != "") {
            $.ajax({
                url:"../controller/ctr_consultar_prod.php",
                type: "POST",
                data: {cod_prod: codprod},
                success: function(rpta) {
                    let rp = JSON.parse(rpta);
                    console.log("Respuesta:",rpta);

                    if(rp) {
                        $(".prod").html(rp.producto);
                        $(".stk").html(rp.stock_disponible);
                        $(".cst").html("S/" + rp.costo);
                        $(".gnc").html(rp.ganancia + "%");
                        $(".prc").html("S/" + rp.precio);
                        $(".mar").html(rp.marca);
                        $(".cat").html(rp.categoria);
                    } else {
                        $("#codigoErroneo").text(codprod);
                        $("#modalError").modal("show"); 
    
                        $("#txt_codprod").val("");

                        let vacio = "&nbsp;";

                        $(".prod").html(vacio);
                        $(".stk").html(vacio);
                        $(".cst").html(vacio);
                        $(".gnc").html(vacio);
                        $(".prc").html(vacio);
                        $(".mar").html(vacio);
                        $(".cat").html(vacio);
                        $("#txt_codprod").focus();
                    }
                }
            });
        }
    });

});

