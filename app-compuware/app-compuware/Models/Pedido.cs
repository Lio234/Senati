using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_pedido")]
    public class Pedido
    {
        [Key]
        [Required(ErrorMessage = "Escriba un codigo valido")]
        [MinLength(5, ErrorMessage = "Escriba un codigo mas corto")]
        [Display(Name = "Id Pedido")]
        public String id_pedido { get; set; }

        [Required(ErrorMessage = "Ponga una fecha correcta")]
        [DataType(DataType.Date)]
        [Display(Name = "Fecha")]
        public DateTime fecha { get; set; }

        [Required(ErrorMessage = "Ponga un Total")]
        [Display(Name = "Total")]
        public double total { get; set; }

        [Required(ErrorMessage = "Seleccione un Cliente")]
        public string id_cliente { get; set; }
        [ForeignKey("id_cliente")]
        public Cliente cliente { get; set; }
    }
}