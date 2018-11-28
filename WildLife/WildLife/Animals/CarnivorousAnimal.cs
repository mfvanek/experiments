using WildLife.Attacks;
using WildLife.Lifecycles;

namespace WildLife.Animals
{
    public abstract class CarnivorousAnimal : Animal
    {
        private readonly IAttack attackType;

        protected CarnivorousAnimal(ILifecycle lifecycle, IAttack attackType, bool isMale, int age)
            : base(lifecycle, true, isMale, age)
        {
            this.attackType = attackType;
        }

        protected override void DoAttack(Animal target)
        {
            attackType.Attack(this, target);
        }
    }
}
