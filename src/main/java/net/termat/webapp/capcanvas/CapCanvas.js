window.onload = function(){
var script = document.createElement("script");
script.type = "text/javascript";
script.src = "$URL";
document.body.appendChild(script);
};
window.addEventListener('keydown', event => {
if(event.key=='c'||event.key=='C'){
let canvas=document.getElementById("$ID");
startCapture(canvas,$FPS,$TIME);
}
});
let startCapture=function(canvas,fps,time){
let cap=new CCapture({format:'webm',framerate: fps,verbose: true});
let render=function(){
requestAnimationFrame(render);
cap.capture( canvas );
};
requestAnimationFrame(render);
cap.start();
setTimeout(function(){
cap.stop();
cap.save();
},time);
};