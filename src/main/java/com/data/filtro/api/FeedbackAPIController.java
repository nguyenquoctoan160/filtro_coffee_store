package com.data.filtro.api;

import com.data.filtro.model.ErrorResponse;
import com.data.filtro.model.Feedback;
import com.data.filtro.model.Product;
import com.data.filtro.repository.ProductRepository;
import com.data.filtro.service.FeedbackService;
import com.data.filtro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/comment")
public class FeedbackAPIController {
    @Autowired
    ProductService productservice;
    @Autowired
    FeedbackService feedbackService;
    @GetMapping("/getAll/{productId}")
    public ResponseEntity<?> getAllFeedbackByProduct(@PathVariable int productId) {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedbackByProduct(productId);
            if (feedbacks == null) {
                String message = "No feedback found!";
                ErrorResponse err = new ErrorResponse(message, HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/sentfeedback/{productId}")
    public ResponseEntity<?> addFeedbackToProduct(@PathVariable int productId, @RequestBody Feedback feedback) {

        Product product = productservice.getProductById(productId);
        if (product != null) {
            feedback.setProduct(product);
            Feedback newFeedback = feedbackService.addFeedback(feedback);
            return new ResponseEntity<>(newFeedback, HttpStatus.OK);
        } else {
            String message = "Product not found!";
            ErrorResponse err = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
