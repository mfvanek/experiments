using Serilog.Events;
using Serilog.Formatting;
using Serilog.Sinks.PeriodicBatching;
using System;
using System.Collections.Generic;
using System.IO;
using System.Threading.Tasks;

namespace SerelogSample
{
    public class MySink: PeriodicBatchingSink
    {
        private ITextFormatter _formatter;

        public MySink(int batchSizeLimit, ITextFormatter formatter) : base(batchSizeLimit, TimeSpan.FromMinutes(1.0))
        {
            _formatter = formatter;
        }

        //protected override void EmitBatch(IEnumerable<LogEvent> events)
        //{
        //    using (StreamWriter sourceStream = File.CreateText("newfile.txt")) // TODO pass fileName
        //    {
        //        foreach (var logEvent in events)
        //        {
        //            _formatter.Format(logEvent, sourceStream);
        //        }
        //    };
        //}

        protected override async Task EmitBatchAsync(IEnumerable<LogEvent> events)
        {
            using (StreamWriter sourceStream = File.AppendText("newfile.txt")) // TODO pass fileName
            {
                foreach (var logEvent in events)
                {
                    _formatter.Format(logEvent, sourceStream);
                }
                await sourceStream.FlushAsync();
            };
        }
    }
}
