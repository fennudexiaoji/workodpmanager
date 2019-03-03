var app = {
    // Application Constructor
    initialize: function() {
    this.bindEvents();
    },
    bindEvents: function() {
        document.addEventListener('deviceready', this.onDeviceReady, false);
    },
    onDeviceReady: function() {
        app.receivedEvent('deviceready');

        // Here, we redirect to the web site.
        /*var targetUrl = "file:///android_asset/test_test.html";;
        var bkpLink = document.getElementById("bkpLink");
        bkpLink.setAttribute("href", targetUrl);
        bkpLink.text = targetUrl;
        window.location.replace(targetUrl);*/
},
    // Note: This code is taken from the Cordova CLI template.
    receivedEvent: function(id) {

    }
};

app.initialize();