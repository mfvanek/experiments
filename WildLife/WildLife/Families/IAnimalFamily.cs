using System.Collections.Generic;
using WildLife.Animals;

namespace WildLife.Families
{
    public interface IAnimalFamily : IEnumerable<Animal>
    {
        IAnimalFamily Add(Animal animal);

        IEnumerable<Animal> GetAll();
    }
}
