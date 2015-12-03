package fr.alma.mw1516.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
@Controller
@RequestMapping("sample")
public class HelloController {

    @RequestMapping(value="sayHello", method = RequestMethod.POST)
    @ResponseBody
    public Country sayHello(@RequestParam(value = "name", required = false) String name) {
        Country country = new Country();
        country.setName("Hello " + name);
        country.setId(42L);
        return country;
    }

}
