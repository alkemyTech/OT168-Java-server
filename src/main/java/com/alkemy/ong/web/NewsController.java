package com.alkemy.ong.web;

import com.alkemy.ong.data.News;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {


    @PostMapping
    public News saveNews(News news) {
        if (news != null) {
            //El type se asigna desde Category
        }
        //return
    }


    @GetMapping("/{newsId}")
    public Optional<News> findById(@PathVariable("newsId") Long newsId) {
        //return .findById(newsId);

    }



}



