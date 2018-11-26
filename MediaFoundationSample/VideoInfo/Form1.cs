using Microsoft.WindowsAPICodePack.Dialogs;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Windows.Forms;

namespace VideoInfo
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void openFolderToolStripMenuItem_Click(object sender, EventArgs e)
        {
            using (var dialog = new CommonOpenFileDialog())
            {
                dialog.InitialDirectory = "C:\\Users";
                dialog.IsFolderPicker = true;
                if (dialog.ShowDialog() == CommonFileDialogResult.Ok)
                {
                    // MessageBox.Show("You selected: " + dialog.FileName);
                    textBox1.Text = dialog.FileName;
                    var mediaFiles = GetFiles(dialog.FileName);
                    var items = mediaFiles.Select(i => new ListViewItem(new string[] {i})).ToArray();
                    listView1.Items.Clear();
                    listView1.Items.AddRange(items);
                }
            }
        }

        private List<string> GetFiles(string rootFolder)
        {
            // see https://docs.microsoft.com/en-us/windows/desktop/medfound/supported-media-formats-in-media-foundation
            string[] allSupportedMediaTypes =
            {
                "*.3g2",
                "*.3gp",
                "*.3gp2",
                "*.3gpp",
                "*.asf",
                "*.wma",
                "*.wmv",
                "*.aac",
                "*.adts",
                "*.avi",
                "*.mp3",
                "*.wav",
                "*.m4a",
                "*.m4v",
                "*.mov",
                "*.mp4",
                "*.sami",
                "*.smi"
            };
            return new List<string>(GetFiles(rootFolder, allSupportedMediaTypes));
        }

        private static IEnumerable<string> GetFiles(string path, string[] searchPatterns)
        {
            return searchPatterns.AsParallel().SelectMany(searchPattern => Directory.EnumerateFiles(path, searchPattern, SearchOption.AllDirectories));
        }
    }
}
