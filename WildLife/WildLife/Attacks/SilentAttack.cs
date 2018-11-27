using WildLife.Models;

namespace WildLife.Attacks
{
    internal class SilentAttack : IAttack
    {
        public void Attack(Animal attacker, Animal attacked)
        {
            attacker.Run();
            attacker.Jump();
            attacker.Bite(attacked);
        }
    }
}
