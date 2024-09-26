package com.ai.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    private Integer id;
    private String trainName;
    private String trainNumber;
    private Long distance;
    private String source;
    private String destiny;
    private String organizer;
    private String travelDate;

}
