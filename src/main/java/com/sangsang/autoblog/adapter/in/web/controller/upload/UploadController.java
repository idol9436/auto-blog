package com.sangsang.autoblog.adapter.in.web.controller.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sangsang.autoblog.adapter.in.web.dto.UploadRequestDTO;
import com.sangsang.autoblog.domain.port.in.UploadUseCase;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private final UploadUseCase uploadUseCase;

    public UploadController(UploadUseCase uploadUseCase){
        this.uploadUseCase = uploadUseCase;
    }

    @PostMapping("/api")
    @ResponseBody
    public Mono<String> upload(@RequestBody UploadRequestDTO dto){

        return uploadUseCase.upload(dto.toCommand());

    }

}
