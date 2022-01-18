//if (!localStorage.getItem("token")){window.location.href="LoginSide.html"} //nægter adgang uden login

async function findAftalerImport(){
    let cpr = document.getElementById("cpr").value;
    let result = await fetch("data/import?cpr=" + cpr, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)
    for (let i = 0; i<json[0].aftaleListe.aftale.length; i++) {
        let aftale = json[0].aftaleListe.aftale[i]
        document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
    }
}

async function findSessionIDImport(){
    let cpr = document.getElementById("cpr").value;
    let result = await fetch("data/import/sessionID?cpr=" + cpr, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)
    for (let i = 0; i<json[0].sessions.ekgSession.length; i++) {
        let aftale = json[0].sessions.ekgSession[i]
        document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
    }
    for (let i = 0; i<json[1].ekgListe.ekgSession.length; i++) {
        let aftale = json[1].ekgListe.ekgSession[i]
        document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
    }
}

async function findEkgDataImport(){
    let cpr = document.getElementById("cpr").value;
    let result = await fetch("data/import/sessionID?cpr=" + cpr, {
        headers: {
            "Authorization": localStorage.getItem("token")
        }
    });
    let json = await result.json();
    console.log(json)
    /*
    for (let i = 0; i<json[0].sessions.ekgSession.length; i++) {
        let aftale = json[0].sessions.ekgSession[i]
        document.getElementById("tekstfelt").innerHTML += JSON.stringify(aftale);
    }
    */
}

function logud() {
    sessionStorage.setItem("username", "");
    window.location.replace("LoginSide.html");
}