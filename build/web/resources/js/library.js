function showProgress(data) {

    if (data.status == "begin") {
        document.getElementById('loading_wrapper').style.display = "block";
    } else if (data.status == "success") {
        document.getElementById('loading_wrapper').style.display = "none";
    }
}

function check(event) {
    if (event.keyCode == 32) {
        return false;
    }
}

var j = true;
document.querySelector(".project").addEventListener("click", function () {
    if (j) {
        document.querySelector(".ul_class").style.display = "block";
        j = false;
    } else {
        document.querySelector(".ul_class").style.display = "none";
        j = true;
    }
});

function blo() {
    if (j) {
        document.querySelector(".ul_class").style.display = "block";
        j = false;
    } else {
        document.querySelector(".ul_class").style.display = "none";
        j = true;
    }
}

 /**
 *  JSF view state fix
 */
jsf.ajax.addOnEvent(function (data) {
    if (data.status == "success") {
        var viewState = getViewState(data.responseXML);

        for (var i = 0; i < document.forms.length; i++) {
            var form = document.forms[i];

            if (form.method == "post" && !hasViewState(form)) {
                createViewState(form, viewState);
            }
        }
    }
});

function getViewState(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (updates[i].getAttribute("id").match(/^([\w]+:)?javax\.faces\.ViewState(:[0-9]+)?$/)) {
            return updates[i].firstChild.nodeValue;
        }
    }

    return null;
}

function hasViewState(form) {
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].name == "javax.faces.ViewState") {
            return true;
        }
    }

    return false;
}

function createViewState(form, viewState) {
    var hidden;

    try {
        hidden = document.createElement("<input name='javax.faces.ViewState'>"); // IE6-8.
    } catch (e) {
        hidden = document.createElement("input");
        hidden.setAttribute("name", "javax.faces.ViewState");
    }

    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("value", viewState);
    hidden.setAttribute("autocomplete", "off");
    form.appendChild(hidden);
}