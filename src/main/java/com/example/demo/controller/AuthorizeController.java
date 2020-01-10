package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;

@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state) {
		AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");
		accessTokenDTO.setState(state);
		accessTokenDTO.setClient_id("63dedcca1a1a69c765b4");
		accessTokenDTO.setClient_secret("283fb72bb822dc7f0074ce7c8f90c83c78c87b1d");
		String accessToken=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		System.out.println(githubUser.getName());
		return "index";
	}
}
