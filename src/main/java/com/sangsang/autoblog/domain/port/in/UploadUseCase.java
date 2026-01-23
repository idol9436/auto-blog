package com.sangsang.autoblog.domain.port.in;

import com.sangsang.autoblog.application.command.UploadCommand;

public interface UploadUseCase {
    
    public void upload(UploadCommand uploadCommand);

}
