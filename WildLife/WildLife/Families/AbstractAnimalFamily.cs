using System.Collections.Generic;
using System.Collections.ObjectModel;
using WildLife.Animals;

namespace WildLife.Families
{
    public abstract class AbstractAnimalFamily : IAnimalFamily
    {
        private readonly List<Animal> animals = new List<Animal>();

        public IEnumerable<Animal> GetAll()
        {
            return new ReadOnlyCollection<Animal>(animals);
        }

        public IAnimalFamily Add(Animal animal)
        {
            animals.Add(animal);
            return this;
        }

        public abstract Animal GetAdultSpecimen(bool isMale);
        public abstract Animal GetOldSpecimen(bool isMale);
        public abstract Animal GetYoungSpecimen(bool isMale);
    }
}
