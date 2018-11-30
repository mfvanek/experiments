namespace MF.MediaInfo.Models
{
    public class AudioInfo
    {
        public uint ChannelCount { get; set; }
        public uint EncodingBitrate { get; set; }
        public string Format { get; set; }
        public bool IsVariableBitRate { get; set; }
        public uint SampleRate { get; set; }
        public uint SampleSize { get; set; }
        public uint StreamNumber { get; set; }

        public override string ToString()
        {
            return $"Number of audio channels = {ChannelCount};\nAverage audio bit rate, in bits per second = {EncodingBitrate};\nAudio sample rate in samples per second = {SampleRate};\n" +
                $"Number of bits per audio sample  = {SampleSize};\nUses variable bit-rate encoding = {IsVariableBitRate};\nIdentifier of the audio stream = {StreamNumber}";
        }
    }
}
