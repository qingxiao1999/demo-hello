package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${github.client.id}")
	private String clientId;
	
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state) {
		AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		String accessToken=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		System.out.println(githubUser.getName());
		return "index";
	}
}
