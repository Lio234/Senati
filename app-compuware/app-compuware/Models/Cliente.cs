using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_cliente")]
    public class Cliente
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        public string id_cliente { get; set; }

        [Required(ErrorMessage = "Escriba una categoria")]
        [StringLength(30)]
        public string nombre { get; set; }

        [Required(ErrorMessage = "Escriba su apellido")]
        [StringLength(30)]
        public string ap_paterno { get; set; }

        [Required(ErrorMessage = "Escriba su apellido")]
        [StringLength(30)]
        public string ap_materno { get; set; }

        [Required(ErrorMessage = "Escriba su direccion")]
        [StringLength(30)]
        public string direccion { get; set; }

        [Required(ErrorMessage = "Escriba tu correo")]
        [StringLength(30)]
        public string correo { get; set; }

        [Required(ErrorMessage = "Escriba su telefono")]
        [StringLength(30)]
        public string telefono { get; set; }

        [Required(ErrorMessage = "Seleccione un distrito.")]
        public string id_distrito { get; set; }

        // Relación con Distrito
        [ForeignKey("id_distrito")]
        public Distrito distrito { get; set; }
    }
}
