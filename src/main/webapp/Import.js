//if (!localStorage.getItem("token")){window.location.href="LoginSide.html"} //nægter adgang uden login
let chart = ""
async function findAftalerImport() {
    let cpr = document.getElementById("input").value;
    let result = await fetch("data/import?cpr=" + cpr, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)

    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Aftaler fra gruppe 2:<br/>";
        for (let i = 0; i < json[0].aftaleListe.aftale.length; i++) {
            let session = json[0].aftaleListe.aftale[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(session);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Aftaler fra gruppe 3:<br/>";
        for (let i = 0; i < json[1].aftaleListe.aftale.length; i++) {
            let session = json[1].aftaleListe.aftale[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(session);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Aftaler fra gruppe 4:<br/>";
        for (let i = 0; i < json[2].aftaleListe.aftale.length; i++) {
            let session = json[2].aftaleListe.aftale[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(session);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Aftaler fra gruppe 5:<br/>";
        for (let i = 0; i < json[3].aftaleListe.aftale.length; i++) {
            let session = json[3].aftaleListe.aftale[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(session);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }

}

async function findSessionIDImport() {
    let cpr = document.getElementById("input").value;
    let result = await fetch("data/import/ekgSessions?cpr=" + cpr, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 2:<br/>";
        for (let i = 0; i < json[0].sessions.ekgSession.length; i++) {
            let data = json[0].sessions.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 3:<br/>";
        for (let i = 0; i < json[1].sessions.ekgSession.length; i++) {
            let data = json[1].sessions.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 4:<br/>";
        for (let i = 0; i < json[2].ekgSessionList.ekgSession.length; i++) {
            let data = json[2].ekgSessionList.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 5:<br/>";
        for (let i = 0; i < json[3].ekgListe.ekgSession.length; i++) {
            let data = json[3].ekgListe.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }

}

async function findEkgDataImport() {
    let sessionID = document.getElementById("input").value;
    let result = await fetch("data/import/ekgSessions/measurements?sessionID=" + sessionID, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Measurements fra gruppe 2:<br/>";
        for (let i = 0; i < json[0].measurements.measurment.length; i++) {
            let aftale = json[0].measurements.measurment[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Measurements fra gruppe 3:<br/>";
        for (let i = 0; i < json[1].measurements.measurment.length; i++) {
            let aftale = json[1].measurements.measurment[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {document.getElementById("tekstfelt").innerHTML += "<br/>Measurements fra gruppe 4:<br/>";
        for (let i = 0; i < json[2].ekgData.measurement.length; i++) {
            let aftale = json[2].ekgData.measurement[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
        }document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }


}

function logud() {
    sessionStorage.setItem("username", "");
    window.location.replace("LoginSide.html");
}


async function HentEkgData() {

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