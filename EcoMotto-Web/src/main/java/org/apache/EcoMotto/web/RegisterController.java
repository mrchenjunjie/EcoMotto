package org.apache.EcoMotto.web;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import  org.apache.EcoMotto.web.Customer;
import org.apache.EcoMotto.web.component.IPersonService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
	@Autowired
	private IPersonService personService;
	@RequestMapping("/register")
    public String onSubmit(@RequestParam(value="username", required=false) String username,
    		@RequestParam(value="password", required=false) String password, 
    		@RequestParam(value="email", required=false) String email,
    		Model model) throws Exception {
		
	        model.addAttribute("username", username);
	        model.addAttribute("password", password);
	        model.addAttribute("email", email);

	    Customer customer = new Customer();
	    customer.setUsername(username);
	    customer.setPassword(password);
	    customer.setEmail(email);
        
	    writeCustomer(customer);
	    
	    
        return "register";
    }
	
	
	public void writeCustomer(Customer customer) throws IOException, URISyntaxException 
	   {
	      //1. Get the instance of COnfiguration
	      Configuration configuration = new Configuration();
	      //2. Get the HDFS instance
	      FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:8020/"), configuration);
	      
	      String new_customer = customer.getUsername()+","+customer.getPassword()+","+customer.getEmail()+"\n";
	      
	      FSDataOutputStream outputStream = hdfs.create(new Path("hdfs://localhost:8020/usr/local/hadoop/junjie/customer/"+customer.getUsername()+".txt"));
	      try
	      {
	    	outputStream.write(new_customer.getBytes());
	      }
	      finally
	      {
	        IOUtils.closeStream(outputStream);
	      } 
	  }
	
	public Customer readCustomer(String customerUsername) throws IOException, URISyntaxException 
	   {
	      //1. Get the instance of COnfiguration
	      Configuration configuration = new Configuration();
	      //2. Get the HDFS instance
	      FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:8020/"), configuration);
	      //3. Open a OutputStream to write the data, this can be obtained from the FileSytem
	      FSDataInputStream inputStream = hdfs.open(new Path("hdfs://localhost:8020/usr/local/hadoop/junjie/customer/"+customerUsername+".txt"));
	      try
	      {
	    	byte[] customerInfor = new byte[1000];
			inputStream.read(customerInfor);
	    	String customerFromRemote = new String(customerInfor,"UTF-8");
	    	Customer customer = new Customer();
	    	customer.setUsername(customerUsername);
	    	customerFromRemote = customerFromRemote.substring(customerFromRemote.indexOf(",")+1);
	    	
	    	customer.setPassword(customerFromRemote.substring(0,customerFromRemote.indexOf(",")));
	    	customerFromRemote = customerFromRemote.substring(customerFromRemote.indexOf(",")+1);
	    	
	    	customer.setEmail(customerFromRemote);
	    	
	    	return customer;
	    	
	      }
	      finally
	      {
	        IOUtils.closeStream(inputStream);
	      } 
	  }
}
