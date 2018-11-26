using MediaFoundation;
using MediaFoundation.Misc;
using Microsoft.WindowsAPICodePack.Dialogs;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace VideoInfo
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();

            if (DesignMode == false)
            {
                // we always have to initialize MF. The 0x00020070 here is the WMF version 
                // number used by the MF.Net samples. Not entirely sure if it is appropriate
                HResult hr = MFExtern.MFStartup(0x00020070, MFStartup.Full);
                Validate(hr);
            }
        }

        private void openFolderToolStripMenuItem_Click(object sender, EventArgs e)
        {
            using (var dialog = new CommonOpenFileDialog())
            {
                dialog.InitialDirectory = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);
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

        private void LogMessage(string message)
        {
            Console.WriteLine(message);
        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            LogMessage("MainForm_FormClosing");
            try
            {
                // Shut down MF
                MFExtern.MFShutdown();
            }
            catch (Exception ex)
            {
                LogMessage(ex.Message);
            }
        }

        private void listView1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                if (listView1.FocusedItem.Bounds.Contains(e.Location))
                {
                    contextMenuStrip1.Show(Cursor.Position);
                }
            }
        }

        /// <summary>
        /// Based on https://docs.microsoft.com/en-us/windows/desktop/medfound/shell-metadata-providers
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void infoToolStripMenuItem_Click(object sender, EventArgs e)
        {
            IMFSourceResolver pSourceResolver = null;
            IMFMediaSource pSource = null;
            object pPropsObject = null;
            var url = this.listView1.SelectedItems[0].Text;
            try
            {
                // Create the source resolver.
                HResult hr = MFExtern.MFCreateSourceResolver(out pSourceResolver);
                Validate(hr);
                if (pSourceResolver == null)
                {
                    throw new Exception("pSourceResolver is null");
                }
                // Get a pointer to the IMFMediaSource interface of the media source.
                hr = pSourceResolver.CreateObjectFromURL(url, MFResolution.MediaSource, null, out pSource);
                Validate(hr);
                if (pSource == null)
                {
                    throw new Exception("pSource is null");
                }
                
                hr = MFExtern.MFGetService(pSource, MFServices.MF_PROPERTY_HANDLER_SERVICE, typeof(IPropertyStore).GUID, out pPropsObject);
                Validate(hr);
                if (pPropsObject == null)
                {
                    throw new Exception("pPropsObject is null");
                }
                IPropertyStore pProps = pPropsObject as IPropertyStore;

                hr = pProps.GetCount(out int cProps);
                Validate(hr);

                for (int i = 0; i < cProps; ++i)
                {
                    PropertyKey key = new PropertyKey();
                    hr = pProps.GetAt(i, key); // ??? TODO check it!
                    Validate(hr);

                    using (PropVariant pv = new PropVariant())
                    {
                        hr = pProps.GetValue(key, pv); // ??? TODO check it!
                        Validate(hr);
                        DisplayProperty(key, pv);
                    }
                }
            }
            finally
            {
                if (pSource != null)
                {
                    Marshal.ReleaseComObject(pSource);
                }
                if (pSourceResolver != null)
                {
                    Marshal.ReleaseComObject(pSourceResolver);
                }
                if (pPropsObject != null)
                {
                    Marshal.ReleaseComObject(pPropsObject);
                }
            }
            // MessageBox.Show(url);
        }

        private void DisplayProperty(PropertyKey key, PropVariant pv)
        {
            MessageBox.Show($"{key.pID} {key.fmtid} - {pv.ToString()}");
        }

        private static void Validate(HResult hr)
        {
            if (hr != HResult.S_OK)
            {
                throw new Exception("Error = " + hr);
            }
        }
    }
}
