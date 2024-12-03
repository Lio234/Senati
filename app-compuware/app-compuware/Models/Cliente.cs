using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_cliente")]
    public class Cliente
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [Display(Name = "Id Cliente")]

        public string id_cliente { get; set; }

        [Required(ErrorMessage = "Escriba un Nombre")]
        [Display(Name = "Nombre")]

        [StringLength(30)]
        public string nombre { get; set; }

        [Required(ErrorMessage = "Escriba su apellido")]
        [Display(Name = "Apellido Paterno")]

        [StringLength(30)]
        public string ap_paterno { get; set; }

        [Required(ErrorMessage = "Escriba su apellido")]
        [Display(Name = "Apellido Materno")]

        [StringLength(30)]
        public string ap_materno { get; set; }

        [Required(ErrorMessage = "Escriba su direccion")]
        [Display(Name = "Dirección")]

        [StringLength(30)]
        public string direccion { get; set; }

        [Required(ErrorMessage = "Escriba tu correo")]
        [Display(Name = "Correo")]

        [StringLength(30)]
        public string correo { get; set; }

        [Required(ErrorMessage = "Escriba su telefono")]
        [Display(Name = "Telefono")]

        [StringLength(30)]
        public string telefono { get; set; }

        [Required(ErrorMessage = "Seleccione un distrito")]
        [Display(Name = "Distrito")]

        public string id_distrito { get; set; }
        // Relación con Distrito
        [ForeignKey("id_distrito")]
        public Distrito distrito { get; set; }
    }   
}

