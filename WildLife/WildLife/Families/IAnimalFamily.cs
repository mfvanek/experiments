using WildLife.Models;

namespace WildLife.Families
{
    public interface IAnimalFamily
    {
        Animal GetAdultSpecimen(bool isMale);

        Animal GetOldSpecimen(bool isMale);

        Animal GetYoungSpecimen(bool isMale);
    }
}
