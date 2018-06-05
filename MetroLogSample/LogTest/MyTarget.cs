using MetroLog;
using MetroLog.Layouts;
using MetroLog.Targets;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace LogTest
{
    class MyTarget : BufferedTarget
    {
        public MyTarget(int threshold) : base(new SingleLineLayout(), threshold) { }

        protected override async Task DoFlushAsync(LogWriteContext context, IEnumerable<LogEventInfo> toFlush)
        {
            var logInformation = toFlush.ToList().Aggregate(string.Empty,
                (current, eventInfo) => $"{current}{eventInfo.SequenceID}|{eventInfo.TimeStamp}|{eventInfo.Level}|{eventInfo.Logger}|{eventInfo.Message} {eventInfo.ExceptionWrapper?.AsString}{Environment.NewLine}");

            using (StreamWriter sourceStream = File.CreateText("newfile.txt")) // TODO pass fileName
            {
                await sourceStream.WriteAsync(logInformation);
            };
        }
    }
}