var chart = ""
async function HentEkgData() {
    // Serialiser formen til js-objekt
    //let loginform = document.getElementById("loginform");
    CPRnummer = document.getElementById("CPRnummerBoks").value;

    console.log(CPRnummer)


    const res = await fetch("data/EKGService?" + new URLSearchParams({
        cpr: CPRnummer,
    }, {
        method: "GET"
    }));

    let json = await res.json()
    console.log(json)
    let labels = Array(json.length).fill("")

    if(chart?.destroy){
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
                    label: "",
                    backgroundColor: "rgba(50,60,93,0)",
                    borderColor: "rgba(50,60,93,0.45)",
                    data: json,
                },
            ],
        },
        // Configuration options go here
        options: {},
    });

}

/* nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/ */
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
        min = tmp;}

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
            sliders[y].max=JSON.parse(JSON.stringify(labels)).length;
            // Manually trigger event first time to display values
            sliders[y].oninput();
        }
    }
}

function findEKG() {


}





