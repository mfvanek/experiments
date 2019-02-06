using System.Collections.Generic;
using WildLife.Animals;

namespace WildLife.Families
{
    public interface IAnimalFamily
    {
        Animal GetAdultSpecimen(bool isMale);

        Animal GetOldSpecimen(bool isMale);

        Animal GetYoungSpecimen(bool isMale);

        IEnumerable<Animal> GetAll();

        IAnimalFamily Add(Animal animal);
    }
}
