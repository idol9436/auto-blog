package com.sangsang.autoblog.domain.model;

import com.sangsang.autoblog.application.command.UploadCommand;

public class GithubInfo {
    public final String token; 
    public final String owner; 
    public final String repo; 
    public final String path;
    public final String commitMsg;
    public final String content;

    private GithubInfo(
        String token,
        String owner,
        String repo,
        String path,
        String commitMsg,
        String content
    ){
        this.token = token;
        this.owner = owner;
        this.repo = repo;
        this.path = path;
        this.commitMsg = commitMsg;
        this.content = content;
    }

    public static GithubInfo from(UploadCommand cmd){
        return new GithubInfo(cmd.token(), cmd.owner(), cmd.repo(), cmd.path(), cmd.commitMsg(), cmd.content());
    }
}
