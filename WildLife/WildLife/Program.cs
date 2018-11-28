using System;
using System.Collections.Generic;
using WildLife.Families;
using WildLife.Models;

namespace WildLife
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("Hello WildLife!");
            List<Animal> wolfPack = new List<Animal>()
            {
                WolfFamily.Instance.GetAdultSpecimen(true),
                WolfFamily.Instance.GetAdultSpecimen(false),
                WolfFamily.Instance.GetAdultSpecimen(false),
                WolfFamily.Instance.GetYoungSpecimen(true),
                WolfFamily.Instance.GetYoungSpecimen(true),
                WolfFamily.Instance.GetYoungSpecimen(true),
                WolfFamily.Instance.GetYoungSpecimen(true),
                WolfFamily.Instance.GetYoungSpecimen(false),
                WolfFamily.Instance.GetYoungSpecimen(false),
                WolfFamily.Instance.GetYoungSpecimen(false),
                WolfFamily.Instance.GetYoungSpecimen(false),
            };

            wolfPack.ForEach(w => Console.WriteLine(w));

            Console.ReadKey();
        }
    }
}
