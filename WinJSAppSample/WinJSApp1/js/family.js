(function () {
    "use strict";
    WinJS.Namespace.define("MyFamily", {
        members: new WinJS.Binding.List([
            { id: 1, label: "Me", name: "Ivan" },
            { id: 2, label: "Wife", name: "Alexandra" },
            { id: 3, label: "Son", name: "Saveliy" },
            { id: 4, label: "Daughter", name: "Taisiya" }
        ])
    });
})();