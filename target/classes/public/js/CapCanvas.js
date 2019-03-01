let CapCanvas=function(id,fps,time){
	window.onload = function(){
		var script = document.createElement("script")
		script.type = "text/javascript";
		script.src = "js/CCapture.all.min.js";
		document.body.appendChild(script);
	};
	window.addEventListener('keydown', event => {
		if(event.key=='c'||event.key=='C'){
			let canvas=document.getElementById(id);
			startCapture(canvas,fps,time);
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
};
