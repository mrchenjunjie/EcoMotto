package org.apache.EcoMotto.web;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.EcoMotto.web.component.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class LoginController {
	@Autowired
	private IPersonService personService;
	@RequestMapping("/home")
    public String onSubmit(@RequestParam(value="userId", required=false) String userId,
    		@RequestParam(value="password", required=false) String password,
    		Model model) throws IOException, URISyntaxException {
			
	        model.addAttribute("msg", "Hello "+personService.getPersonName() );
	        model.addAttribute("userId", userId);
	        model.addAttribute("password", password);
	        
	        Customer customer = new Customer();
	        RegisterController reg = new RegisterController();
	        customer = reg.readCustomer(userId);
	        
	        if(customer.getPassword().equals(password)){
	        	return "home";
	        }
	        return "error";
	}
}