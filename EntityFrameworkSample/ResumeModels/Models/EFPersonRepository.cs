using System.Collections.Generic;
using System.Data.Entity;

namespace ResumeModels.Models
{
    public class EFPersonRepository : IPersonRepository
    {
        private readonly PersonDbContext context;

        public EFPersonRepository(PersonDbContext ctx)
        {
            context = ctx;
        }

        public IEnumerable<Person> GetPersons()
        {
            return GetPersonsRaw();
        }

        public int GetPersonsCount()
        {
            int count = GetPersonsRaw().CountAsync().Result;
            return count;
        }

        private DbSet<Person> GetPersonsRaw()
        {
            return context.Persons;
        }
    }
}
