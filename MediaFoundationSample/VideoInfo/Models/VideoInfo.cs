namespace MF.MediaInfo.Models
{
    public class VideoInfo
    {
        public string Compression { get; set; }

        public string Director { get; set; }

        public uint EncodingBitrate { get; set; }

        public uint FrameHeight { get; set; }

        public uint FrameWidth { get; set; }

        public uint FrameRate { get; set; }

        public double RealFrameRate { get => FrameRate / 1000.0d; }

        public uint HorizontalAspectRatio { get; set; }

        public uint VerticalAspectRatio { get; set; }

        public uint TotalBitrate { get; set; }

        public uint StreamNumber { get; set; }

        public override string ToString()
        {
            return $"Identifier of the video stream = {StreamNumber};\nTotal data rate for all video and audio streams, in bits per second = {TotalBitrate};\nThe horizontal component of the pixel aspect ratio = {HorizontalAspectRatio};\nThe vertical component of the pixel aspect ratio = {VerticalAspectRatio}";
        }
    }
}
