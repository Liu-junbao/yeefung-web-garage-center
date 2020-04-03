package com.jeesite.modules.yf.web;

import com.google.gson.Gson;
import com.jeesite.modules.yf.entity.YFGarageReport;
import com.jeesite.modules.yf.service.YFGarageStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "${adminPath}/yf/api")
public class ApiController {

    @Autowired
    private YFGarageStatusService yFGarageStatusService;

    @ResponseBody
    @RequestMapping(value = "report", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public String report(@RequestBody String content) {

        YFGarageReport json = new Gson().fromJson(content, YFGarageReport.class);

        return "true";
    }
}


