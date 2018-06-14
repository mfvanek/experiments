using System.Collections.Generic;

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
            return context.Persons;
        }
    }
}
