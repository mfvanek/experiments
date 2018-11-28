using System;
using WildLife.Lifecycles;

namespace WildLife.Models
{
    public class Rabbit : HerbivorousAnimal
    {
        private Rabbit(bool isMale, int age)
            : base(RabbitLifecycle.Instance, isMale, age)
        {}

        public string Squeal()
        {
            return "wiiiiiiii";
        }

        public override string Cry()
        {
            return this.Squeal();
        }

        /// <summary>
        /// Jump
        /// </summary>
        /// <returns>Returns the length of the jump in meters</returns>
        public override double Jump()
        {
            Console.WriteLine("The rabbit is jumping very fast!");
            return 5.0d;
        }

        /// <summary>
        /// Run
        /// </summary>
        /// <returns>Returns the running speed in km\h</returns>
        public override double Run()
        {
            return this.Jump();
        }

        public override void Bite(Animal target)
        {
            Console.WriteLine("The rabbit doesn't bite anyone");
        }
    }
}
