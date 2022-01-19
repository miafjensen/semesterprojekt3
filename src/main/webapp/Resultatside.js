/**

 * @author ${ Mia }

 * @Date ${jan 2022}

 */

let tok = localStorage.getItem("token");        //kræver token for at kunne tilgå siden
if (!tok) {
    window.location.href = "LoginSide.html"
}      //hvis token mangler, vil man bliver navigeret til loginsiden
var chart = ""
//bruger samme metode som til import fra andre grupper i import.js, derfor ikke authorization via token
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
    let Start;
    let sessionID;
    let container;
    console.log(json)

    // to næsten ens metoder til at printe fordi json array skifter mellem to forskellige navne uden kendt årsag
    //dette er til array ekgListe
    try {
        document.getElementById("searchFieldArea").innerHTML += "Session tilhørende cpr: " + cpr + "<br/>";
        for (let i = 0; i < json.ekgListe.length; i++) {
            sessionID = (" SessionID: " + json.ekgListe[i].sessionID)
            Start = (" Dato: " + json.ekgListe[i].start)
            container = Start + sessionID + "<br/>";
            document.getElementById("searchFieldArea").innerHTML += container;
        }
    } catch (err) {
        err.message;
    }
    //dette er til array ekgSession
    try {
        for (let i = 0; i < json.ekgSession.length; i++) {
            sessionID = (" SessionID: " + json.ekgSession[i].sessionID)
            Start = (" Dato: " + json.ekgSession[i].start)
            container = Start + sessionID + "<br/>";
            document.getElementById("searchFieldArea").innerHTML += container;
        }
    } catch (err) {
        err.message;
    }

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
    let values = await res.json() //data fra array til chart
    //console.log(json)
    for (var i = 0; i < values.length; i++) //data navngives med label, som et nummer der svarer til pladsen i array
        labels.push("" + i)
//tømmer chart hvis det allerede indeholder data fra tidligere sessionID
    if (chart?.destroy) {
        chart?.destroy()
    }

    chart = new Chart(document.getElementById("myChart").getContext("2d"), {
        type: "line",
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
            //disse sørger for at canvas tilpasser sig div
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
                sliders[y].oninput();
            }
        }
    }
}

function logud() {
    sessionStorage.setItem("username", "");
    window.location.replace("LoginSide.html");
}











