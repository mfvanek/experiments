using WildLife.Models;

namespace WildLife.Attacks
{
    public interface IAttack
    {
        void Attack(Animal attacker, Animal attacked);
    }
}
