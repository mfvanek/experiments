// For an introduction to the Blank template, see the following documentation:
// https://go.microsoft.com/fwlink/?LinkId=232509

(function () {
	"use strict";

	var app = WinJS.Application;
	var activation = Windows.ApplicationModel.Activation;
    var isFirstActivation = true;

    var mySplitView = window.mySplitView = {
        splitView: null,
        homeClicked: WinJS.UI.eventHandler(function (ev) {
            document.getElementById("worldFlyout").winControl.show(this);
        }),
        familyClicked: WinJS.UI.eventHandler(function (ev) {
            document.getElementById("worldFlyout").winControl.show(this);
        }),
    };

	app.onactivated = function (args) {
		if (args.detail.kind === activation.ActivationKind.voiceCommand) {
			// TODO: Handle relevant ActivationKinds. For example, if your app can be started by voice commands,
			// this is a good place to decide whether to populate an input field or choose a different initial view.
		}
		else if (args.detail.kind === activation.ActivationKind.launch) {
			// A Launch activation happens when the user launches your app via the tile
			// or invokes a toast notification by clicking or tapping on the body.
			if (args.detail.arguments) {
				// TODO: If the app supports toasts, use this value from the toast payload to determine where in the app
				// to take the user in response to them invoking a toast notification.
			}
			else if (args.detail.previousExecutionState === activation.ApplicationExecutionState.terminated) {
				// TODO: This application had been suspended and was then terminated to reclaim memory.
				// To create a smooth user experience, restore application state here so that it looks like the app never stopped running.
				// Note: You may want to record the time when the app was last suspended and only restore state if they've returned after a short period.
			}
		}

		if (!args.detail.prelaunchActivated) {
			// TODO: If prelaunchActivated were true, it would mean the app was prelaunched in the background as an optimization.
			// In that case it would be suspended shortly thereafter.
			// Any long-running operations (like expensive network or disk I/O) or changes to user state which occur at launch
			// should be done here (to avoid doing them in the prelaunch case).
			// Alternatively, this work can be done in a resume or visibilitychanged handler.
		}

		if (isFirstActivation) {
			// TODO: The app was activated and had not been running. Do general startup initialization here.
			document.addEventListener("visibilitychange", onVisibilityChanged);
            args.setPromise(WinJS.UI.processAll().then(function () {
                document.getElementById("showButton").addEventListener("click", showWorldFlyout, false);
                document.getElementById("closeButton").addEventListener("click", closeWorldFlyout, false);
                //document.getElementById("worldFlyout").addEventListener("afterhide", onDismiss, false);
                document.getElementById("ratingControlHost").addEventListener("change", ratingChanged, false);
            }).done(function () {
                mySplitView.splitView = document.querySelector(".splitView").winControl;
                new WinJS.UI._WinKeyboard(mySplitView.splitView.paneElement);
            }));
		}

		isFirstActivation = false;
	};

	function onVisibilityChanged(args) {
		if (!document.hidden) {
			// TODO: The app just became visible. This may be a good time to refresh the view.
		}
    }

    function showWorldFlyout() {
        let showButton = document.getElementById("showButton");
        document.getElementById("worldFlyout").winControl.show(showButton);
    }

    function closeWorldFlyout() {
        document.getElementById("worldFlyout").winControl.hide();
    }

    function ratingChanged(event) {
        //let showButton = document.getElementById("showButton");
        document.getElementById("worldFlyout").winControl.show(this);
    }

	app.oncheckpoint = function (args) {
		// TODO: This application is about to be suspended. Save any state that needs to persist across suspensions here.
		// You might use the WinJS.Application.sessionState object, which is automatically saved and restored across suspension.
		// If you need to complete an asynchronous operation before your application is suspended, call args.setPromise().
    };

	app.start();

})();
