package com.lzt.SampleController;

import com.alibaba.fastjson.JSONObject;

import com.lzt.serivice.UserService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



//Controller 单独可跳转页面
//RestController == Controller+ResponseBody 是无法跳转页面的
 @Controller
public class HelloController {



	@Autowired  // @Qualifier("") 指定实现类
	private UserService userservice  ;

 	@RequestMapping("/")
	public String Welcome(){
		return "Welcome";
	}

@RequestMapping("/VerifyLogin")
public String Verify(HttpServletRequest Request){
 	    String password=Request.getParameter("Password");
 	    String username=Request.getParameter("Name");
        String truePassword=userservice.findPassByName(username);

	HttpSession session=Request.getSession();
	session.setAttribute("loginName",username);

if (password.equals(truePassword))
	return "redirect:/PC";
 	 //   return "MainPage";
else
    return "Welcome";
}
    @RequestMapping("/Record")
    public String Record() {

        return "record";
    }

	@RequestMapping(value = "/getLogin", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject addPC(HttpServletRequest Request) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		HttpSession session=Request.getSession();
		result.put("loginName",session.getAttribute("loginName"));
		return result;
	}
	}