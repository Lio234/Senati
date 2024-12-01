using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_distrito")]
    public class Distrito
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [MinLength(5, ErrorMessage = "Escriba un codigo mas corto")]
        [Display(Name = "Id distrito")]
        public String id_distrito { get; set; }

        [Required(ErrorMessage = "Escriba su distrito")]
        [StringLength(30)]
        [Display(Name = "Distrito")]
        public String distrito { get; set; }

        [Required(ErrorMessage = "Seleccione una provincia.")]
        [Display(Name = "Id Provincia")]
        public string id_provincia { get; set; }

        // Relación con Provincia
        [ForeignKey("id_provincia")]
        public Provincia provincia { get; set; }

        // Propiedad de navegación
        public ICollection<Cliente> Clientes { get; set; }
    }
}