package com.example.ec.web;

import com.example.ec.service.TourRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/ratings")
public class RatingController {
    private  static final Logger LOGGER = LoggerFactory.getLogger((RatingController.class));
    private TourRatingService tourRatingService;

    @Autowired
    public RatingController(TourRatingService tourRatingService) {
        this.tourRatingService = tourRatingService;
    }

    @GetMapping
    public List<RatingDto> getAll(){
        LOGGER.info("GET /ratings");
        return tourRatingService.lookupAll().stream()
                .map((t->new RatingDto(t.getScore(),t.getComment(),t.getCustomerId()))).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RatingDto getRating(@PathVariable("id") Integer id) {
        LOGGER.info("GET /ratings/{id}", id);
        return tourRatingService.lookupRatingById(id)
                .map(t -> new RatingDto(t.getScore(), t.getComment(), t.getCustomerId()))
                .orElseThrow(() -> new NoSuchElementException("Rating " + id + " not found"));

    }


    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        LOGGER.error("Unable to complete transaction", ex);
        return ex.getMessage();
    }

}
