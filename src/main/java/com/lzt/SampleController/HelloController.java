package com.lzt.SampleController;

import com.lzt.dao.UserDao;
import com.lzt.entity.User;
import com.lzt.serivice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


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

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
@RequestMapping("/VerifyLogin")
public String Verify(HttpServletRequest Request){
 	    String password=Request.getParameter("Password");
 	    String username=Request.getParameter("Name");
        String truePassword=userservice.findPassByName(username);

if (password.equals(truePassword))
 	    return "MainPage";
else
    return "E404";
}
    @RequestMapping("/Record")
    public String Record() {

        return "record";
    }
    	/*
	@RequestMapping("/register")
	public String register() {
 		return "register";
 	}
	@RequestMapping("/index")
	public String index() {
 	    return "index";
	}

 @RequestMapping("/getList")
public String GetUserList(Model model){

        List<User> userList =new ArrayList<User>();
     //    userList=(List<User>) userdao.findAll();
         model.addAttribute("users",userList);
         return "showlist";
}
	@RequestMapping("/AddRegister")
	public String register(HttpServletRequest request){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		if (password.equals(password2)){
			User userEntity = new User();
			userEntity.setUsername(username);
			userEntity.setPassword(password);
		//	userdao.save(userEntity);
			long i=2;
			return "index";
		}else {
			return "register";
		}
	}
*/

}