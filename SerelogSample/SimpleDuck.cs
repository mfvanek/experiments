using Serilog;

namespace SerelogSample
{
    class SimpleDuck
    {
        private static readonly ILogger Logger = Log.Logger.ForContext<SimpleDuck>();

        public void Quack()
        {
            Logger.Information("Duck says: \"Quack-quack-quack\"");
        }
    }
}
