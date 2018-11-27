using System;
using WildLife.Models;

namespace WildLife.Families
{
    public class WolfFamily : IAnimalFamily
    {
        public Animal GetAdultSpecimen(bool isMale)
        {
            return isMale ? Wolf.GetAdultMale() : Wolf.GetAdultFemale();
        }

        public Animal GetOldSpecimen(bool isMale)
        {
            throw new NotSupportedException("Old wolves don't survive");
        }

        public Animal GetYoungSpecimen(bool isMale)
        {
            return new Wolf();
        }
    }
}
