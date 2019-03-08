package org.softuni.exodia.web.controllers;

import lombok.extern.java.Log;
import org.softuni.exodia.domain.models.binding.document.DocumentScheduleBindingModel;
import org.softuni.exodia.domain.models.view.document.DocumentDetailsViewModel;
import org.softuni.exodia.service.DocumentService;
import org.softuni.exodia.util.PdfMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Controller
public class DocumentController {

    private static final String APPLICATION_PDF = "application/pdf";

    private final DocumentService service;
    private final PdfMaker pdfMaker;

    @Autowired
    public DocumentController(DocumentService service,
                              PdfMaker pdfMaker) {
        this.service = service;
        this.pdfMaker = pdfMaker;
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        model.addAttribute("view", "views/schedule.html");
        return "base-layout";
    }

    @PostMapping("/schedule")
    public String schedulePost(@ModelAttribute DocumentScheduleBindingModel model) {
        return service
                .schedule(model)
                .map(id -> "redirect:/details/" + id)
                .orElse("redirect:/");
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable String id, Model model) {
        Optional<DocumentDetailsViewModel> document = service
                .findById(UUID.fromString(id), DocumentDetailsViewModel.class);
        if (document.isPresent()) {
            model.addAttribute("document", document.get());
            model.addAttribute("view", "views/details.html");
            return "base-layout";
        }

        return "redirect:/";
    }

    @GetMapping("/print/{id}")
    public String print(@PathVariable String id, Model model) {
        Optional<DocumentDetailsViewModel> document = service
                .findById(UUID.fromString(id), DocumentDetailsViewModel.class);
        if (document.isPresent()) {
            model.addAttribute("document", document.get());
            model.addAttribute("view", "views/print.html");
            return "base-layout";
        }

        return "redirect:/";
    }

    @PostMapping("/print/{id}")
    public String printPost(@PathVariable String id) {
        service.deleteById(UUID.fromString(id));

        return "redirect:/";
    }

    @GetMapping(value = "/download/{id}", produces = APPLICATION_PDF)
    public @ResponseBody
    void download(@PathVariable String id, HttpServletResponse response) {
        service.findById(UUID.fromString(id), DocumentDetailsViewModel.class)
                .flatMap(d -> pdfMaker.markdownToPdf(d.getTitle(), d.getContent()))
                .ifPresent(data -> {
                    String filename = id + ".pdf";
                    response.setContentType(APPLICATION_PDF);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                    response.setContentLength(data.length);

                    try (OutputStream output = response.getOutputStream()) {
                        FileCopyUtils.copy(data, output);
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Write to response output stream failed", e);
                    }
                });
    }
}
