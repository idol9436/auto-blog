package com.sangsang.autoblog.application.service;

import org.springframework.stereotype.Service;

import com.sangsang.autoblog.application.command.UploadCommand;
import com.sangsang.autoblog.domain.model.GithubInfo;
import com.sangsang.autoblog.domain.port.in.UploadUseCase;
import com.sangsang.autoblog.domain.port.out.GithubRestApiPort;

@Service
public class UploadService implements UploadUseCase {

  private final GithubRestApiPort githubRestApiPort;

  public UploadService(GithubRestApiPort githubRestApiPort){
      this.githubRestApiPort = githubRestApiPort;
  }

  @Override
  public void upload(UploadCommand cmd) {
      if(cmd.type().equals("Github")){
        githubRestApiPort.commitToGithub(GithubInfo.from(cmd)).doOnNext(res -> System.out.println(res));
      }

  }
}