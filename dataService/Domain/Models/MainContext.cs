using Microsoft.EntityFrameworkCore;


namespace dataService.Domain.Models {

    public class MainContext : DbContext {

        public MainContext(DbContextOptions<MainContext> options) : base(options) {}

        public DbSet<sponsors> sponsors {get; set;}
        public DbSet<actions> actions{get; set;}
        


    }

}