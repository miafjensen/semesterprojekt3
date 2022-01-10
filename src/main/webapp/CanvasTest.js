
const data = [-0.1, -0.05, -0.01, 0.1]

window.onload = (function (){
    let canvas = document.getElementById("mycanvas");
    let context = canvas.getContext("2d");
    context.moveTo(0,300);
    for (let i = 0; i<data.length; i++)
        context.lineTo(i, 300-data[i]*100);
    context.stroke();
})