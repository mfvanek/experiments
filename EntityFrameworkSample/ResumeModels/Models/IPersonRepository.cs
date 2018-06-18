using System.Collections.Generic;

namespace ResumeModels.Models
{
    public interface IPersonRepository
    {
        IEnumerable<Person> GetPersons();

        int GetPersonsCount();
    }
}
