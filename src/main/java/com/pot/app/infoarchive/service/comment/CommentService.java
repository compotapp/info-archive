package com.pot.app.infoarchive.service.comment;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreateRequest;
import com.pot.app.infoarchive.domain.comment.dto.CommentResponse;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdateRequest;

import java.util.List;

public interface CommentService {

    CommentResponse save(CommentCreateRequest request);

    CommentResponse getById(IdDto request);

    List<CommentResponse> getAll();

    CommentResponse update(CommentUpdateRequest request);

    void deleteById(IdDto request);

    void deleteAll();
}
