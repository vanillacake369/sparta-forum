package com.restapi.spartaforum.controller;

import com.restapi.spartaforum.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private QuestionService questionService;

    
}
