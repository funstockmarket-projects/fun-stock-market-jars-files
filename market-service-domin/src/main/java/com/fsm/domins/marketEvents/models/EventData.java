package com.fsm.domins.marketEvents.models;

import com.fsm.domins.globalenums.MarketEvents;
import com.fsm.domins.stockDetails.models.StockFileDetails;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Event_Data")
public class EventData {
    @Id
    @Field("eventUUID")
    private String eventUUID;
    @Field(value="Event_Name")
    @Indexed(unique = true)
    private MarketEvents eventName;
    @Field(value="EventCreationData")
    private LocalDateTime EventCreationData;
    @Field(value = "ModifiedEventCreationData")
    private LocalDateTime ModifiedDate = LocalDateTime.now();
    @Field("Stock_File_Details")
    @DBRef
    private List<StockFileDetails> stockFileDetails;
    public void isValid(){
        this.ModifiedDate = LocalDateTime.now();
    }
}
