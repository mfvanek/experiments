using System.Collections.Generic;
using WildLife.Animals;

namespace WildLife.Families
{
    public interface IAnimalFamily
    {
        IEnumerable<Animal> GetAll();

        IAnimalFamily Add(Animal animal);
    }
}
