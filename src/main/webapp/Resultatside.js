var chart = ""

async function findAlleEKGSessions() {
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
    console.log(json)
    try{
    for (let i = 0; i<json[0].ekgListe.ekgSession.length; i++) {
        let aftale = json[1].ekgListe.ekgSession[i]
        document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
    }}catch (err){
        err.message;
    }
    try {
        for (let i = 0; i < json[0].ekgSession.length; i++) {
            let aftale = json[0].ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
        }
    } catch (err) {
        err.message;
    }
    try {
        for (let i = 0; i < json[0].ekgListe.length; i++) {
            let aftale = json[0].ekgListe[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
        }
    } catch (err) {
        err.message;
    }
}

//udfyldskema(sessions)
/*
for (let i = 0; i<json[0].sessions.ekgList.length; i++) {
    let sessions = json[0].sessions.ekgList[i]
    document.getElementById("searchFieldArea").innerHTML += JSON.stringify(sessions);
}*/

//let sessions = await sessionsID.json()
//console.log(sessions)
//udfyldskema(sessions)


function udfyldskema(sessions) {
    let dato = "";
    let sessionid = "";
    let CPR = "";
    let container = "";
    for (let i = 0; i < sessions.ekgList.length; i++) {
        dato = ("  Dato: " + json.sessions.ekgList[i].start);
        sessionid = ("  SessionID: " + sessions.ekgList[i].sessionID);
        CPR = ("  CPR: " + sessions.ekgList[i].cpr);

        container = '<span class="searchField">' + dato + CPR + sessionid + '</span>';
        console.log("test af container: " + container)
    }

    for (let i = 0; i < sessions.ekgSession.length; i++) {
        dato = ("  Dato: " + sessions.ekgSession[i].start);
        sessionid = ("  SessionID: " + sessions.ekgSession[i].sessionID);
        CPR = ("  CPR: " + sessions.ekgSession[i].cpr);

        container = '<span class="searchField">' + dato + CPR + sessionid + '</span>';
        console.log("test af container: " + container)
    }


    console.log("container: " + container)
    document.getElementById("searchFieldArea").innerHTML = container;

}


async function HentEkgData() {
    sesID = document.getElementById("sessionID").value; //henter sessionID fra indtastningsfeltet
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

    /* nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders*/
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











