let tok = localStorage.getItem("token");        //kræver token for at kunne tilgå siden
if (!tok) {
    window.location.href = "LoginSide.html"
}      //hvis token mangler, vil man bliver navigeret til loginsiden
var chart = ""

async function findEKGSessions() {
    cpr = document.getElementById("CPRBoks").value;
    console.log(cpr)

    const sessionsID = await fetch("data/ekgSessions?cpr=" + cpr,
        {
            headers: {
                //"Authorization": localStorage.getItem("token"),
                "Authorization": "hemmeliglogin",
                "Accept": "application/json"
            },
            method: "GET"
        });
    let json = await sessionsID.json();
    //let session = [];
    let Start;
    let sessionID;
    let container;
    console.log(json)
    //document.getElementById("searchFieldArea").innerHTML += JSON.stringify(json);
    try {
        document.getElementById("searchFieldArea").innerHTML += "Session tilhørende cpr: " + cpr + "<br/>";
        for (let i = 0; i < json.ekgListe.length; i++) {
            //session = json.ekgListe[i];
            sessionID = (" SessionID: " + json.ekgListe[i].sessionID)
            Start = (" Dato: " + json.ekgListe[i].start)
            container = Start + sessionID + "<br/>";
            document.getElementById("searchFieldArea").innerHTML += container;
        }
    } catch (err) {
        err.message;
    }
    try {
        for (let i = 0; i < json.ekgSession.length; i++) {
            session = json.ekgSession[i]
            sessionID = json.ekgSession[i].sessionID
            CPR = json.ekgSession[i].cpr
            start = json.ekgSession[i].start
            container = "sessionID: " + sessionID + "  dato: " + start + "  cpr: " + CPR + "<br/>";
            document.getElementById("searchFieldArea").innerHTML += JSON.stringify(container);
        }
    } catch (err) {
        err.message;
    }
    /*
    try {
        for (let i = 0; i < json[0].ekgListe.length; i++) {
            sessionID = json[0].ekgListe[i].sessionID
            CPR = json[0].ekgListe[i].cpr
            start = json[0].ekgListe[i].start
            container = "sessionID: " + sessionID + "  dato: " + start + "  cpr: " + CPR + "<br/>";
            document.getElementById("searchFieldArea").innerHTML += JSON.stringify(container);
        }
    } catch (err) {
        err.message;
    } */

}


async function HentEkgData() {
    sesID = document.getElementById("sesID").value; //henter sessionID fra indtastningsfeltet
    console.log(sesID)

    const res = await fetch("data/ekgSessions/EKGmeasurements?sessionID=" + sesID,
        {
            headers: {
                "Authorization": localStorage.getItem("token")
            },
            method: "GET"
        });

    let labels = []
    let values = await res.json()
    //console.log(json)
    for (var i = 0; i < values.length; i++)
        labels.push("" + i)

    if (chart?.destroy) {
        chart?.destroy()
    }

    chart = new Chart(document.getElementById("myChart").getContext("2d"), {
        // The type of chart we want to create
        type: "line",
        // The data for our dataset
        data: {
            labels: labels,
            datasets: [
                {
                    label: "EKG",
                    backgroundColor: "rgba(50,60,93,0)",
                    borderColor: "rgba(50,60,93,0.45)",
                    data: values,
                },
            ],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        },
    });

    // nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders
    function getVals() {
        // Get slider values
        var parent = this.parentNode;
        var slides = parent.getElementsByTagName("input");
        var min = parseFloat(slides[0].value);
        var max = parseFloat(slides[1].value);
        // Neither slider will clip the other, so make sure we determine which is larger
        if (min > max) {
            var tmp = max;
            max = min;
            min = tmp;
        }

        var label = [];
        var value = [];

        label = JSON.parse(JSON.stringify(labels)).slice(min, max);
        value = JSON.parse(JSON.stringify(values)).slice(min, max);
        chart.data.labels = label;
        chart.data.datasets[0].data = value;
        chart.update();

        var displayElement = parent.getElementsByClassName("rangeValues")[0];
        displayElement.innerHTML = "Min : " + min + " Max : " + max;
    }

    // Initialize Sliders
    var sliderSections = document.getElementsByClassName("range-slider");
    for (var x = 0; x < sliderSections.length; x++) {
        var sliders = sliderSections[x].getElementsByTagName("input");
        for (var y = 0; y < sliders.length; y++) {
            if (sliders[y].type === "range") {
                sliders[y].oninput = getVals;
                sliders[y].max = JSON.parse(JSON.stringify(labels)).length;
                // Manually trigger event first time to display values
                sliders[y].oninput();
            }
        }
    }
}

function logud() {
    sessionStorage.setItem("username", "");
    window.location.replace("LoginSide.html");
}











