using WildLife.Attacks;

namespace WildLife.Models
{
    public abstract class CarnivorousAnimal : Animal
    {
        private readonly IAttack attackType;

        protected CarnivorousAnimal(IAttack attackType) : base(true)
        {
            this.attackType = attackType;
        }

        protected override void DoAttack(Animal target)
        {
            attackType.Attack(this, target);
        }
    }
}
