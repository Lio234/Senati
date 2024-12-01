using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_departamento")]
    public class Departamento
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [MinLength(5, ErrorMessage = "Escriba un codigo mas corto")]
        [Display(Name = "Id departamento")]
        public String id_departamento { get; set; }

        [Required(ErrorMessage = "Escriba su departamento")]
        [StringLength(30)]
        [Display(Name = "Departamento")]
        public String departamento { get; set; }

        // Propiedad de navegación
        public ICollection<Provincia> Provincias { get; set; }
    }
}