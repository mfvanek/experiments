using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ResumeModels.Models
{
    public interface IPersonRepository
    {
        IEnumerable<Person> GetPersons();
    }
}
