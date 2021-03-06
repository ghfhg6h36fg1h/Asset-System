package com.lzt.SampleController;

import com.lzt.entity.ModelFloor;
import com.lzt.entity.User;
import com.lzt.serivice.ModelFloorService;
import com.lzt.serivice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-19.
 */
@Controller
public class MFComtroller {
    @Autowired
    ModelFloorService modelFloorService;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping("/MF")
    public String GetPCList(Model model) {
        HttpSession session = request.getSession();
        String power = (String) session.getAttribute("power");
        model.addAttribute("remark", (String) session.getAttribute("remark"));
        model.addAttribute("power", (String) session.getAttribute("power"));

        if (!power.equals("guest")) {

            List<ModelFloor> mflist = modelFloorService.findAll();
            model.addAttribute("mflist", mflist);
            List<User> userlist = userService.findAll();
            model.addAttribute("userlist", userlist);
            return "MFManagement";
        } else
            return "guest";
    }

    @RequestMapping(value = "/SaveMF", method = RequestMethod.POST)
    public void saveMF(HttpServletResponse response) throws ServletException, IOException {

        String remark = request.getParameter("remark");
        String type = request.getParameter("type");
        String name = request.getParameter("name");

        modelFloorService.save(name, type, remark);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/UpdateMF", method = RequestMethod.POST)
    public void updateMF(HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String remark = request.getParameter("remark");
        String type = request.getParameter("type");
        String name = request.getParameter("name");

        modelFloorService.update(id, name, type, remark);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/deleteMF", method = RequestMethod.POST)
    public void deleteMF(HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));

        modelFloorService.delete(id);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

}
