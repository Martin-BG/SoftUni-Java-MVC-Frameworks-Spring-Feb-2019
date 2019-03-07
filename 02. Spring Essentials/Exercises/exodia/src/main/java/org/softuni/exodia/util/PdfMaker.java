package org.softuni.exodia.util;

import com.qkyrie.markdown2pdf.Markdown2PdfConverter;
import com.qkyrie.markdown2pdf.internal.exceptions.ConversionException;
import com.qkyrie.markdown2pdf.internal.exceptions.Markdown2PdfLogicException;
import com.qkyrie.markdown2pdf.internal.writing.Markdown2PdfWriter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.logging.Level;

@Log
@Component
public class PdfMaker {
    private static final String PDF_LAYOUT = "<center><h1>%s</h1></center><hr>%s";

    private static void markdownToPdf(String content, OutputStream output)
            throws Markdown2PdfLogicException, ConversionException {
        Markdown2PdfConverter
                .newConverter()
                .readFrom(() -> content)
                .writeTo(getMarkdown2PdfWriter(output))
                .doIt();
    }

    private static Markdown2PdfWriter getMarkdown2PdfWriter(OutputStream output) {
        return data -> {
            try {
                output.write(data);
            } catch (IOException e) {
                throw new PdfMakerException("Failed to write data to output stream", e);
            }
        };
    }

    public Optional<byte[]> markdownToPdf(String title, String content) {
        // Doesn't support non-latin characters: https://stackoverflow.com/a/20325481/7598851

        String formatted = String.format(PDF_LAYOUT, title, content);

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            markdownToPdf(formatted, output);
            return Optional.of(output.toByteArray());
        } catch (IOException | PdfMakerException | ConversionException | Markdown2PdfLogicException e) {
            log.log(Level.SEVERE, "Failed to covert data to PDF format", e);
            return Optional.empty();
        }
    }

    private static class PdfMakerException extends RuntimeException {
        PdfMakerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
