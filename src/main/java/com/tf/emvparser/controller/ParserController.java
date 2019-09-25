package com.tf.emvparser.controller;

import com.tf.emvparser.services.EmvParserService;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

    @Autowired
    private EmvParserService emvParserService;

    @GetMapping("/{input}")
    public String parseEmvInput(@PathVariable String input) throws ISOException {
        return emvParserService.parseInput(input);
    }
}
