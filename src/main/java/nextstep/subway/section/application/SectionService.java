package nextstep.subway.section.application;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.dto.SectionRequest;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;

@Service
@Transactional
public class SectionService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public SectionService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public LineResponse addSection(Long lineId, SectionRequest sectionRequest) {
        Line findedLine = lineRepository.findById(lineId).orElseThrow(EntityNotFoundException::new);
        Station upStation = stationRepository.findById(sectionRequest.getUpStationId())
            .orElseThrow(EntityNotFoundException::new);
        Station downStation = stationRepository.findById(sectionRequest.getDownStationId())
            .orElseThrow(EntityNotFoundException::new);
        findedLine.addSection(sectionRequest.toSection(upStation, downStation));

        return LineResponse.of(findedLine);
    }
}