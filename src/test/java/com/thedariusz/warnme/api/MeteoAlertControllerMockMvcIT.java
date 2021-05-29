package com.thedariusz.warnme.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.MeteoAlertOrigin;
import com.thedariusz.warnme.twitter.MeteoAlert;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MeteoAlertController.class)
class MeteoAlertControllerMockMvcIT extends IntegrationTestBase {
    static final String ALERTS_PATH = "/alerts";
    @Autowired
    WebClient twitterClientBuilder;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MeteoAlertDao meteoAlertDao;


    @BeforeEach
    public void init() {
        meteoAlertDao.deleteAll();
    }


    @Test
    void fetchAllShouldSaveSingleAlertInMemory() throws Exception {
        //given
        final OffsetDateTime startDateTime = OffsetDateTime.now();

        mockMvc.perform(post(ALERTS_PATH + "/1139834822011084801"))
                .andDo(print())
                .andExpect(status().isOk());


        final List<MeteoAlert> meteoAlerts = meteoAlertDao.fetchAll();
        assertThat(meteoAlerts)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("creationDate", "description", "media", "alertOrigin")
                        .build())
                .contains(
                        meteoAlert(2, Set.of("burze", "burze z gradem", "deszcz", "grad"))
//                        meteoAlert(1, Set.of("burze", "burza", "deszcz", "grad"))
                );

    }

//    @Test
//    void fetchAllShouldSaveManyAlertsInMemory() throws Exception {
//        mockMvc.perform(post(ALERTS_PATH + "/2979632800"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        assertThat(meteoAlertDao.fetchAll())
//                .hasSize(2)
//                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
//                        .withIgnoredFields("level", "categories", "description", "media")
//                        .build())
//                .contains(
//                        meteoAlert("2021-05-11T11:10:10.000Z", meteoAlertOrigin("10")),
//                        meteoAlert("2021-03-12T17:05:02.000Z", meteoAlertOrigin("20"))
//                );
//    }
//

//    private TweetDtoTest expectedMeteoAlert() {
//        return new TweetDtoTest(
//                "1",
//                "test",
//                new AuthorDtoTest("1", "imgw", "imgw ipb"),
//                "2021-05-06",
//                List.of("url1", "url2"),
//                List.of("burze")
//        );
//    }
//
//    private TweetDtoTest expectedOtherAlert() {
//        return new TweetDtoTest(
//                "2",
//                "test",
//                new AuthorDtoTest("1", "imgw", "imgw ipb"),
//                "2021-05-06",
//                List.of("photo1", "photo2"),
//                List.of("burze")
//        );
//    }

    private MeteoAlert meteoAlert(int level, Set<String> categories) {
        final MeteoAlertOrigin meteoAlertOrigin = new MeteoAlertOrigin("Twitter", "1139834822011084801", "1");
        return new MeteoAlert(level, categories, null, null, meteoAlertOrigin, null);
    }

}