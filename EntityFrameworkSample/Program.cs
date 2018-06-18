using Ninject;
using ResumeModels.Models;
using System;

namespace EntityFrameworkSample
{
    class Program
    {
        static readonly IKernel kernel = new StandardKernel(new ResumeModule());

        static void Main(string[] args)
        {
            var persons = kernel.Get<IPersonRepository>().GetPersons();
            foreach (var person in persons)
            {
                 Console.WriteLine($"{person.FullName} is {person.Age} year(s) old and has {person.Resumes?.Count ?? 0} resume(s)");
            }

            if (IsNeedInitializeDb())
            {
                InitDb();
            }
            Console.ReadKey();
        }

        private static bool IsNeedInitializeDb()
        {
            return kernel.Get<IPersonRepository>().GetPersonsCount() <= 0;
        }


        private static void InitDb()
        {
            kernel.Get<DbInitializer>().Init();
        }
    }
}
