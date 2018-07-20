package com.lzt.SampleController;

import com.lzt.entity.ModelFloor;
import com.lzt.serivice.ModelFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Enzo Cotter on 2018-7-19.
 */
@Controller
public class MFComtroller {
    @Autowired
    ModelFloorService modelFloorService;
    @RequestMapping("/MF")
    public String GetPCList(Model model) {
        return "MFManagement";
    }

}
