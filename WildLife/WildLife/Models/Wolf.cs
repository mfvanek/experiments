using System;

namespace WildLife.Models
{
    public class Wolf : CarnivorousAnimal
    {
        public Wolf() {}

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

        public override bool Attack(Animal target)
        {
            this.Run();
            this.Jump();
            this.Bite(target);
            return this.Eat(target);
        }
    }
}
