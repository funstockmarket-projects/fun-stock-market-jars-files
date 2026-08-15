package funMarketStockDetails;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    private T data;
    private int processingCounts;

}
