using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_distrito")]
    public class Distrito
    {
        [Key]
        [Required]
        [StringLength(5)]
        public string id_distrito { get; set; }

        [Required]
        [StringLength(50)]
        public string distrito { get; set; }

        [Required]
        [StringLength(5)]
        public string id_provincia { get; set; }

        [ForeignKey("id_provincia")]
        public Provincia Provincia { get; set; }
    }
}