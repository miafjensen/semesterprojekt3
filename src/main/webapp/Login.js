/**

 * @author ${tidligere kode fra IT3 grp4}

 * @Date ${}

 */
let user = "";
let pass = "";
/* kode til at skjule og vise kodeordet */
const visibilityListen = document.getElementById('visibilityListen')
visibilityListen.addEventListener('click', togglevisibiliy) //brug functionen hvis man trykker på iconet

var passlabel = document.getElementById('password')
var btn = document.getElementById('btn')

//gør det muligt at logge ind ved tryk på 'enter'
passlabel.addEventListener('keyup', (e) => {
    if (e.keyCode === 13) {
        btn.click();
    }
})

//funktion til at få vist tegnene der skrives i kodeordsfelt
function togglevisibiliy() {
    const passwordInput = document.getElementById("password")
    const icon = document.getElementById("icon")
    if (passwordInput.type === "password") { //vis koden
        passwordInput.type = "text"
        icon.innerText = "visibility_off"
    } else { //vis ikke koden
        passwordInput.type = "password"
        icon.innerText = "visibility"
    }
}

/* kode til at validere koden */
async function login() {
    // Serialiser formen til js-objekt
    user = document.getElementById("username").value;
    pass = document.getElementById("password").value;
    sessionStorage.setItem("user", user);
    console.log(user + pass)
    //Bruger fetch-API til at sende data - POST. JSON.stringify for at serialisere objekt til string.
    //tilpasset vores url
    const res = await fetch("data/login?" + new URLSearchParams({
        username: user,
        password: pass,
    }, {
        method: "GET"
    }));

    // hvis vi får en token, gemmer vi den i browserens localstorage
    const token = await res.text();
    localStorage.setItem("token", token);
    //For ekstra krymmel fisker vi en bruger ud af token
    const payload = window.atob(token.split(".")[1]);
    const payloadJson = JSON.parse(payload);
    localStorage.setItem("user", payloadJson.username);
    //Viderestil til den rigtige side!
    window.location.href = "StartSide.html"
}


