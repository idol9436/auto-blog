package com.sangsang.autoblog.application.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sangsang.autoblog.application.command.UploadCommand;
import com.sangsang.autoblog.domain.model.GithubInfo;
import com.sangsang.autoblog.domain.port.in.UploadUseCase;
import com.sangsang.autoblog.domain.port.out.GithubRestApiPort;

import reactor.core.publisher.Mono;

@Service
public class UploadService implements UploadUseCase {

  private final GithubRestApiPort githubRestApiPort;

  public UploadService(GithubRestApiPort githubRestApiPort){
      this.githubRestApiPort = githubRestApiPort;
  }

  @Override
  public Mono<ResponseEntity<String>>  findExist(UploadCommand cmd) {
      if(cmd.type().equals("Github")){
        System.out.println("Find to Github");
        return githubRestApiPort.getExistInfo(GithubInfo.from(cmd));
      }

      return Mono.just(ResponseEntity.ok("ok"));
  }

  @Override
  public Mono<ResponseEntity<String>>  upload(UploadCommand cmd) {
      if(cmd.type().equals("Github")){
        if(cmd.sha() != null && !cmd.sha().isEmpty()){
          System.out.println("Update to Github");
          return githubRestApiPort.updateToGithub(GithubInfo.from(cmd));
        }
        System.out.println("Create to Github");
        return githubRestApiPort.createToGithub(GithubInfo.from(cmd));
      }

      return Mono.just(ResponseEntity.ok("ok"));

  }
}