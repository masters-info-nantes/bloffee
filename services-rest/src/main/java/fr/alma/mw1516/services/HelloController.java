package fr.alma.mw1516.services;

import org.springframework.web.bind.annotation.*;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
@RestController
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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Country hello() {
        Country country = new Country();
        country.setName("Hello World");
        country.setId(42L);
        return country;
    }

}
