using System;

namespace WildLife.Plants
{
    public class Carrot : Plant
    {
        public override void GrowUp()
        {
            Console.WriteLine("The carrot is growing up");
        }

        public override string ToString()
        {
            return "Carrot";
        }
    }
}
