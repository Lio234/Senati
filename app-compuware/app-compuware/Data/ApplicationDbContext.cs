using Microsoft.EntityFrameworkCore;
using app_compuware.Models;

namespace app_compuware.Data
{
    public class ApplicationDbContext : DbContext
    {

        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        { }
        public DbSet<Categoria> Categoria { get; set; }
        public DbSet<Marca> Marca { get; set; }

        public DbSet<Cliente> Cliente { get; set; }
        public DbSet<Departamento> Departamento { get; set; }
        public DbSet<Provincia> Provincia { get; set; }
        public DbSet<Distrito> Distrito { get; set; }
        public DbSet<Pedido> Pedido { get; set; }

    }

}
