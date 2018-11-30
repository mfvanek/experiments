namespace MF.MediaInfo.Extensions
{
    public static class PropertyKeyExt
    {
        public static bool IsEqual(this MediaFoundation.Misc.PropertyKey lhs, Microsoft.WindowsAPICodePack.Shell.PropertySystem.PropertyKey rhs)
        {
            return lhs.pID == rhs.PropertyId && lhs.fmtid == rhs.FormatId;
        }
    }
}
