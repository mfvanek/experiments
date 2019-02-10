using System;
using WildLife.Attacks;
using WildLife.Lifecycles;

namespace WildLife.Animals
{
    public sealed class Wolf : CarnivorousAnimal
    {
        private static Wolf adultMale = null;

        private Wolf(bool isMale, int age)
            : base(WolfLifecycle.One, new SilentAttack(), isMale, age)
        {}

        public string Howl()
        {
            Console.WriteLine("The wolf is howling");
            return "Whooooooooooo";
        }

        public override string Cry()
        {
            return Howl();
        }

        public override double Jump()
        {
            Console.WriteLine("The wolf is jumping");
            return 5.6d;
        }

        public override double Run()
        {
            Console.WriteLine("The wolf is running");
            return 12.5d;
        }

        public override void Bite(Animal target)
        {
            Console.WriteLine("The wolf is biting target");
        }

        public override string ToString()
        {
            return $"{Generation} " + (IsMale ? "male" : "female") + " wolf";
        }

        public static Wolf GetAdultMale()
        {
            if (adultMale == null)
            {
                adultMale = new Wolf(true, WolfLifecycle.One.GetOldAge() - 1);
            }
            return adultMale;
        }

        public static Wolf GetAdultFemale()
        {
            return new Wolf(false, WolfLifecycle.One.GetOldAge() - 1);
        }

        public static Wolf GetYoungMale()
        {
            return new Wolf(true, WolfLifecycle.One.GetAdultAge() - 1);
        }

        public static Wolf GetYoungFemale()
        {
            return new Wolf(false, WolfLifecycle.One.GetAdultAge() - 1);
        }
    }
}
