(function () {
    "use strict";

    WinJS.Namespace.define("MyToolBar", {
        commandList: null,
        outputCommand: WinJS.UI.eventHandler(function (ev) {
            var status = document.querySelector(".status");
            var command = ev.currentTarget;
            if (command.winControl) {
                var label = command.winControl.label || command.winControl.icon || "button";
                var section = command.winControl.section || "";
                var msg = section + " command " + label + " was pressed";
                status.textContent = msg;
            }
        })
    });

    var dataArray = [
        new WinJS.UI.Command(null, { id: 'cmdEdit', label: 'edit', section: 'primary', type: 'button', icon: 'edit', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdDelete', label: 'delete', section: 'primary', type: 'button', icon: 'delete', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdFavorite', label: 'favorite', section: 'primary', type: 'toggle', icon: 'favorite', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdOpenWith', label: 'open with', section: 'primary', type: 'button', icon: 'openfile', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdDownload', label: 'download', section: 'primary', type: 'button', icon: 'download', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdPin', label: 'pin', section: 'primary', type: 'button', icon: 'pin', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdZoom', label: 'zoom', section: 'primary', type: 'button', icon: 'zoomin', onclick: MyToolBar.outputCommand }),
        new WinJS.UI.Command(null, { id: 'cmdFullscreen', label: 'full screen', section: 'primary', type: 'button', icon: 'fullscreen', onclick: MyToolBar.outputCommand })
    ];

    MyToolBar.commandList = new WinJS.Binding.List(dataArray);
})();