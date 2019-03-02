package net.termat.webapp.capcanvas;

import static spark.Spark.get;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

public class Main {
	private static String code;

	public static void main(String[] args) {
		Spark.staticFileLocation("/public");
		code=getJs();
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
             return new ModelAndView(model, "index.mustache");
        }, new MustacheTemplateEngine());

        get("/capcanvas/:param", (request, response) -> {
        	try{
        		String url=request.url();
        		url=url.substring(0, url.indexOf("capcanvas"))+"js/CCapture.all.min.js";
        		System.out.println(url);

        		Map<String,String> map=paramMap(request.params("param"));
        		String id=map.get("id");
        		String fps=map.get("fps");
        		String time=map.get("time");
                response.status(200);
                String ret=new String(code);
                ret=ret.replace("$URL", url);
                ret=ret.replace("$ID", id);
                ret=ret.replace("$FPS", fps);
                ret=ret.replace("$TIME", time);
                return ret;
        	}catch(Exception e){
        		e.printStackTrace();
	        	response.status(400);
	        	response.type("application/json");
	    		return "";
        	}
        });

	}

	private static String getJs(){
		try{
			URL url=Main.class.getResource("CapCanvas.js");
			BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buf=new StringBuffer();
			String line=null;
			while((line=br.readLine())!=null){
				buf.append(line);
			}
			return buf.toString();
		}catch(Exception e){
			return "";
		}
	}

	private static String getHostName() {
	    try {
	        return InetAddress.getLocalHost().getHostAddress();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "UnknownHost";
	}

	private static Map<String,String> paramMap(String param) throws Exception{
    	Map<String,String> ret=new HashMap<String,String>();
    	String[] p=param.split("&");
    	for(int i=0;i<p.length;i++){
    		String[] k=p[i].split("=");
    		if(k.length<2)continue;
    		ret.put(k[0], k[1]);
    	}
    	return ret;
    }
}
