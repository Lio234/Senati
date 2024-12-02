using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace app_compuware.Models
{
    [Table("tb_producto")]
    public class Producto
    {
        [Key]
        [Required(ErrorMessage = "El código del producto es obligatorio.")]
        [StringLength(10, ErrorMessage = "El código no debe exceder los 10 caracteres.")]
        [Display(Name = "Id Producto")]
        public string id_producto { get; set; }

        [Required(ErrorMessage = "El nombre del producto es obligatorio.")]
        [StringLength(30, ErrorMessage = "El nombre no debe exceder los 30 caracteres.")]
        [Display(Name = "Producto")]
        public string producto { get; set; }

        [Required(ErrorMessage = "El costo del producto es obligatorio.")]
        [Range(0.01, double.MaxValue, ErrorMessage = "El costo debe ser mayor a 0.")]
        [Display(Name = "Costo")]
        public decimal costo { get; set; }

        [Required(ErrorMessage = "La ganancia del producto es obligatoria.")]
        [Range(0, double.MaxValue, ErrorMessage = "La ganancia debe ser mayor o igual a 0.")]
        [Display(Name = "Ganancia")]
        public decimal ganancia { get; set; }

        [Required(ErrorMessage = "Debe seleccionar una marca.")]
        public string id_marca { get; set; }

        [ForeignKey("id_marca")]
        public Marca Marca { get; set; }

        [Required(ErrorMessage = "Debe seleccionar una categoría.")]
        public string id_categoria { get; set; }

        [ForeignKey("id_categoria")]
        public Categoria Categoria { get; set; }
    }
}