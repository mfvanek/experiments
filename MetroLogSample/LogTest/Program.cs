using MetroLog;
using System;

namespace LogTest
{
    class Program
    {
        private const int COUNT = 1000;
        private const int THRESHOLD = COUNT + 1; // decrease this value to get log file in app folder
        private static readonly ILogger Logger;

        static Program()
        {
            var config = new LoggingConfiguration();
            config.AddTarget(LogLevel.Trace, LogLevel.Fatal, new MyTarget(THRESHOLD));
            Logger = LogManagerFactory.CreateLogManager(config).GetLogger("MyTestLog");
        }

        static void Main(string[] args)
        {
            Console.WriteLine("Testing MyTarget");
            for (uint i = 1; i <= COUNT; ++i)
            {
                Logger.Info($"counter = {i}");
            }
            Console.WriteLine("Test is done. Press any key and then check app folder");
            Console.ReadKey();
        }
    }
}