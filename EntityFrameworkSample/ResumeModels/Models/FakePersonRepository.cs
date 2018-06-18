using System;
using System.Collections.Generic;
using System.Globalization;

namespace ResumeModels.Models
{
    public class FakePersonRepository : IPersonRepository
    {
        static string tpl = "dd.MM.yyyy";

        public IEnumerable<Person> GetPersons()
        {
            return GetPersonsRaw();
        }

        public int GetPersonsCount()
        {
            return GetPersonsRaw().Count;
        }

        private IList<Person> GetPersonsRaw()
        {
            var culture = CultureInfo.InvariantCulture;
            return new List<Person>() {
                new Person {
                    PersonId = 1, FirstName = "Ivan", LastName = "Vakhrushev", BirthDate = DateTime.ParseExact("02.12.1986", tpl, culture),
                    Resumes = new List<Resume>() { new Resume {Summary = "Father, husband and developer"} }
                },
                new Person {PersonId = 2, FirstName = "Alexandra", LastName = "Shatova", BirthDate = DateTime.ParseExact("11.05.1987", tpl, culture)},
                new Person {PersonId = 3, FirstName = "Taisiya", LastName = "Shatova", BirthDate = DateTime.ParseExact("26.08.2011", tpl, culture)},
                new Person {PersonId = 4, FirstName = "Saveliy", LastName = "Vakhrushev", BirthDate = DateTime.ParseExact("18.06.2016", tpl, culture)}
            };
        }
    }
}
