using ResumeModels.Models;
using System;

namespace EntityFrameworkSample
{
    class DbInitializer
    {
        private IPersonRepository repository;

        public DbInitializer(IPersonRepository _repository)
        {
            repository = _repository;
        }

        public void Init()
        {
            using (var db = new PersonDbContext())
            {
                var persons = repository.GetPersons();
                db.Persons.AddRange(persons);
                db.SaveChanges();
                Console.WriteLine("Data saved");
            }
        }
    }
}
