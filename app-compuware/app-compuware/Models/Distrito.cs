using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_distrito")]
    public class Distrito
    {
        [Key]
        [Required]
        [MinLength(50, ErrorMessage = "Escriba un Codigo")]
        [StringLength(5)]
        [Display(Name = "Id Distrito")]

        public string id_distrito { get; set; }

        [Required]
        [MinLength(50, ErrorMessage = "Escriba un Distrito")]
        [Display(Name = "Distrito")]

        public string distrito { get; set; }

        [Required]
        [MinLength(50, ErrorMessage = "Escriba codigo de departamento")]
        [Display(Name = "Id provincia")]

        public string id_provincia { get; set; }

        [ForeignKey("id_provincia")]
        public Provincia Provincia { get; set; }
    }
}