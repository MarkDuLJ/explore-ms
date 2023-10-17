package com.example.ec.service;

import com.example.ec.domain.TourRating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TourRatingServiceIntegrationTest {
    private static final int CUSTOMRER_ID= 123;
    private static final int TOUR_ID= 1;
    private static final int NOT_A_TOUR_ID= 777;

    @Autowired
    private  TourRatingService service;
    @Test
    public void delete() {
        List<TourRating> tourRatings = service.lookupAll();
        service.delete(tourRatings.get(0).getTour().getId(), tourRatings.get(0).getCustomerId());

        assertThat(service.lookupAll().size(), is(tourRatings.size()-1));
    }
}
