using Serilog;
using Serilog.Configuration;
using Serilog.Formatting.Display;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SerelogSample
{
    public static class MySinkLoggerConfigurationExtensions
    {
        const string DefaultConsoleOutputTemplate = "[{Timestamp:HH:mm:ss} {Level:u3}] {Message:lj}{NewLine}{Exception}";

        public static LoggerConfiguration MySink(this LoggerSinkConfiguration sinkConfiguration,
            int queueLimit,
            string outputTemplate = DefaultConsoleOutputTemplate,
            IFormatProvider formatProvider = null)
        {
            var formatter = new MessageTemplateTextFormatter(outputTemplate, formatProvider);
            return sinkConfiguration.Sink(new MySink(queueLimit, formatter));
        }
    }
}
