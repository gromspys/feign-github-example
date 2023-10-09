package com.example.demo;

import com.example.types.Contributor;
import com.example.types.Issue;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.spring.SpringContract;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class Main {

    interface GitHubApi {
        @GetMapping("/repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);

        @GetMapping("/repos/{owner}/{repo}/issues")
        List<Issue> issues(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
    }

    public static void main(String... args) {
        GitHubApi github = Feign.builder()
                .decoder(new JacksonDecoder())
                .contract(new SpringContract())
                .target(GitHubApi.class, "https://api.github.com");

        System.out.println(github.issues("OpenFeign", "feign"));
        System.out.println(github.contributors("OpenFeign", "feign"));
    }
}
