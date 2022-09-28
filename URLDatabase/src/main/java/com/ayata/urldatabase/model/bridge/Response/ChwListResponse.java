package com.ayata.urldatabase.model.bridge.Response;

import com.ayata.urldatabase.model.database.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChwListResponse {
    private int resultsPerPage;
    private int currentPage;
    private int total;
    private List<Users> chwsList;
}
