package com.lzt.SampleController;

import com.alibaba.fastjson.JSONObject;

import com.lzt.entity.User;
import com.lzt.serivice.UserService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


//Controller 单独可跳转页面
//RestController == Controller+ResponseBody 是无法跳转页面的
@Controller
public class HelloController {


    @Autowired  // @Qualifier("") 指定实现类
    private UserService userservice;

    @RequestMapping("/")
    public String Welcome() {
        return "Welcome";
    }


    @RequestMapping("/VerifyLogin")
    public String Verify(HttpServletRequest Request) {
        String password = Request.getParameter("Password");
        String username = Request.getParameter("Name");
        User user = userservice.findByName(username);
        String truePassword = user.getPassword();

        HttpSession session = Request.getSession();
        session.setAttribute("loginName", user.getUsername());
        session.setAttribute("remark", user.getRemark());
        session.setAttribute("power", user.getPower());
        session.setAttribute("truePass", truePassword);

        if (password.equals(truePassword))
            return "redirect:/PC";

        else
            return "Welcome";
    }

    @RequestMapping("/Record")
    public String Record() {

        return "record";
    }

    @RequestMapping("/password")
    public String password(HttpServletRequest Request, Model model) {
        HttpSession session = Request.getSession();

        model.addAttribute("truePass", session.getAttribute("truePass"));
        return "password";
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    public void changePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPass = request.getParameter("newPass");
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("loginName");
        User user = userservice.findByName(name);
        user.setPassword(newPass);
        userservice.updateuser(user);

//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();
//		out.flush();
//		out.close();

    }

    @RequestMapping(value = "/SaveUser", method = RequestMethod.POST)
    public void SaveUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        String power = request.getParameter("power");
        User user = new User();
        user.setPassword("123456");
        user.setPower(power);
        user.setRemark(remark);
        user.setUsername(name);
        userservice.saveUser(user);


    }

    @RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
    public void UpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String remark = request.getParameter("remark");
        String power = request.getParameter("power");
        String pass = request.getParameter("pass");
        Long id=Long.parseLong(request.getParameter("id"));
       User user=userservice.findByid(id);
       if (pass=="1")
           user.setPassword("123456");

       user.setPower(power);
       user.setUsername(name);
       user.setRemark(remark);


   userservice.saveUser(user);


    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        userservice.deleteUser(id);


    }


    @RequestMapping(value = "/getLogin", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getLogin(HttpServletRequest Request) throws ServletException, IOException {
        JSONObject result = new JSONObject();
        HttpSession session = Request.getSession();
        result.put("power", session.getAttribute("power"));
        return result;
    }

}