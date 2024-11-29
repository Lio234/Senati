using Microsoft.EntityFrameworkCore;
using app_compuware.Models;

namespace app_compuware.Data
{  
        public class ApplicationDbContext : DbContext
        {

            public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
            { }
            public DbSet<Categoria> Categoria { get; set; }
        }
    
}
