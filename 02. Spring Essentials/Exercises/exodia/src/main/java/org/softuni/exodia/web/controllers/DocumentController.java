package org.softuni.exodia.web.controllers;

import lombok.extern.java.Log;
import org.softuni.exodia.annotations.AuthenticatedUser;
import org.softuni.exodia.annotations.Layout;
import org.softuni.exodia.config.WebConfig;
import org.softuni.exodia.domain.models.binding.document.DocumentScheduleBindingModel;
import org.softuni.exodia.domain.models.view.document.DocumentDetailsViewModel;
import org.softuni.exodia.service.DocumentService;
import org.softuni.exodia.util.PdfMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Layout
@Controller
@AuthenticatedUser
@SessionAttributes(DocumentController.DOCUMENT)
public class DocumentController extends BaseController {

    private static final String APPLICATION_PDF = "application/pdf";
    public static final String DOCUMENT = "document";

    private final DocumentService service;
    private final PdfMaker pdfMaker;

    @Autowired
    public DocumentController(DocumentService service,
                              PdfMaker pdfMaker) {
        this.service = service;
        this.pdfMaker = pdfMaker;
    }

    @ModelAttribute(DOCUMENT)
    public DocumentDetailsViewModel documentDetailsViewModel() {
        return new DocumentDetailsViewModel();
    }

    @GetMapping(WebConfig.URL_SCHEDULE)
    public String schedule() {
        return "schedule";
    }

    @PostMapping(WebConfig.URL_SCHEDULE)
    public String schedulePost(@ModelAttribute DocumentScheduleBindingModel bindingModel,
                               @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                               Model model) {
        return redirect(service
                .schedule(bindingModel)
                .map(doc -> {
                    model.addAttribute(DOCUMENT, doc);
                    return WebConfig.URL_DETAILS + "/" + doc.getId();
                })
                .orElse(WebConfig.URL_SCHEDULE));
    }

    @GetMapping(WebConfig.URL_DETAILS + "/{id}")
    public String details(@PathVariable String id,
                          @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                          Model model) {
        return getDocumentView(id, document, model, "details");
    }

    @GetMapping(WebConfig.URL_PRINT + "/{id}")
    public String print(@PathVariable String id,
                        @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                        Model model) {
        return getDocumentView(id, document, model, "print");
    }

    @PostMapping(WebConfig.URL_PRINT + "/{id}")
    public String printPost(@PathVariable String id, SessionStatus sessionStatus) {
        service.deleteById(UUID.fromString(id));

        sessionStatus.setComplete();

        return redirect(WebConfig.URL_INDEX);
    }

    @ResponseBody
    @GetMapping(value = WebConfig.URL_DOWNLOAD + "/{id}", produces = APPLICATION_PDF)
    public void download(@PathVariable String id,
                         @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                         HttpServletResponse response) {
        pdfBuildAndSend(id, document, response);
    }

    private void pdfBuildAndSend(String id, DocumentDetailsViewModel document, HttpServletResponse response) {
        uuid(id)
                .flatMap(uuid -> (uuid.toString().equals(document.getId())) ?
                        Optional.of(document) :
                        service.findById(uuid, DocumentDetailsViewModel.class))
                .flatMap(doc -> pdfMaker.markdownToPdf(doc.getTitle(), doc.getContent()))
                .ifPresent(pdfData -> {
                    response.setContentType(APPLICATION_PDF);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + id + ".pdf\"");
                    response.setContentLength(pdfData.length);

                    try (OutputStream output = response.getOutputStream()) {
                        FileCopyUtils.copy(pdfData, output);
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Sending data failed", e);
                    }
                });
    }

    private String getDocumentView(String id, DocumentDetailsViewModel document, Model model, String view) {
        return id.equals(document.getId()) ?
                view :
                uuid(id)
                        .flatMap(uuid -> service.findById(uuid, DocumentDetailsViewModel.class))
                        .map(doc -> {
                            model.addAttribute(DOCUMENT, doc);
                            return view;
                        })
                        .orElse(redirect(WebConfig.URL_INDEX));
    }
}
