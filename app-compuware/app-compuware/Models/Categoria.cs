using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_categoria")]
    public class Categoria
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [MinLength(5, ErrorMessage = "Escriba un codigo mas corto")]
        [Display(Name = "Id categoria")]
        public String id_categoria { get; set; }

        [Required(ErrorMessage = "Escriba una categoria")]
        [StringLength(30)]
        [Display(Name = "Nombre")]
        public String categoria { get; set; }
    }
}