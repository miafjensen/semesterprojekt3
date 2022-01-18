//if (!localStorage.getItem("token")){window.location.href="LoginSide.html"} //nægter adgang uden login
let chart2 = ""
let chart3 = ""
let chart4 = ""

async function findAftalerImport() {
    let cpr = document.getElementById("searchinput").value;
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
    let cpr = document.getElementById("searchinput").value;
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
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 3:<br/>";
        for (let i = 0; i < json[1].sessions.ekgSession.length; i++) {
            let data = json[1].sessions.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 4:<br/>";
        for (let i = 0; i < json[2].ekgSessionList.ekgSession.length; i++) {
            let data = json[2].ekgSessionList.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }
    try {
        document.getElementById("tekstfelt").innerHTML += "<br/>Sessions fra gruppe 5:<br/>";
        for (let i = 0; i < json[3].ekgListe.ekgSession.length; i++) {
            let data = json[3].ekgListe.ekgSession[i]
            document.getElementById("tekstfelt").innerHTML += JSON.stringify(data);
        }
        document.getElementById("tekstfelt").innerHTML += "<br/>";
    } catch (err) {
        err.message
    }

}

async function findEkgDataImport() {
    let sessionID = document.getElementById("searchinput").value;
    let result = await fetch("data/import/ekgSessions/measurements?sessionID=" + sessionID, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    if (json[0].measurements.measurment.length > 0) {
        try {
            let labels2 = [];
            let values2 = json[0].measurements.measurment
            for (var i = 0; i < values2.length; i++)
                labels2.push("" + i)

            if (chart2?.destroy) {
                chart2?.destroy()
            }

            chart2 = new Chart(document.getElementById("gruppe2").getContext("2d"), {
                // The type of chart we want to create
                type: "line",
                // The data for our dataset
                data: {
                    labels: labels2,
                    datasets: [
                        {
                            label: "EKG gruppe 2",
                            backgroundColor: "rgba(50,60,93,0)",
                            borderColor: "rgba(50,60,93,0.45)",
                            data: values2,
                        },
                    ],
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                },
            });

            /* nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders*/
            function getVals2() {
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

                var label2 = [];
                var value2 = [];

                label2 = JSON.parse(JSON.stringify(labels2)).slice(min, max);
                value2 = JSON.parse(JSON.stringify(values2)).slice(min, max);
                chart2.data.labels = label2;
                chart2.data.datasets[0].data = value2;
                chart2.update();

                var displayElement = parent.getElementsByClassName("rangeValues2")[0];
                displayElement.innerHTML = "Min : " + min + " Max : " + max;
            }

            // Initialize Sliders
            var sliderSections = document.getElementsByClassName("range-slider2");
            for (var x = 0; x < sliderSections.length; x++) {
                var sliders2 = sliderSections[x].getElementsByTagName("input");
                for (var y = 0; y < sliders2.length; y++) {
                    if (sliders2[y].type === "range") {
                        sliders2[y].oninput = getVals2;
                        sliders2[y].max = JSON.parse(JSON.stringify(labels2)).length;
                        // Manually trigger event first time to display values
                        sliders2[y].oninput();
                    }
                }
            }
        } catch (err) {
            err.message
        }
    }
    if (json[1].measurements.measurment.length > 0) {
        try {
            let labels3 = [];
            let values3 = json[1].measurements.measurment
            for (var i = 0; i < values3.length; i++)
                labels3.push("" + i)

            if (chart3?.destroy) {
                chart3?.destroy()
            }

            chart3 = new Chart(document.getElementById("gruppe3").getContext("2d"), {
                // The type of chart we want to create
                type: "line",
                // The data for our dataset
                data: {
                    labels: labels3,
                    datasets: [
                        {
                            label: "EKG gruppe 3",
                            backgroundColor: "rgba(50,60,93,0)",
                            borderColor: "rgba(50,60,93,0.45)",
                            data: values3,
                        },
                    ],
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                },
            });

            // nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders
            function getVals3() {
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

                var label3 = [];
                var value3 = [];

                label3 = JSON.parse(JSON.stringify(labels3)).slice(min, max);
                value3 = JSON.parse(JSON.stringify(values3)).slice(min, max);
                chart3.data.labels = label3;
                chart3.data.datasets[0].data = value3;
                chart3.update();

                var displayElement = parent.getElementsByClassName("rangeValues3")[0];
                displayElement.innerHTML = "Min : " + min + " Max : " + max;
            }

            // Initialize Sliders
            var sliderSections = document.getElementsByClassName("range-slider3");
            for (var x = 0; x < sliderSections.length; x++) {
                var sliders3 = sliderSections[x].getElementsByTagName("input");
                for (var y = 0; y < sliders3.length; y++) {
                    if (sliders3[y].type === "range") {
                        sliders3[y].oninput = getVals3;
                        sliders3[y].max = JSON.parse(JSON.stringify(labels3)).length;
                        // Manually trigger event first time to display values
                        sliders3[y].oninput();
                    }
                }
            }

        } catch (err) {
            err.message
        }
    }
    if (json[2].ekgData.measurement.length > 0) {
        try {

            let labels4 = [];
            let values4 = json[2].ekgData.measurement
            for (var i = 0; i < values4.length; i++)
                labels4.push("" + i)

            if (chart4?.destroy) {
                chart4?.destroy()
            }

            chart4 = new Chart(document.getElementById("gruppe4").getContext("2d"), {
                // The type of chart we want to create
                type: "line",
                // The data for our dataset
                data: {
                    labels: labels4,
                    datasets: [
                        {
                            label: "EKG gruppe 4",
                            backgroundColor: "rgba(50,60,93,0)",
                            borderColor: "rgba(50,60,93,0.45)",
                            data: values4,
                        },
                    ],
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                },
            });

            // nedenstående er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/  til range sliders
            function getVals4() {
                // Get slider values
                var parent = this.parentNode;
                var slides4 = parent.getElementsByTagName("input");
                var min = parseFloat(slides4[0].value);
                var max = parseFloat(slides4[1].value);
                // Neither slider will clip the other, so make sure we determine which is larger
                if (min > max) {
                    var tmp = max;
                    max = min;
                    min = tmp;
                }

                var label4 = [];
                var value4 = [];

                label4 = JSON.parse(JSON.stringify(labels4)).slice(min, max);
                value4 = JSON.parse(JSON.stringify(values4)).slice(min, max);
                chart4.data.labels = label4;
                chart4.data.datasets[0].data = value4;
                chart4.update();

                var displayElement4 = parent.getElementsByClassName("rangeValues4")[0];
                displayElement4.innerHTML = "Min : " + min + " Max : " + max;
            }

            // Initialize Sliders
            var sliderSections4 = document.getElementsByClassName("range-slider4");
            for (var x = 0; x < sliderSections4.length; x++) {
                var sliders4 = sliderSections4[x].getElementsByTagName("input");
                for (var y = 0; y < sliders4.length; y++) {
                    if (sliders4[y].type === "range") {
                        sliders4[y].oninput = getVals4;
                        sliders4[y].max = JSON.parse(JSON.stringify(labels4)).length;
                        // Manually trigger event first time to display values
                        sliders4[y].oninput();
                    }
                }
            }
        } catch (err) {
            err.message
        }
        console.log(json)

        /*
         try {document.getElementById("tekstfelt").innerHTML += "<br/>Measurements fra gruppe 2:<br/>";
             //values = json[0].measurements
             for (let i = 0; i < json[0].measurements.measurment.length; i++) {
                 let aftale1 = json[0].measurements.measurment[i]

                 console.log(values)
                 document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale1);
             }document.getElementById("tekstfelt").innerHTML += "<br/>";
         } catch (err) {
             err.message
         }
         try {document.getElementById("tekstfelt").innerHTML += "<br/>Measurements fra gruppe 3:<br/>";
             for (let i = 0; i < json[1].measurements.measurment.length; i++) {
                 let aftale2 = json[1].measurements.measurment[i]
                 document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale2);
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
         }*/


    }
}

// charts med  range sliders er baseret på eksempel fra https://jsfiddle.net/gh7qb4ud/1/
function logud() {
    sessionStorage.setItem("username", "");
    window.location.replace("LoginSide.html");
}



