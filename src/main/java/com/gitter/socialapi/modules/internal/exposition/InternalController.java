package com.gitter.socialapi.modules.internal.exposition;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.comment.application.CommentService;
import com.gitter.socialapi.modules.comment.exposition.payload.request.*;
import com.gitter.socialapi.modules.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.modules.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/internal")
public class InternalController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> getHealthcheck() {
        return ResponseEntity.ok("alive");
    }
}
