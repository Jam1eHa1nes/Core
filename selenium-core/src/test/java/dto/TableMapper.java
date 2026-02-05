package dto;

import java.util.Map;

public class TableMapper {

    public Search decodeSearch(Map<String, String> row) {

        return new Search(
                row.get("searchEngine"),
                row.get("searchCriterea"));
    }
}
