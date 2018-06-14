using System.Data.Entity;

namespace ResumeModels.Models
{
    public class PersonDbContext : DbContext
    {
        public DbSet<Person> Persons { get; set; }
    }
}
