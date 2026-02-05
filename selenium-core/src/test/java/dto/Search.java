package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Search {

    private String searchEngine;
    private String searchCriterea;
}
