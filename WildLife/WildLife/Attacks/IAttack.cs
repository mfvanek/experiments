using WildLife.Animals;

namespace WildLife.Attacks
{
    public interface IAttack
    {
        void Attack(Animal attacker, Animal attacked);
    }
}
