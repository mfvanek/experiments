using WildLife.Attacks;
using WildLife.Lifecycles;

namespace WildLife.Animals
{
    public abstract class HerbivorousAnimal : Animal
    {
        private readonly IAttack attackType = new NoAttack();

        protected HerbivorousAnimal(ILifecycle lifecycle, bool isMale, int age)
            : base(lifecycle, false, isMale, age)
        {}

        protected override void DoAttack(Animal target)
        {
            attackType.Attack(this, target);
        }
    }
}
