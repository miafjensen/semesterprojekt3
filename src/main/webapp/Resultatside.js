var chart = ""

async function findAlleEKGSessions() {

    const sessionsID = await fetch("data/ekgSessions?" + URLSearchParams({
        method: "GET"
        }));

    let sessions = await sessionsID.json()
    console.log(sessions)

}
/*
function hentAftaleFecth(from, to) {
    let fra = from;
    let til = to;
    fetch("data/aftaler/aftalerSQL?" + new URLSearchParams({
        from: fra,
        to: til,

    }), {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    }).then(resp => resp.json()).then(data => {
        console.log(data)
        udfyldskema(data)
    } );
}

function udfyldskema(data) {
    let timestart = "";
    let timeend = "";
    let klinikId = "";
    let cpr = "";
    let container = "";
    let note = "";

    for (let i = 0; i < data.aftale.length; i++) {
        timestart = data.aftale[i].timeStart.substring(11, 16) + "\t-\t";
        timeend = data.aftale[i].timeEnd.substring(11, 16)
        klinikId = ("klinikId: " + data.aftale[i].klinikID);
        cpr = "CPR: " + data.aftale[i].cpr + "\t";
        note = "Notat: " + data.aftale[i].notat;


        let Tider = '<span class="autotider">' + timestart + timeend + '</span>';
        let CPR = '<span class="autoname">' + cpr + klinikId + '</span>';
        let Notat = '<span class="autonote">' + note + '</span><hr>';

        container += Tider + CPR + Notat;
    }
    document.getElementById("autotider").innerHTML = container;
}
 */

async function HentEkgData() {
    sesID = document.getElementById("sessionID").value; //henter sessionID fra indtastningsfeltet

    console.log(sesID)


    const res = await fetch("data/ekgSessions/measurements?sessionID=" + sesID,
    {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    ,
        method: "GET"
    });
    let rangevalues = []
    let labels = []
    let values = await res.json()
    //console.log(json)
    rangevalues = values
    for (var i = 0; i < values.length; i++)
        labels.push("" + i)
    //rangevalues.push(values)
    //Array(json.length).fill("")

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
        // Configuration options go here
        options: {
            responsive: true,
            maintainAspectRatio: false
        },
    });

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

        //var datasets = Data.datasets;
        // IF YOU HAVE MULTIPLE SERIESES

        // ChartObj.data.labels = label;
        // for (var i = 0; i < datasets.length; i++) {
        //     values = datasets[i].data.slice(min, max);
        //     ChartObj.data.datasets[i].data = values;
        // }
        // ChartObj.update();
        value = JSON.parse(JSON.stringify(rangevalues)).slice(min, max);
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

/* nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders*/


function findEKGSession() {

}







