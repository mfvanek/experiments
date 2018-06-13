using Serilog;
using System;

namespace SerelogSample
{
    class Program
    {
        private const int COUNT = 1000;
        private const int THRESHOLD = COUNT + 1; // decrease this value to get log file in app folder
        private static readonly ILogger Logger;

        static Program()
        {
            const string template = "{Timestamp:yyyy-MM-dd HH:mm:ss.fff zzz} [{Level:u3}] [{SourceContext:l}] {Message:lj} <{ThreadId}>{NewLine}{Exception}";
            var config = new LoggerConfiguration();
            config.MinimumLevel.Debug()
                .Enrich.WithThreadId()
                .WriteTo.Console(outputTemplate: template)
                .WriteTo.File("mySerilogTest.log", rollingInterval: RollingInterval.Day, outputTemplate: template);
            Log.Logger = config.CreateLogger();
            Logger = Log.Logger.ForContext<Program>();
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Testing Serilog");
            for (uint i = 1; i <= COUNT; ++i)
            {
                Logger.Information($"counter = {i}");
            }
            new SimpleDuck().Quack();
            Console.WriteLine("Test is done. Press any key and then check app folder");
            Console.ReadKey();
        }
    }
}
