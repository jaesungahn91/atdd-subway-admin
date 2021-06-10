package nextstep.subway.line.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.dto.StationResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @NoArgsConstructor
public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    private LineResponse(final Long id, final String name, final String color, final List<StationResponse> stations, final LocalDateTime createdDate, final LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static LineResponse of(final Line line) {
        List<StationResponse> stationResponse = line.lineUp()
            .stream()
            .map(StationResponse::of)
            .collect(Collectors.toList());

        return LineResponse.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .createdDate(line.getCreatedDate())
                .modifiedDate(line.getModifiedDate())
                .stations(stationResponse)
                .build();
    }
}
