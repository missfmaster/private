package com.hw.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hw.bean.Record;
import com.hw.dao.RecordDao;

@WebServlet(description = "接收所有外部请求", urlPatterns = { "/*" })
public class ReceiverServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ReceiverServlet.class);
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.info("收到请求：⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇");
		Record rec = new Record();
		
		String meth = request.getMethod();
		log.info("Method : {}", meth);
		rec.setMethod(meth);
		
		Map<String, String> headers = new HashMap<String, String>();
		Enumeration<String> names = request.getHeaderNames();
		log.info("Header : ");
		String name = null, value = null;
		while(names.hasMoreElements()) {
			name = names.nextElement();
			value = request.getHeader(name);
			headers.put(name, value);
			log.info("\t{} : {}", name, value);
		}
		rec.setHeader(JSON.toJSONString(headers));
		
		Map<String, String[]> map = request.getParameterMap();
		if(map != null && !map.isEmpty()) {
			log.info("参数接收方式：Parameter.");
			rec.setTakeType(1);
			rec.setDataType(1);
			rec.setDataJSON(JSON.toJSONString(map));
			for(Entry<String, String[]> entry : map.entrySet()) {
				log.info("\t{} : {}", entry.getKey(), entry.getValue());
			}
		} else {
			ServletInputStream is = request.getInputStream();
			if(is != null) {
				log.info("参数接收方式：Stream.");
				rec.setTakeType(2);
				rec.setDataType(2);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buff = new byte[512];
				int length = 0;
				while((length = is.read(buff)) != -1) {
					bos.write(buff, 0, length);
				}
				is.close();
				rec.setDataStream(bos.toByteArray());
				log.info("\t[Stream.]");
			}
		}
		new RecordDao().insert(rec);
		log.info("请求结束：⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆⬆");
	}

}
