package Tp.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Tp.JSonData.JsonData;
import Tp.model.Configuration;

@RestController
public class ConfigurationController {
    @CrossOrigin
    @GetMapping("Configurations/")
    public JsonData UpdateCategorie() throws Exception {
        JsonData json = new JsonData();
        try {
            Configuration c=new Configuration();
            c.getConfig();
        } catch (Exception e) {
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        return json;

    }
}
