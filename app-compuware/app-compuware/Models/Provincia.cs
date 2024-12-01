using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_provincia")]
    public class Provincia
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [MinLength(5, ErrorMessage = "Escriba un codigo mas corto")]
        [Display(Name = "Id provincia")]
        public String id_provincia{ get; set; }

        [Required(ErrorMessage = "Escriba su provincia")]
        [StringLength(30)]
        [Display(Name = "Provincia")]
        public String provincia { get; set; }

        [Required(ErrorMessage = "Seleccione un departamento.")]
        [Display(Name = "Id Departamento")]
        public string id_departamento { get; set; }

        // Relación con Departamento
        [ForeignKey("id_departamento")]
        public Categoria departamento { get; set; }

        // Propiedad de navegación
        public ICollection<Distrito> Distritos { get; set; }
    }
}